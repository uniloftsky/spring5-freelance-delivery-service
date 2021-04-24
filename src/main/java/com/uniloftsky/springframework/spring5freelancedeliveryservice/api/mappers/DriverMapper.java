package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {

    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);

    @Mapping(source = "userId", target = "user_id")
    DriverDTO driverToDriverDTO(Driver driver);

    @Mapping(source = "user_id", target = "userId")
    Driver driverDTOToDriver(DriverDTO driverDTO);

}
