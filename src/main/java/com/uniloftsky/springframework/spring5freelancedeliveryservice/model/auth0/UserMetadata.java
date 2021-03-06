package com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DriverDTO;
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
    private Set<AdvertisementDTO> advertisements = new HashSet<>();
    private DriverDTO driver;


}
