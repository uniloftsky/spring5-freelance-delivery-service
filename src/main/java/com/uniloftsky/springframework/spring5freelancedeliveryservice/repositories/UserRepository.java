package com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;

public interface UserRepository {

    User findById(String id);

}
