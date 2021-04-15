package com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Item extends BaseEntity {

    public Item() {}

    public Item(String name, Integer price, String description, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    private String name;
    private Integer price;
    private String description;
    private String image;

}
