package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;

public interface DriverMapper {

    DriverDTO driverToDriverDTO(Driver driver);
    Driver driverDTOToDriver(DriverDTO driverDTO);

}
