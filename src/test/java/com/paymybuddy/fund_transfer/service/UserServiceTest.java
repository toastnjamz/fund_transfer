package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private RoleTypeService roleTypeServiceMock;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoderMock;

    @Mock
    private AccountService accountServiceMock;

    @Mock
    private CurrencyService currencyServiceMock;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    //TODO: fix, then add not logged in use case
//    @Test
//    public void getUserFromAuth_userLoggedIn_userReturned() {
//        //arrange
//        User user = new User();
//        user.setEmail("test@test.com");
//
//        Authentication auth = mock(Authentication.class);
//        auth.setAuthenticated(true);
//
//        when(auth.getPrincipal()).thenReturn()
//        when(auth.getName()).thenReturn(user.getEmail());
//        when(userRepositoryMock.findUserByEmail(user.getEmail())).thenReturn(user);
//
//        //act
//        User result = userServiceImpl.getUserFromAuth(auth);
//
//        //assert
//        assertNotNull(result);
//    }

    //TODO: fix
    @Test
//    @WithMockUser(username = "test@test.com", roles = { "Regular" })
    public void getUserFromAuth_userLoggedIn_userReturned() {
        //arrange
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("1234");

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        //TODO: What type should this be returning?
//        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken("test@test.com", "1234");
//        when(auth.getPrincipal()).thenReturn(principal);
        when(auth.getPrincipal()).thenReturn("test@test.com");

        when(auth.getName()).thenReturn(user.getEmail());
        when(userRepositoryMock.findUserByEmail(user.getEmail())).thenReturn(user);

        //act
        User result = userServiceImpl.getUserFromAuth(auth);

        //assert
        assertNotNull(result);
    }

    @Test
    public void findUserById_userExists_userReturned() {
        //arrange
        User user = new User();
        user.setId(1);

        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));

        //act
        User result = userServiceImpl.findUserById(1);

        //assert
        assertEquals(user, result);
    }

    @Test
    public void findUserById_userDoesNotExist_nullReturned() {
        //arrange

        //act
        User result = userServiceImpl.findUserById(1);

        //assert
        assertNull(result);
    }

    @Test
    public void findUserByEmail_userExists_userReturned() {
        //arrange
        User user = new User();
        user.setEmail("test@test.com");

        when(userRepositoryMock.findUserByEmail(user.getEmail())).thenReturn(user);

        //act
        User result = userServiceImpl.findUserByEmail("test@test.com");

        //assert
        assertEquals(user, result);
    }

    @Test
    public void findUserByEmail_userDoesNotExist_nullReturned() {
        //arrange

        //act
        User result = userServiceImpl.findUserByEmail("test@test.com");

        //assert
        assertNull(result);
    }

    @Test
    public void createUserByRegistration_userIsValid_userReturned() {
        //arrange
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("1234");
        user.setDisplayName("test_user");

        when(userRepositoryMock.save(any())).thenReturn(user);

        //act
        User result = userServiceImpl.createUserByRegistration(user);

        //assert
        assertEquals(user.getEmail(), result.getEmail());
    }

    //TODO: add
//    @Test
//    public void createUserByRegistration_userDoesNotExist_nullReturned() {
//        //arrange
//
//        //act
//
//        //assert
//
//    }
}
