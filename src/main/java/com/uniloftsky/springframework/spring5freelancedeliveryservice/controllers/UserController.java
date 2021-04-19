package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

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

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") String id, HttpServletResponse response) {
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public Set<User> getAllUsers() {
        return userService.findAll();
    }

    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User patchUser(@PathVariable("id") String id, @RequestBody UserDTO user) {
        return userService.save(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") String id) {
        userService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public String testMethod(@RequestBody UserDTO user) {
        return "string";
    }

}