package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Connection;

import java.util.List;

public interface ConnectionService {

    public Connection findConnectionById(int id);

    public List<Connection> findConnectionListByUserEmail(String email);

    public List<String> findConnectionListUIElement(String email);

    public Connection createConnection(Connection connection);

    public void createConnection(String owningUserEmail, String connectedUserEmail);

    public void updateConnection(Connection connection);

    public void deleteConnection(int id);
}
