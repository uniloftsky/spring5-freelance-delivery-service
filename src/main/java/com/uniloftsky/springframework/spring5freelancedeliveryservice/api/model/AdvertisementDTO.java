package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AdvertisementDTO {

    private Long id;
    private String title;
    private Set<TypeDTO> types = new HashSet<>();
    private String deliverFrom;
    private String deliverTo;
    private DetailsDTO details;
    private DriverDTO executor;
    private Integer price;
    private LocalDate date;
    private Integer period;
    private String description;

}
