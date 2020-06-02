package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Connection;
import com.paymybuddy.fund_transfer.domain.User;

import java.util.List;

public interface ConnectionService {

    public Connection findConnectionById(int id);

    public List<Connection> findConnectionListByUser(User user);

    public List<User> findConnectedUsersByOwningUser(User owningUser);

    public Connection createConnection(Connection connection);

    public void createConnection(User owningUser, String connectedUserEmail);

//    public void updateConnection(Connection connection);

    public void deleteConnection(int id);
}
