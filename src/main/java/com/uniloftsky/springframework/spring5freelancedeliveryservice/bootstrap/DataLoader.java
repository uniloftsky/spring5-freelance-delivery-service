package com.uniloftsky.springframework.spring5freelancedeliveryservice.bootstrap;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.NotificationMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.UserMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.*;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.UserMetadata;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.NotificationRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.TypeRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.UserRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final TypeRepository typeRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final AdvertisementService advertisementService;
    private final DriverService driverService;
    private final UserMapper userMapper;

    public DataLoader(TypeRepository typeRepository, UserRepository userRepository, NotificationMapper notificationMapper, NotificationRepository notificationRepository, NotificationService notificationService, AdvertisementService advertisementService, DriverService driverService, UserMapper userMapper) {
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
        this.advertisementService = advertisementService;
        this.driverService = driverService;
        this.userMapper = userMapper;
    }

    @Override
    public void run(String... args) throws InterruptedException {

        UserDTO userDTO = new UserDTO();
        UserMetadata userMetadata = new UserMetadata();
        userMetadata.setDriver(null);
        userMetadata.setAdvertisements(null);
        userMetadata.setNotifications(null);
        userDTO.setUserMetadata(userMetadata);
        userRepository.save(userRepository.findById("auth0|607d94db1c9629006daa7adf"), userDTO);

        Set<Type> types = new HashSet<>();
        types.add(new Type("Таксі"));
        types.add(new Type("Короткі відстані"));
        types.add(new Type("Довгі відстані"));
        types.add(new Type("Великі вантажі"));
        typeRepository.saveAll(types);

        Notification notification1 = new Notification(LocalDate.now(), "Notification1", "Notification message", "auth0|607d94db1c9629006daa7adf");
        notificationService.save(notification1, userRepository.findById("auth0|607d94db1c9629006daa7adf"));
        Driver driver = new Driver();
        driver.setDescription("I'm driver");
        driver.setExperience(10);
        driver.setTypes(types);
        driver.setUserId("auth0|607d94db1c9629006daa7adf");
        driverService.save(driver, userRepository.findById("auth0|607d94db1c9629006daa7adf"));

        Advertisement advertisement = Advertisement.builder().title("Deliver").date(LocalDate.now()).deliverFrom("Бердичів").deliverTo("Житомир").description("Desc")
                .types(types).price(100).status(Status.ACTIVE).build();
        advertisementService.save(advertisement, userRepository.findById("auth0|607d94db1c9629006daa7adf"));

        log.info("Saved advertisement1");
        Thread.sleep(1000);

        Advertisement advertisement1 = Advertisement.builder().date(LocalDate.now()).deliverFrom("Київ").deliverTo("Львів").description("Desc").title("Доставка")
                .types(types.stream().limit(2).collect(Collectors.toSet())).price(200).status(Status.ACTIVE).build();
        advertisementService.save(advertisement1, userRepository.findById("auth0|607d94db1c9629006daa7adf"));

        log.info("Saved advertisement2");
        Thread.sleep(1000);

        Advertisement advertisement2 = Advertisement.builder().date(LocalDate.now()).deliverFrom("Житомир").deliverTo("Козятин").description("Desc").title("Repo")
                .types(types.stream().skip(2).collect(Collectors.toSet())).price(1000).status(Status.ACTIVE).build();
        advertisementService.save(advertisement2, userRepository.findById("auth0|607d94db1c9629006daa7adf"));

        log.info("Saved advertisement3");
        Thread.sleep(1000);

        userDTO = new UserDTO();
        userMetadata = new UserMetadata();
        userMetadata.setDriver(null);
        userMetadata.setAdvertisements(null);
        userMetadata.setNotifications(null);
        userDTO.setUserMetadata(userMetadata);
        userRepository.save(userRepository.findById("auth0|60ae82c62f4b3000705f9717"), userDTO);

        Driver driver1 = new Driver();
        driver1.setDescription("I'm a uniloftsky driver");
        driver1.setExperience(20);
        driver1.setTypes(types.stream().skip(2).collect(Collectors.toSet()));
        driver1.setUserId("auth0|60ae82c62f4b3000705f9717");
        driverService.save(driver1, userRepository.findById("auth0|60ae82c62f4b3000705f9717"));

        log.info("Data loaded successfully");
    }
}
