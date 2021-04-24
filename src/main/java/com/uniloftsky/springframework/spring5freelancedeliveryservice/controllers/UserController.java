package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public User getUser(Authentication authentication) {
        return userService.findById(authentication.getName());
    }

    @GetMapping("/user/notifications")
    public Set<Notification> getUserNotifications(Authentication authentication) {
        return userService.findById(authentication.getName()).getUser_metadata().getNotifications();
    }

    @GetMapping("/user/advertisements")
    public Set<Advertisement> getUserAdvertisements(Authentication authentication) {
        return userService.findById(authentication.getName()).getUser_metadata().getAdvertisements();
    }

/*    @GetMapping("/users/{id}")
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
    public User patchUser(@PathVariable("id") String id, @RequestBody UserDTO user) throws IllegalAccessException {
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
    }*/

}