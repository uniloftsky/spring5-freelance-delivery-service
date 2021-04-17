package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

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
}
