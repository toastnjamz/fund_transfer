package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Connection;
import com.paymybuddy.fund_transfer.domain.User;

import java.util.List;

public interface ConnectionService {

    public List<Connection> findConnectionListByUser(User user);

    public List<User> findConnectedUsersByOwningUser(User owningUser);

    public void createConnection(User owningUser, String connectedUserEmail);
}
