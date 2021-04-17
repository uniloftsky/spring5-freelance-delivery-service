package com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private LocalDateTime created_at;
    private String email;
    private boolean email_verified;
    private Set<Identity> identities;
    private String name;
    private String nickname;
    private String picture;
    private String user_id;
    private String family_name;
    private String given_name;

}
