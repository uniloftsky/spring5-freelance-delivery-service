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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private Set<Type> types;

    @NotBlank
    @JsonProperty("deliver_from")
    private String deliverFrom;

    @NotBlank
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

    @JsonProperty("user_id")
    private String userId;

}
