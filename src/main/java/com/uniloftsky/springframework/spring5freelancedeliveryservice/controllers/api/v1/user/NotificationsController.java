package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.notification.NotificationService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user/notifications")
public class NotificationsController {

    private final UserService userService;
    private final NotificationService notificationService;

    public NotificationsController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Set<Notification> getUserNotifications(Authentication authentication) {
        return userService.findById(authentication.getName()).getUser_metadata().getNotifications();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Notification getUserNotification(@PathVariable("id") Long id, Authentication authentication) {
        return notificationService.findUserNotification(id, authentication.getName());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserNotification(@PathVariable("id") Long id, Authentication authentication) {
        notificationService.delete(id, userService.findById(authentication.getName()));
        return new ResponseEntity<>("Notification deleted", new HttpHeaders(), HttpStatus.OK);
    }

}
