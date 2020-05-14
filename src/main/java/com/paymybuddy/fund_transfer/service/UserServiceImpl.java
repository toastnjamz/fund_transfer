package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleTypeService roleTypeService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleTypeService roleTypeService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleTypeService = roleTypeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    //TODO: Remove or update test method
//    @Override
//    public User createUserByRegistration(String userEmail, String userPassword, String userDisplayName) {
//        User user = new User();
//        user.setEmail(userEmail);
//        user.setPassword(bCryptPasswordEncoder.encode(userPassword));
//        user.setDisplayName(userDisplayName);
//        RoleType role = roleTypeService.findRoleTypeByRoleType("Regular");
//        user.setRoleType(role);
//        user.setIsActive(true);
//        return userRepository.save(user);
//    }

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
