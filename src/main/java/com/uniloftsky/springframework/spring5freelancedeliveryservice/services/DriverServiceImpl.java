package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.TypeMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.BadRequestException;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.ResourceNotFoundException;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.DriverRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.utils.FieldsHandler;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final TypeMapper typeMapper;
    private final TypeService typeService;
    private final UserService userService;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper, TypeMapper typeMapper, TypeService typeService, UserService userService) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
        this.typeMapper = typeMapper;
        this.typeService = typeService;
        this.userService = userService;
    }

    @Override
    public Driver findById(Long id) {
        Optional<Driver> driverOptional = driverRepository.findById(id);
        if (driverOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find driver with ID: " + id);
        }
        return driverOptional.get();
    }

    @Override
    public Set<Driver> findAll() {
        return new HashSet<>(driverRepository.findAll());
    }

    @Override
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver save(Driver driver, User user) {
        if (user.getUser_metadata().getDriver() != null && driver.getId() == null) {
            throw new BadRequestException("Given user is already a driver!");
        } else {
            driver = handleDriver(driver, user);
            UserDTO userDTO = user.clone();
            userDTO.getUserMetadata().setDriver(driverMapper.driverToDriverDTO(driver));
            userService.save(user, userDTO);
            return driver;
        }
    }

    @Override
    public DriverDTO patch(DriverDTO driverDTO, User user) {
        DriverDTO patchedDriver = driverMapper.driverToDriverDTO(getUserDriver(user));
        FieldsHandler.handleFields(driverDTO, patchedDriver);
        return driverMapper.driverToDriverDTO(save(driverMapper.driverDTOToDriver(patchedDriver), user));
    }

    @Override
    public void delete(Long driverId) {
        driverRepository.delete(findById(driverId));
    }

    @Override
    public void delete(Long driverId, User user) {
        driverRepository.delete(findById(driverId));
        UserDTO userDTO = user.clone();
        userDTO.getUserMetadata().setDriver(null);
        userService.save(user, userDTO);
    }

    @Override
    public Driver getUserDriver(User user) {
        Optional<Driver> driverOptional = Optional.ofNullable(findById(user.getUser_metadata().getDriver().getId()));
        if (driverOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find an expected user-driver. Is given user a driver?");
        } else {
            return driverOptional.get();
        }
    }

    private Driver handleDriver(Driver driver, User user) {
        Set<Type> types = new HashSet<>();
        for (Type type : driver.getTypes()) {
            types.add(typeMapper.typeDTOToType(typeService.findById(type.getId())));
        }
        driver.setUserId(user.getUser_id());
        driver.getTypes().clear();
        driver.getTypes().addAll(types);
        return driverRepository.save(driver);
    }
}
