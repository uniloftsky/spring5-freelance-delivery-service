package com.uniloftsky.springframework.spring5freelancedeliveryservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Notification extends BaseEntity {

    private LocalDate timeStamp;
    private String title;
    private String message;

    @ManyToOne
    private User user;

}
