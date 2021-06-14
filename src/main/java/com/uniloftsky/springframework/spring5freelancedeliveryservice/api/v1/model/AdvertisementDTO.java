package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Status;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    private DetailsDTO details = new DetailsDTO();

    @JsonProperty("driver_id")
    private Long driverId;

    private Set<Long> responded = new HashSet<>();

    private Double price;
    private LocalDate date;
    private Integer period;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank
    private String description;

    private Status status = Status.ACTIVE;

    @JsonProperty("user_id")
    private String userId;

}
