package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Details;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Status;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO {

    private Long id;
    private String title;
    private Set<Type> types;
    private String deliver_from;
    private String deliver_to;
    private Details details;
    private Long driverId;
    private Integer price;
    private LocalDate date;
    private Integer period;
    private String description;
    private Status status = Status.ACTIVE;

}
