package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Connection;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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





    //TODO: Remove unused service methods?
    @Override
    public Connection findConnectionById(int id) {
        Optional<Connection> connectionOptional = connectionRepository.findById(id);
        if (connectionOptional.isPresent()) {
            Connection connection = connectionOptional.get();
            return connection;
        }
        return null;
    }

    @Override
    public Connection createConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

//    @Override
//    public void updateConnection(Connection connection) {
//        Connection updatedConnection = findConnectionById(connection.getId());
//        updatedConnection.setOwningUser(connection.getOwningUser());
//        updatedConnection.setConnectedUser(connection.getConnectedUser());
//    }

    @Override
    public void deleteConnection(int id) {
        connectionRepository.deleteById(id);
    }
}
