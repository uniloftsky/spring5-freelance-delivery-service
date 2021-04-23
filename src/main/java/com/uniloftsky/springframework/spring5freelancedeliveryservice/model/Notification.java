package com.uniloftsky.springframework.spring5freelancedeliveryservice.model;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Notification extends BaseEntity {

    private LocalDate timeStamp;
    private String title;
    private String message;

    private String userId;
/*    @ManyToOne
    private User user;*/

}
