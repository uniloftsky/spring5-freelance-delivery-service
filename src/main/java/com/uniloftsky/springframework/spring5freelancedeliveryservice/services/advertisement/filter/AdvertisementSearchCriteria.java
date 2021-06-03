package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertisementSearchCriteria {

    private String title;
    private String[] types;
    private String deliver_from;
    private String deliver_to;
    private Integer max_price;
    private Integer min_price;
    private Status status;

}
