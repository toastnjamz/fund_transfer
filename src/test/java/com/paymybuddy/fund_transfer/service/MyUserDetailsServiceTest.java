package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.MyUserDetails;
import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyUserDetailsServiceTest {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @Test
    public void loadUserByUsername_userExists_myUserDetailsReturned() {
        //arrange
        User user = new User();
        user.setEmail("testEmail@test.com");
        RoleType roleType = new RoleType("Regular");
        user.setRoleType(roleType);

        when(userServiceMock.findUserByEmail(user.getEmail())).thenReturn(user);

        //act
        MyUserDetails result = myUserDetailsService.loadUserByUsername(user.getEmail());

        //assert
        assertEquals(user.getEmail(), result.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_userDoesNotExist_throwsUsernameNotFoundException() {
        //arrange
        String email = "test@test.com";

        when(userServiceMock.findUserByEmail(email)).thenReturn(null);

        //act
        MyUserDetails result = myUserDetailsService.loadUserByUsername(email);

        //assert
        assertNull(result);
    }
}