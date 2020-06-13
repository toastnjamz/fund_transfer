package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Connection;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private ConnectionRepository connectionRepository;
    private UserService userService;

    @Autowired
    public ConnectionServiceImpl(ConnectionRepository connectionRepository, UserService userService) {
        this.connectionRepository = connectionRepository;
        this.userService = userService;
    }

    @Override
    public List<Connection> findConnectionListByUser(User user) {
        return connectionRepository.findConnectionListByUser(user);
    }

    @Override
    public List<User> findConnectedUsersByOwningUser(User owningUser) {
        List<User> connectedUsersList = new ArrayList<>();
        if (userService.findUserById(owningUser.getId()) != null) {
            for (Connection connection : findConnectionListByUser(owningUser)) {
                connectedUsersList.add(userService.findUserById(connection.getConnectedUserId()));
            }
        }
        return connectedUsersList;
    }

    @Override
    public void createConnection(User owningUser, String connectedUserEmail) {
        List<Connection> connectionList = connectionRepository.findConnectionListByUser(owningUser);
        User connectedUser = userService.findUserByEmail(connectedUserEmail);
        if (connectionList.isEmpty() || connectionList.stream().noneMatch(connection -> userService.findUserById(connection.getConnectedUserId()).equals(connectedUser))) {
            Connection newConnection = new Connection(owningUser, connectedUser.getId());
            connectionRepository.save(newConnection);
        }
    }
}
