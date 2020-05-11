package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.User;

import java.util.List;

public interface UserService {

    public List<User> findAllUsers();

    public User findUserById(int id);

    public User findUserByEmail(String email);

    public User createUser(User user);

    //TODO: Remove test method
    public User createUserByRegistration(String email, String password, String displayName);

    public void updateUser(User user);

    public void deleteUser(int id);
}