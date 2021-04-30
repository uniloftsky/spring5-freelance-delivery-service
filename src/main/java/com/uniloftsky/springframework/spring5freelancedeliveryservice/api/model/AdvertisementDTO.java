package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Details;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Status;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO {

    private String title;
    private Set<Type> types = new HashSet<>();
    private String deliverFrom;
    private String deliverTo;
    private Details details;
    private Driver executor;
    private Integer price;
    private LocalDate date;
    private Integer period;
    private String description;
    private Status status = Status.ACTIVE;

}
