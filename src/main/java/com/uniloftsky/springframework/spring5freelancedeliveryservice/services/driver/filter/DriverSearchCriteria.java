package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverSearchCriteria {

    private String name;
    private String[] types;
    private Integer min_experience;
    private Integer max_experience;

}
