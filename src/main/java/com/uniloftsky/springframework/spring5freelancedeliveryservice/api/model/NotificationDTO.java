package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificationDTO {

    private Long id;
    private LocalDate time_stamp;
    private String title;
    private String message;
    private String user_id;

}
