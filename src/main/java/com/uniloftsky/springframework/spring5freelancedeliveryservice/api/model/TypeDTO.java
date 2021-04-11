package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TypeDTO {

    public TypeDTO(String name) {
        this.name = name;
    }

    private Long id;
    private String name;

}
