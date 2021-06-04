package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("people_count")
    private Integer peopleCount;

    @JsonProperty("advertisement_id")
    private Long advertisementId;

}
