package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertisementSearchCriteria {

    private String title;
    private String[] types;
    private String deliverFrom;
    private String deliverTo;
    private Integer maxPrice;
    private Integer minPrice;
    private Status status;

}
