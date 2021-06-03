package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;

import java.util.Set;

public interface UserService {

    User findById(String id);

    Set<User> findAll();

    User save(User user, UserDTO userDTO);

    void delete(String userId);
}
