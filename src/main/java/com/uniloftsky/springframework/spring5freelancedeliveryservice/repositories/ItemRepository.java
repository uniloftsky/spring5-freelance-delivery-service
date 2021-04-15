package com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
