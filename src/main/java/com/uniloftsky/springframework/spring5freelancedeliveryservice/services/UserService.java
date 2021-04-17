package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;

public interface UserService {

    User findById(String id);

}
