package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.MyUserDetails;
import com.paymybuddy.fund_transfer.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    // Replacing username with email here, since it's the unique identifier i'm using.
    @Override
    @Transactional
    public MyUserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException(userEmail);
        }
        MyUserDetails myUserDetails = new MyUserDetails(user, user.getRoleType());
        return myUserDetails;
    }
}
