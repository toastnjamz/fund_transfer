package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.MyUserDetails;
import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleTypeService roleTypeService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    private SessionRegistry sessionRegistry;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleTypeService roleTypeService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleTypeService = roleTypeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    //Gets all logged in users
//    public List<String> getUsersFromSessionRegistry() {
//        return sessionRegistry.getAllPrincipals().stream()
//                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
//                .map(Object::toString)
//                .collect(Collectors.toList());
//    }

//    public User getUserFromAuth(Authentication auth) {
//        MyUserDetails userDetails = (MyUserDetails)auth.getPrincipal();
//        if (userDetails != null) {
//            User user = findUserByEmail(userDetails.getUsername());
//            return user;
//        }
//        return null;
//    }

    //TODO: Does this effectively add a security layer?
    public User getUserFromAuth(Authentication auth) {
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            MyUserDetails userDetails = (MyUserDetails)auth.getPrincipal();
            User user = findUserByEmail(userDetails.getUsername());
            return user;
        }
        return null;
    }


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
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

    //TODO: Remove if not using or add validation
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    //TODO: Remove?
    @Override
    public User createUserByRegistration(User user) {
        User registeredUser = new User();
        registeredUser.setEmail(user.getEmail());
        registeredUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        registeredUser.setDisplayName(user.getDisplayName());
        registeredUser.setRoleType(roleTypeService.findRoleTypeByRoleType("Regular"));
        registeredUser.setIsActive(true);
        return userRepository.save(registeredUser);
    }

    @Override
    public void updateUser(User user) {
        User updatedUser = findUserById(user.getId());
        updatedUser.setRoleType(user.getRoleType());
        //TODO: check if email is already taken first
        //updatedUser.setEmail(user.getEmail());
        //TODO: use passwordEncoder?
        updatedUser.setPassword(user.getPassword());
        updatedUser.setDisplayName(user.getDisplayName());
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
