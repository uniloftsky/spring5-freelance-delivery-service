package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractController {

    private final UserService userService;

    protected AbstractController(UserService userService) {
        this.userService = userService;
    }

    User getUser(Authentication authentication) {
        return userService.findById(authentication.getName());
    }

}
