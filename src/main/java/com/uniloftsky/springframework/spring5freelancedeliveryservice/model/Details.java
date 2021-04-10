package com.uniloftsky.springframework.spring5freelancedeliveryservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Details extends BaseEntity {

    private Integer height;
    private Integer width;
    private Integer length;
    private Integer weight;

    @OneToOne
    private Advertisement advertisement;

}
