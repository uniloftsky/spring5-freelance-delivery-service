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
        this.family_name = user.getFamily_name();
        this.given_name = user.getGiven_name();
        this.picture = user.getPicture();
        if (user.getUser_metadata() == null) {
            this.userMetadata = new UserMetadata();
        } else {
            this.userMetadata = user.getUser_metadata();
        }
    }

    private String email;
    private boolean email_verified;

    @JsonProperty("user_metadata")
    private UserMetadata userMetadata;

    private String name;
    private String nickname;
    private String picture;
    private String family_name;
    private String given_name;

}
