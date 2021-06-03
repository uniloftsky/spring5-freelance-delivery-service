package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class TypeDTO {

    public TypeDTO(String name) {
        this.name = name;
    }

    private Long id;

    @NotBlank
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
