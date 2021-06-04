package com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @EntityGraph(attributePaths = {"types", "executor", "executor.types", "executor.advertisements", "details"})
    @Override
    List<Advertisement> findAll();

    @EntityGraph(attributePaths = {"types", "executor", "executor.types", "executor.advertisements", "details"})
    @Override
    Optional<Advertisement> findById(Long aLong);
}
