package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.service.ConnectionService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.containsString;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringSecurityAuthTestConfig.class)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private ConnectionService connectionServiceMock;

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
        user.setDisplayName("regular_user");
        user.setRoleType(new RoleType("Regular"));

        auth = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void register_urlValid_statusIsSuccessful() throws Exception {
        mockMvc.perform(get("/register")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void registerNewUser_validUserInput_statusIsSuccessful() throws Exception {
        when(userServiceMock.findUserByEmail(user.getEmail())).thenReturn(null);

        mockMvc.perform(post("/register")
                .queryParam("email", user.getEmail())
                .queryParam("password", user.getPassword())
                .queryParam("displayName", user.getDisplayName()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void registerNewUser_invalidUserInput_displaysErrorMessage() throws Exception {
        User user = new User();
        user.setEmail("test");
        user.setPassword("");
        user.setDisplayName("invalid_test_user");
        user.setRoleType(new RoleType("Regular"));

        when(userServiceMock.findUserByEmail(user.getEmail())).thenReturn(null);

        mockMvc.perform(post("/register")
                .queryParam("email", user.getEmail())
                .queryParam("password", user.getPassword())
                .queryParam("displayName", user.getDisplayName()))
                .andExpect(content().string(containsString("Email must be in a valid format.")))
                .andExpect(content().string(containsString("Passwords need to be between 5-60 characters.")));
    }

    @Test
    public void registerNewUser_userAlreadyExists_displaysErrorMessage() throws Exception {
        when(userServiceMock.findUserByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(post("/register")
                .queryParam("email", user.getEmail())
                .queryParam("password", user.getPassword())
                .queryParam("displayName", user.getDisplayName()))
                .andExpect(content().string(containsString("The email you entered is already taken.")));
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void getAddConnection_userIsLoggedIn_statusIsSuccessful() throws Exception {
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);

        mockMvc.perform(get("/user/addConnection")
                .requestAttr("user", user))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getAddConnection_userIsNotLoggedIn_statusIsRedirectionToLogin() throws Exception {
        mockMvc.perform(get("/user/addConnection")
                .requestAttr("user", user))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void createAddConnection_userIsLoggedIn_statusIsRedirectionToTransfer() throws Exception {
        User user2 = new User();
        user2.setEmail("regular@test.com");
        user2.setPassword("1234");
        user2.setDisplayName("regular_user");
        user2.setRoleType(new RoleType("Regular"));

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(userServiceMock.getUserFromAuth(auth)).thenReturn(user);
        when(userServiceMock.findUserByEmail(user2.getEmail())).thenReturn(user2);

        mockMvc.perform(post("/user/addConnection")
                .queryParam("email", user2.getEmail()))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user/transfer"));
    }

    @Test
    public void createAddConnection_userIsNotLoggedIn_statusIsRedirectionToLogin() throws Exception {
        mockMvc.perform(post("/user/addConnection")
                .requestAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
