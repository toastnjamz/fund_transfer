package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.*;
import com.paymybuddy.fund_transfer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleTypeService roleTypeService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AccountService accountService;
    private CurrencyService currencyService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleTypeService roleTypeService,
                           BCryptPasswordEncoder bCryptPasswordEncoder, AccountService accountService, CurrencyService currencyService) {
        this.userRepository = userRepository;
        this.roleTypeService = roleTypeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountService = accountService;
        this.currencyService = currencyService;
    }

    public User getUserFromAuth(Authentication auth) {
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            MyUserDetails userDetails = (MyUserDetails)auth.getPrincipal();
            User user = findUserByEmail(userDetails.getUsername());
            return user;
        }
        return null;
    }

    @Override
    public User findUserById(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user;
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    //This method is for a user-specific registration flow. A separate admin registration flow can be added if needed.
    @Override
    public User createUserByRegistration(User user) {
        User registeredUser = new User();
        registeredUser.setEmail(user.getEmail());
        registeredUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        registeredUser.setDisplayName(user.getDisplayName());
        registeredUser.setRoleType(roleTypeService.findRoleTypeByRoleType("Regular"));
        registeredUser.setIsActive(true);

        //Creating a new account for the user with $0.00 balance USD
        Account account = new Account(registeredUser, accountService.findAccountTypeByAccountType("Regular"),
                currencyService.findCurrencyByCurrencyLabel("USD"), new BigDecimal(0.00));
        accountService.createAccount(account);
        registeredUser.setAccount(account);
        return userRepository.save(registeredUser);
    }
}
