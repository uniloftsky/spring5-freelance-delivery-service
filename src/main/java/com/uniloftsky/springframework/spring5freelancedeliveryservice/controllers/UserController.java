package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*@ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users", params = "id")
    public Set<UserDTO> getUsers(@RequestParam("id") String id) {
        return userService.findById(id);
    }*/

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

}
