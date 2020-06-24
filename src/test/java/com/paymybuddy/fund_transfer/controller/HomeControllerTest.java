package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringSecurityAuthTestConfig.class)
@RunWith(SpringRunner.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    private UserService userServiceMock;

    private User user;
    private Authentication auth;
    private SecurityContext securityContext;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Before
    public void setUpMockSecurity() {
        user = new User();
        user.setEmail("regular@test.com");
        user.setPassword("1234");

        auth = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void home_userLoggedIn_statusIsSuccessful() throws Exception {
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);

        mockMvc.perform(get("/user/home"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void home_userNotLoggedIn_statusIsRedirectionToLogin() throws Exception {
        mockMvc.perform(get("/user/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void contact_userIsLoggedIn_statusIsSuccessful() throws Exception {
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);

        mockMvc.perform(get("/user/contact"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void contact_userIsNotLoggedIn_statusIsRedirectionToLogin() throws Exception {
        mockMvc.perform(get("/user/contact"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}