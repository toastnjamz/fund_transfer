package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        User updatedUser = this.findUserById(user.getId());
        updatedUser.setRoleType(user.getRoleType());
        //TODO: check if email is already taken first
        //updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setDisplayName(user.getDisplayName());
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
