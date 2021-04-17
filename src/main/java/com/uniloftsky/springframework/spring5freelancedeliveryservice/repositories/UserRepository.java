package com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;

import java.util.Set;

public interface UserRepository {

    User findById(String id);

    Set<User> findAll();

    User save(User user);

    User patch(User user);

}
