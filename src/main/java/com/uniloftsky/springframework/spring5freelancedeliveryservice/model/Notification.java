package com.uniloftsky.springframework.spring5freelancedeliveryservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Notification extends BaseEntity {

    @JsonProperty("time_stamp")
    private LocalDate timeStamp;

    private String title;
    private String message;

    @JsonProperty("user_id")
    private String userId;
/*    @ManyToOne
    private User user;*/

}
