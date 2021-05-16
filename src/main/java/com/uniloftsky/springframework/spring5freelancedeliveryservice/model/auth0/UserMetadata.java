package com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMetadata {

    private Set<Notification> notifications = new HashSet<>();
    private Set<Advertisement> advertisements = new HashSet<>();
    private Driver driver;


}
