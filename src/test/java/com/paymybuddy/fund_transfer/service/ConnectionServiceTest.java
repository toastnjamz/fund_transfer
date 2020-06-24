package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Connection;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.ConnectionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionServiceTest {

    @Mock
    private ConnectionRepository connectionRepositoryMock;

    @Mock
    private UserServiceImpl userServiceImplMock;

    @InjectMocks
    private ConnectionServiceImpl connectionServiceImpl;

    @Test
    public void findConnectionListByUser_userExists_connectionListReturned() {
        //arrange
        User user1 = new User();
        User user2 = new User();
        Connection connection = new Connection(user1, user2.getId());
        connection.setId(1);

        when(connectionRepositoryMock.findConnectionListByUser(user1)).thenReturn(Arrays.asList(connection));

        //act
        List<Connection> result = connectionServiceImpl.findConnectionListByUser(user1);

        //assert
        assertEquals(1, result.size());
    }

    @Test
    public void findConnectedUsersByOwningUser_userExists_userListReturned() {
        //arrange
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        Connection connection = new Connection(user1, user2.getId());
        connection.setId(1);

        when(connectionRepositoryMock.findConnectionListByUser(user1)).thenReturn(Arrays.asList(connection));
        when(userServiceImplMock.findUserById(user1.getId())).thenReturn(user1);

        //act
        List<User> result = connectionServiceImpl.findConnectedUsersByOwningUser(user1);

        //assert
        assertEquals(1, result.size());
    }

    @Test
    public void createConnection_userExists_connectionSaved() {
        //arrange
        User user1 = new User();
        user1.setEmail("user1@test.com");
        User user2 = new User();
        user2.setEmail("user2@test.com");

        when(connectionRepositoryMock.findConnectionListByUser(user1)).thenReturn(new ArrayList<>());
        when(userServiceImplMock.findUserByEmail(user2.getEmail())).thenReturn(user2);

        //act
        connectionServiceImpl.createConnection(user1, user2.getEmail());

        //assert
        verify(connectionRepositoryMock, times(1)).save(any(Connection.class));
    }

    @Test
    public void createConnection_userDoesNotExist_connectionNotSaved() {
        //arrange
        User user1 = new User();
        user1.setEmail("user1@test.com");
        String user2Email = "user2@test.com";

        when(userServiceImplMock.findUserByEmail(user2Email)).thenReturn(null);

        //act
        connectionServiceImpl.createConnection(user1, user2Email);

        //assert
        verify(connectionRepositoryMock, times(0)).save(any(Connection.class));
    }

    @Test
    public void createConnection_userEmailsMatch_connectionNotSaved() {
        //arrange
        User user1 = new User();
        user1.setEmail("user1@test.com");
        String user2Email = "user1@test.com";

        //act
        connectionServiceImpl.createConnection(user1, user2Email);

        //assert
        verify(connectionRepositoryMock, times(0)).save(any(Connection.class));
    }
}
