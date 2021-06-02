package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class DriverDTO {

    private Long id;

    private Integer experience;

    @NotBlank
    private String description;

    @NotBlank
    private String name;

    @NotBlank
    private Set<TypeDTO> types = new HashSet<>();

    private Set<AdvertisementDTO> advertisements = new HashSet<>();

    @JsonProperty("user_id")
    private String userId;

}
