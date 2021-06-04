package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.notification.NotificationService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "User's notifications", description = "Controller what provides end-points to work with user's notifications. Authentication is required.")
@RestController
@RequestMapping("/api/v1/user/notifications")
public class NotificationsController {

    private final UserService userService;
    private final NotificationService notificationService;

    public NotificationsController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Operation(summary = "Get user's notifications.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Set<Notification> getUserNotifications(Authentication authentication) {
        return userService.findById(authentication.getName()).getUser_metadata().getNotifications();
    }

    @Operation(summary = "Get user's notification specified by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "User doesn't have specified notification.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Notification getUserNotification(@Parameter(description = "ID of notification what will be found") @PathVariable("id") Long id, Authentication authentication) {
        return notificationService.findUserNotification(id, authentication.getName());
    }

    @Operation(summary = "Delete user's notification specified by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "User doesn't have specified notification.")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserNotification(@Parameter(description = "ID of notification what will be deleted") @PathVariable("id") Long id, Authentication authentication) {
        notificationService.delete(id, userService.findById(authentication.getName()));
        return new ResponseEntity<>("Notification deleted", new HttpHeaders(), HttpStatus.OK);
    }

}
