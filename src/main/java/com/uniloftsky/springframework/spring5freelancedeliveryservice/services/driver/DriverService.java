package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;

import java.util.Set;

public interface DriverService {

    Driver findById(Long id);

    Set<Driver> findAll();

    Driver save(Driver driver);

    Driver save(Driver driver, User user);

    DriverDTO patch(DriverDTO driverDTO, User user);

    void delete(Long driverId);

    void delete(Long driverId, User user);

    Driver getUserDriver(User user);

    AdvertisementDTO executingAdvertisement(Long advertisementId, User user);

    AdvertisementDTO respondOnAdvertisement(Long advertisementId, User user);

    AdvertisementDTO finishAdvertisement(Long advertisementId, User user);

}
