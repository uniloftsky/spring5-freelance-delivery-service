package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;

public interface DriverMapper {

    DriverDTO driverToDriverDTO(Driver driver);
    Driver driverDTOToDriver(DriverDTO driverDTO);

}
