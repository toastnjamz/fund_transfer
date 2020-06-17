package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    public User getUserFromAuth(Authentication auth);

    public User findUserById(int id);

    public User findUserByEmail(String email);

    public User createUserByRegistration(User user);
}