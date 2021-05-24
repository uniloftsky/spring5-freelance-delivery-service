package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {

    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);

    DriverDTO driverToDriverDTO(Driver driver);

    Driver driverDTOToDriver(DriverDTO driverDTO);

}
