package com.uniloftsky.springframework.spring5freelancedeliveryservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Driver extends BaseEntity {

    private Integer experience;
    private String description;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Type> types = new HashSet<>();

    @OneToMany(mappedBy = "executor", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<Advertisement> advertisements = new HashSet<>();

    private String userId;

}
