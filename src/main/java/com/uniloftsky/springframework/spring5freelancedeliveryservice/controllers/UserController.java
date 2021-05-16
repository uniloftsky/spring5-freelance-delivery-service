package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.ResourceNotFoundException;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.NotificationService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final DriverService driverService;
    private final NotificationService notificationService;
    private final AdvertisementService advertisementService;

    public UserController(UserService userService, DriverService driverService, NotificationService notificationService, AdvertisementService advertisementService) {
        this.userService = userService;
        this.driverService = driverService;
        this.notificationService = notificationService;
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public User getUser(Authentication authentication) {
        return userService.findById(authentication.getName());
    }

    @PatchMapping
    public User patchUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        return userService.save(userService.findById(authentication.getName()), userDTO);
    }

    //Notifications block

    @GetMapping("/notifications")
    public Set<Notification> getUserNotifications(Authentication authentication) {
        return userService.findById(authentication.getName()).getUser_metadata().getNotifications();
    }

    @GetMapping(value = "/notifications/{id}")
    public Notification getUserNotification(@PathVariable("id") Long id, Authentication authentication) {
        return notificationService.findUserNotification(id, authentication.getName());
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<String> deleteUserNotification(@PathVariable("id") Long id, Authentication authentication) throws IllegalAccessException {
        notificationService.delete(id, userService.findById(authentication.getName()));
        return new ResponseEntity<>("Notification deleted", new HttpHeaders(), HttpStatus.OK);
    }

    //End of notifications block

    //Advertisements block

    @GetMapping("/advertisements")
    public Set<Advertisement> getUserAdvertisements(Authentication authentication) {
        return userService.findById(authentication.getName()).getUser_metadata().getAdvertisements();
    }

    @GetMapping("/advertisements/{id}")
    public Advertisement getUserAdvertisement(@PathVariable("id") Long id, Authentication authentication) {
        return advertisementService.findUserAdvertisement(id, authentication.getName());
    }

    @DeleteMapping("/advertisements/{id}")
    public ResponseEntity<String> deleteUserAdvertisement(@PathVariable("id") Long id, Authentication authentication) {
        advertisementService.delete(id, userService.findById(authentication.getName()));
        return new ResponseEntity<>("Advertisement deleted", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/advertisements")
    public Advertisement createUserAdvertisement(@RequestBody Advertisement advertisement, Authentication authentication) {
        return advertisementService.save(advertisement, userService.findById(authentication.getName()));
    }

    @PatchMapping("/advertisements/{id}")
    public Advertisement patchUserAdvertisement(@PathVariable("id") Long id, @RequestBody AdvertisementDTO advertisement, Authentication authentication) {
        return advertisementService.patch(advertisement, userService.findById(authentication.getName()), id);
    }

    //End of advertisements block

    @GetMapping("/driver")
    public DriverDTO getUserDriver(Authentication authentication) {
        if (userService.findById(authentication.getName()).getUser_metadata().isDriver()) {
            return driverService.findByUserId(authentication.getName());
        } else {
            throw new ResourceNotFoundException("Cannot find an expected user-driver. Is given user a driver?");
        }
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