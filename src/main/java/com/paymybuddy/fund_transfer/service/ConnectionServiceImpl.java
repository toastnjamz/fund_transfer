package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Connection;
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

    //TODO: remove and replace with findConnectionByUserEmail(String email)?
    @Override
    public List<Connection> findConnectionListByUserEmail(String email) {
        return connectionRepository.findConnectionListByUserEmail(email);
    }

    @Override
    public void createConnection(String owningUserEmail, String connectionUserEmail) {
        List<Connection> connectionList = connectionRepository.findConnectionListByUserEmail(owningUserEmail);
        if (connectionList.stream().noneMatch(connection ->
                connection.getConnectedUser().getEmail().equals(connectionUserEmail))) {
            Connection newConnection = new Connection(userService.findUserByEmail(owningUserEmail), userService.findUserByEmail(connectionUserEmail));
            connectionRepository.save(newConnection);
        }
    }

    @Override
    public List<String> findConnectionListUIElement(String email) {
        List<String> connectionEmailsList = new ArrayList<>();
        if (findConnectionListByUserEmail(email) != null) {
            for (Connection connection : findConnectionListByUserEmail(email)) {
                connectionEmailsList.add(connection.getConnectedUser().getEmail());
            }
        }
        return connectionEmailsList;
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

    @Override
    public void updateConnection(Connection connection) {
        Connection updatedConnection = findConnectionById(connection.getId());
        updatedConnection.setOwningUser(connection.getOwningUser());
        updatedConnection.setConnectedUser(connection.getConnectedUser());
    }

    @Override
    public void deleteConnection(int id) {
        connectionRepository.deleteById(id);
    }
}
