package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.UserDTO;
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
    public User save(User user, UserDTO userDTO) {
        return userRepository.save(user, userDTO);
    }

    @Override
    public void delete(String userId) {
        userRepository.delete(userId);
    }
}
