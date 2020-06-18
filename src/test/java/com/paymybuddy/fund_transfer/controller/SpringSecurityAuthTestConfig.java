package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.MyUserDetails;
import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.domain.User;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityAuthTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        RoleType regularRole = new RoleType("Regular");
        User regularUser = new User(regularRole, "regular@test.com", "1234", "regular_user");
        regularUser.setIsActive(true);
        MyUserDetails regularMyUser = new MyUserDetails(regularUser, regularRole);

        RoleType adminRole = new RoleType("Admin");
        User adminUser = new User(adminRole, "admin@test.com", "1234", "admin_user");
        adminUser.setIsActive(true);
        MyUserDetails adminMyUser = new MyUserDetails(adminUser, adminRole);

        return new InMemoryUserDetailsManager(Arrays.asList(regularMyUser, adminMyUser));
    }
}
