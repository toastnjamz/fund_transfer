package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.controller.SpringSecurityAuthTestConfig;
import com.paymybuddy.fund_transfer.domain.BankAccount;
import com.paymybuddy.fund_transfer.domain.MyUserDetails;
import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringSecurityAuthTestConfig.class)
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

    @Test
    public void getUserFromAuth_userLoggedIn_userReturned() {
        //arrange
        User user = new User();
        user.setEmail("regular@test.com");
        user.setPassword("1234");
        RoleType regularUserRole = new RoleType("Regular");
        MyUserDetails myUserDetailsMock = new MyUserDetails(user, regularUserRole);

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(((MyUserDetails)(auth.getPrincipal()))).thenReturn(myUserDetailsMock);

        when(userRepositoryMock.findUserByEmail(user.getEmail())).thenReturn(user);

        //act
        User result = userServiceImpl.getUserFromAuth(auth);

        //assert
        assertNotNull(result);
    }

    @Test
    public void getUserFromAuth_userNotLoggedIn_nullReturned() {
        //arrange
        AnonymousAuthenticationToken authentication = mock(AnonymousAuthenticationToken.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        //act
        User result = userServiceImpl.getUserFromAuth(authentication);

        //assert
        assertNull(result);
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
        verify(userRepositoryMock, times(1)).save(any(User.class));
    }
}
