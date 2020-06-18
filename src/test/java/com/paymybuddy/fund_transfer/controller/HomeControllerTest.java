package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.service.MyUserDetailsService;
import com.paymybuddy.fund_transfer.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void getHome() throws Exception {
        mockMvc.perform(get("/user/home")).andExpect((status().is3xxRedirection()));
    }

    @Test
    @WithUserDetails("regular@test.com")
    public void getContact() throws Exception {
        mockMvc.perform(get("/user/contact")).andExpect((status().is3xxRedirection()));
    }
}
