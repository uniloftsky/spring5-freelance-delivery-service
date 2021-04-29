package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;

import java.util.Set;

public interface DriverService {

    DriverDTO findById(Long id);

    DriverDTO findByUserId(String id);

    Set<DriverDTO> findAll();

    DriverDTO save(DriverDTO driverDTO);

    Driver save(Driver driver, User user);

    void delete(Long driverId);

    void delete(Long driverId, User user);

}
