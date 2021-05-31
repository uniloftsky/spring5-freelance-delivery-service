package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Details;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Status;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;

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

    @JsonProperty("deliver_from")
    private String deliverFrom;

    @JsonProperty("deliver_to")
    private String deliverTo;

    private Details details;

    @JsonProperty("driver_id")
    private Long driverId;

    private JSONArray responded = new JSONArray();

    private Integer price;
    private LocalDate date;
    private Integer period;
    private String description;
    private Status status = Status.ACTIVE;

}
