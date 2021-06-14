package com.uniloftsky.springframework.spring5freelancedeliveryservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Advertisement extends BaseEntity {

    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Type> types = new HashSet<>();

    private String deliverFrom;
    private String deliverTo;

    @OneToOne(mappedBy = "advertisement", cascade = CascadeType.PERSIST)
    private Details details;

    @ManyToOne
    private Driver executor;

    @ElementCollection
    private Set<Long> responded = new HashSet<>();

    private Double price;
    private LocalDate date;
    private Integer period;
    private String description;
    private String phoneNumber;

    private Status status = Status.ACTIVE;
    private String userId;

    public void setDetails(Details details) {
        if (details != null) {
            details.setAdvertisement(this);
            this.details = details;
        }
    }
}
