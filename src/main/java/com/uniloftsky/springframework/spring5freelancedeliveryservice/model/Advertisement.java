package com.uniloftsky.springframework.spring5freelancedeliveryservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Advertisement extends BaseEntity {

    private String title;

    @ManyToMany
    private Set<Type> types = new HashSet<>();

    private String deliverFrom;
    private String deliverTo;

    @OneToOne(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private Details details;

    @ManyToOne
    private Driver executor;

    private Integer price;
    private LocalDate date;
    private Integer period;
    private String description;
    private Status status = Status.ACTIVE;
    private String userId;

}
