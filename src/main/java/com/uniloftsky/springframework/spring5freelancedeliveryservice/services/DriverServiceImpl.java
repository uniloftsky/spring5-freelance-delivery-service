package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final UserService userService;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper, UserService userService) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
        this.userService = userService;
    }

    @Override
    public DriverDTO findById(Long id) {
        return driverRepository.findById(id)
                .map(driverMapper::driverToDriverDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public DriverDTO findByUserId(String id) {
        Driver driver = driverRepository.findByUserId(id);
        return driverMapper.driverToDriverDTO(driver);
    }

    @Override
    public Set<DriverDTO> findAll() {
        return driverRepository.findAll()
                .stream()
                .map(driverMapper::driverToDriverDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public DriverDTO save(DriverDTO driverDTO) {
        Driver driver = driverMapper.driverDTOToDriver(driverDTO);
        return driverMapper.driverToDriverDTO(driverRepository.save(driver));
    }

    @Override
    public Driver save(Driver driver, User user) {
        save(driverMapper.driverToDriverDTO(driver));
        UserDTO userDTO = user.clone();
        userDTO.getUserMetadata().setDriver(true);
        userService.save(user, userDTO);
        return driver;
    }

    @Override
    public void delete(Long driverId) {
        driverRepository.delete(driverMapper.driverDTOToDriver(findById(driverId)));
    }

    @Override
    public void delete(Long driverId, User user) {
        driverRepository.delete(driverMapper.driverDTOToDriver(findById(driverId)));
        UserDTO userDTO = user.clone();
        userDTO.getUserMetadata().setDriver(false);
        userService.save(user, userDTO);
    }
}
