package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailsDTO {

    private Long id;
    private Integer height;
    private Integer width;
    private Integer length;
    private Integer weight;
    private AdvertisementDTO advertisement;

}
