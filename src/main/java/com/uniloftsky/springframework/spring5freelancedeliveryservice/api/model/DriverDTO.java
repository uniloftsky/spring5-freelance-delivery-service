package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class DriverDTO {

    private Long id;
    private String car;
    private Integer experience;
    private String description;
    private Set<TypeDTO> types = new HashSet<>();
    private Set<Advertisement> advertisements = new HashSet<>();
    private String user_id;

}
