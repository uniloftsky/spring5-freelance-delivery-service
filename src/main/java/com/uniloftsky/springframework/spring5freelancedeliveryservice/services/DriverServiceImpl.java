package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.AdvertisementMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.BadRequestException;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.ResourceNotFoundException;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Status;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.DriverRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.utils.DTOHandler;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final TypeService typeService;
    private final UserService userService;
    private final NotificationService notificationService;

    private final AdvertisementMapper advertisementMapper;
    private final AdvertisementService advertisementService;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper, TypeService typeService, UserService userService, NotificationService notificationService, @Lazy AdvertisementMapper advertisementMapper, @Lazy AdvertisementService advertisementService) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
        this.typeService = typeService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.advertisementMapper = advertisementMapper;
        this.advertisementService = advertisementService;
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
            return handleDriver(driver, user);
        }
    }

    @Override
    public DriverDTO patch(DriverDTO driverDTO, User user) {
        DriverDTO patchedDriver = driverMapper.driverToDriverDTO(getUserDriver(user));
        DTOHandler.handleFields(driverDTO, patchedDriver);
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
        Optional<DriverDTO> driverOptional = Optional.ofNullable(user.getUser_metadata().getDriver());
        if (driverOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find an expected user-driver. Is given user a driver?");
        } else {
            return driverMapper.driverDTOToDriver(driverOptional.get());
        }
    }

    @Override
    public AdvertisementDTO executingAdvertisement(Long advertisementId, User user) {
        Driver driver = getUserDriver(user);
        Advertisement advertisement = findDriverAdvertisement(advertisementId, driver);
        User client = userService.findById(advertisement.getUserId());
        DTOHandler.createNotificationOnEvent(client, "Ваше замовлення '" + advertisement.getTitle() + "' виконується!", "Водій " + driver.getName() + " приступив до виконання замовлення '" + advertisement.getTitle() + "'.", notificationService);
        advertisement.setStatus(Status.IN_PROCESS);
        advertisementService.save(advertisement, client);
        save(driver, user);
        return advertisementMapper.advertisementToAdvertisementDTO(advertisement);
    }

    @Override
    public AdvertisementDTO respondOnAdvertisement(Long advertisementId, User user) {
        Driver driver = getUserDriver(user);
        Advertisement advertisement = advertisementService.findById(advertisementId);
        User client = userService.findById(advertisement.getUserId());
        DTOHandler.createNotificationOnEvent(client, "Новий відгук на замовлення!", "Водій " + driver.getName() + " відгукнувся на ваше замовлення.", notificationService);
        advertisement.getResponded().add(getRespondedDriverJSON(driver));
        advertisementService.save(advertisement, client);
        return advertisementMapper.advertisementToAdvertisementDTO(advertisement);
    }

    private Driver handleDriver(Driver driver, User user) {
        DTOHandler.patchAdvertisementTypes(driver, user, typeService);
        driverRepository.save(driver);
        UserDTO userDTO = user.clone();
        userDTO.getUserMetadata().setDriver(driverMapper.driverToDriverDTO(driver));
        userService.save(user, userDTO);
        return driver;
    }

    private Advertisement findDriverAdvertisement(Long advertisementId, Driver driver) {
        Optional<Advertisement> advertisementOptional = driver.getAdvertisements().stream()
                .filter(e -> e.getId().equals(advertisementId)).findFirst();
        if (advertisementOptional.isEmpty()) {
            throw new ResourceNotFoundException("Driver with ID: " + driver.getId() + " has not expected advertisement with ID: " + advertisementId);
        }
        return advertisementOptional.get();
    }

    private JSONObject getRespondedDriverJSON(Driver driver) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", driver.getId());
        jsonObject.put("name", driver.getName());
        jsonObject.put("description", driver.getDescription());
        jsonObject.put("experience", driver.getExperience());
        jsonObject.put("types", driver.getTypes());
        return jsonObject;
    }

}
