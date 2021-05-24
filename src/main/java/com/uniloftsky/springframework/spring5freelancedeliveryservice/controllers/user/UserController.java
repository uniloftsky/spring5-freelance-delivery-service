package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getUser(Authentication authentication) {
        return userService.findById(authentication.getName());
    }

    @PatchMapping
    public User patchUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        return userService.save(userService.findById(authentication.getName()), userDTO);
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