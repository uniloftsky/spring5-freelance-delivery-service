package com.uniloftsky.springframework.spring5freelancedeliveryservice.bootstrap;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.NotificationMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.UserMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.UserMetadata;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.NotificationRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.TypeRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.UserRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.NotificationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    public void run(String... args) {

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
/*        Driver driver = new Driver();
        driver.setDescription("I'm driver");
        driver.setExperience(10);
        driver.setTypes(types);
        driver.setUserId("auth0|607d94db1c9629006daa7adf");
        driverService.save(driver, userRepository.findById("auth0|607d94db1c9629006daa7adf"));*/

/*        Advertisement advertisement1 = new Advertisement();
        advertisement1.setDate(LocalDate.now());
        advertisement1.setDeliverFrom("Point1");
        advertisement1.setDeliverTo("Point2");
        advertisement1.setDescription("Desc");
        advertisement1.setTitle("Delivery");
        advertisement1.setTypes(types);
        advertisementService.save(advertisement1, userRepository.findById("auth0|607d94db1c9629006daa7adf"));*/

    }
}
