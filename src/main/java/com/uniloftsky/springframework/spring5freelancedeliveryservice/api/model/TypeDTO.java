package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class TypeDTO {

    public TypeDTO(String name) {
        this.name = name;
    }

    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeDTO typeDTO = (TypeDTO) o;
        return Objects.equals(id, typeDTO.id) &&
                Objects.equals(name, typeDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
