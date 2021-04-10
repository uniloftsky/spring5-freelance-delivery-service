package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Set<NotificationDTO> notifications = new HashSet<>();

}
