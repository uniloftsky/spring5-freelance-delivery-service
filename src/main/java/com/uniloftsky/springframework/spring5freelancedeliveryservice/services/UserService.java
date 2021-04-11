package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.User;

import java.util.Set;

public interface UserService {

    User findByLogin(String login);
    Set<UserDTO> getAll();
    UserDTO getById(Long id);
    UserDTO save(UserDTO userDTO);
    UserDTO create(UserDTO userDTO);
    void delete(Long id);

}
