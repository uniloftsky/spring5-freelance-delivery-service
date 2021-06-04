package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "Controller what provides end-points to work with user's notifications. Authentication is required.")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public User getUser(Authentication authentication) {
        return userService.findById(authentication.getName());
    }

    @Operation(summary = "Patch user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public User patchUser(@Parameter(description = "Fields of user to patch") @RequestBody UserDTO userDTO, Authentication authentication) {
        return userService.save(userService.findById(authentication.getName()), userDTO);
    }
}