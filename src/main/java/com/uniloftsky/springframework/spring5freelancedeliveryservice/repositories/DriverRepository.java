package com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByUserId(String id);

}
