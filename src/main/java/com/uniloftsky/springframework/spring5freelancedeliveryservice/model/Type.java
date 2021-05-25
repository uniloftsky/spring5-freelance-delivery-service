package com.uniloftsky.springframework.spring5freelancedeliveryservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Type extends BaseEntity {

    public Type(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return Objects.equals(name, type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
