package com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @EntityGraph(attributePaths = {"advertisements", "types"})
    Optional<Driver> findByUserId(String id);

    @EntityGraph(attributePaths = {"advertisements", "types"})
    @Override
    List<Driver> findAll();

    @EntityGraph(attributePaths = {"advertisements", "types"})
    @Override
    Optional<Driver> findById(Long aLong);
}
