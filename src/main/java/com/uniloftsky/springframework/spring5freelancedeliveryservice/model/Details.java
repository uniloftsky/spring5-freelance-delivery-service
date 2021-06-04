package com.uniloftsky.springframework.spring5freelancedeliveryservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Details extends BaseEntity {

    private Integer height;
    private Integer width;
    private Integer length;
    private Integer weight;
    private Integer peopleCount;

    @OneToOne
    private Advertisement advertisement;

}
