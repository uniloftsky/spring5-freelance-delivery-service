package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.UserMetadata;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.familyName = user.getFamily_name();
        this.givenName = user.getGiven_name();
        this.picture = user.getPicture();
        if (user.getUser_metadata() == null) {
            this.userMetadata = new UserMetadata();
        } else {
            this.userMetadata = user.getUser_metadata();
        }
    }

    private String email;

    @JsonProperty("email_verified")
    private boolean emailVerified;

    @JsonProperty("user_metadata")
    private UserMetadata userMetadata;

    private String name;
    private String nickname;
    private String picture;

    @JsonProperty("family_name")
    private String familyName;

    @JsonProperty("given_name")
    private String givenName;

}
