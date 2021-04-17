package com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Identity {

    private String connection;
    private String provider;
    private String user_id;
    private boolean isSocial;

}
