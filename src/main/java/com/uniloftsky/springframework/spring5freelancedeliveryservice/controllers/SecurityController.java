package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.UserMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    private final UserMapper userMapper;
    private final UserService userService;

    public SecurityController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("/loginSuccess")
    public UserDTO getAuthoredUser(Authentication authentication) {
        return userMapper.userToUserDTO(userService.findByLogin(authentication.getName()));
    }

    @GetMapping("/logoutSuccess")
    public ResponseEntity<Object> getLogoutMessage() {
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Object> getLoginMessage() {
        return new ResponseEntity<>("You are not logged in!", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

}
