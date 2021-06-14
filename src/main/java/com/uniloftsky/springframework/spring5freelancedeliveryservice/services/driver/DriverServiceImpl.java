package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.AdvertisementMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.BadRequestException;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.ResourceNotFoundException;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.*;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.DriverRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.filter.DriverCriteriaRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.filter.DriverPage;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.filter.DriverSearchCriteria;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.notification.NotificationService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.type.TypeService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.utils.DTOHandler;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverCriteriaRepository driverCriteriaRepository;
    private final TypeService typeService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final AdvertisementService advertisementService;

    private final AdvertisementMapper advertisementMapper;
    private final DriverMapper driverMapper;

    public DriverServiceImpl(DriverRepository driverRepository, DriverCriteriaRepository driverCriteriaRepository, TypeService typeService, UserService userService, NotificationService notificationService, @Lazy AdvertisementService advertisementService, @Lazy AdvertisementMapper advertisementMapper, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverCriteriaRepository = driverCriteriaRepository;
        this.typeService = typeService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.advertisementService = advertisementService;
        this.advertisementMapper = advertisementMapper;
        this.driverMapper = driverMapper;
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
    public Set<AdvertisementDTO> findRecommendedAdvertisements(User user) {
        Driver driver = getUserDriver(user);
        return advertisementService.findAll().stream().filter(e -> {
            for (Type type : e.getTypes()) {
                if (driver.getTypes().contains(type)) {
                    return true;
                }
            }
            return false;
        }).map(advertisementMapper::advertisementToAdvertisementDTO).collect(Collectors.toSet());

    }

    @Override
    public Page<DriverDTO> filterDrivers(DriverPage driverPage, DriverSearchCriteria driverSearchCriteria) {
        return driverCriteriaRepository.findAllWithFilters(driverPage, driverSearchCriteria);
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
            return resaveDriverToUser(driver, user);
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
        changeStatusOfAdvertisementWithNotification(driver, advertisement, Status.IN_PROCESS, Notification.builder()
                .message("Водій " + driver.getName() + " приступив до виконання замовлення '" + advertisement.getTitle() + "'.")
                .title("Ваше замовлення '" + advertisement.getTitle() + "' виконується!")
                .build(), user);
        return advertisementMapper.advertisementToAdvertisementDTO(advertisement);
    }

    @Override
    public AdvertisementDTO respondOnAdvertisement(Long advertisementId, User user) {
        Driver driver = getUserDriver(user);
        Advertisement advertisement = advertisementService.findById(advertisementId);
        User client = userService.findById(advertisement.getUserId());
        advertisement.getResponded().add(driver.getId());
        advertisementService.save(advertisement, client);
        DTOHandler.createNotificationOnEvent(client, Notification.builder().message("Водій " + driver.getName() + " відгукнувся на ваше замовлення.").title("Новий відгук на замовлення!").build(), notificationService);
        return advertisementMapper.advertisementToAdvertisementDTO(advertisement);
    }

    @Override
    public AdvertisementDTO finishAdvertisement(Long advertisementId, User user) {
        Driver driver = getUserDriver(user);
        Advertisement advertisement = findDriverAdvertisement(advertisementId, driver);
        changeStatusOfAdvertisementWithNotification(driver, advertisement, Status.READY, Notification.builder()
                .message("Водій " + driver.getName() + " виконав ваше замовлення: '" + advertisement.getTitle() + "'.")
                .title("Ваше замовлення виконано!")
                .build(), user);
        return advertisementMapper.advertisementToAdvertisementDTO(advertisement);
    }

    private Driver resaveDriverToUser(Driver driver, User user) {
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

    private void changeStatusOfAdvertisementWithNotification(Driver driver, Advertisement advertisement, Status status, Notification notification, User user) {
        User client = userService.findById(advertisement.getUserId());
        DTOHandler.createNotificationOnEvent(client, notification, notificationService);
        advertisement.setStatus(status);
        advertisementService.save(advertisement, client);
        save(driver, user);
    }

}
