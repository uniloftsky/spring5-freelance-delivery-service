package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Set<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(String userId, UserDTO user) throws IllegalAccessException {
        return userRepository.save(userId, user);
    }

    @Override
    public void delete(String userId) {
        userRepository.delete(userId);
    }
}
