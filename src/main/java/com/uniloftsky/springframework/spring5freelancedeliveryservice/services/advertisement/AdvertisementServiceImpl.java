package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.AdvertisementMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.BadRequestException;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.ResourceNotFoundException;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Status;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.AdvertisementRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.notification.NotificationService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.type.TypeService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.utils.DTOHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final TypeService typeService;
    private final UserService userService;
    private final DriverService driverService;
    private final AdvertisementMapper advertisementMapper;
    private final NotificationService notificationService;

    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, AdvertisementMapper advertisementMapper, TypeService typeService, UserService userService, DriverService driverService, NotificationService notificationService) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementMapper = advertisementMapper;
        this.typeService = typeService;
        this.userService = userService;
        this.driverService = driverService;
        this.notificationService = notificationService;
    }

    @Override
    public Advertisement findById(Long id) {
        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
        if (advertisementOptional.isEmpty()) {
            throw new ResourceNotFoundException("Advertisement with ID: " + id + " not found!");
        }
        return advertisementOptional.get();
    }

    @Override
    public Advertisement findUserAdvertisement(Long id, String userId) {
        Optional<AdvertisementDTO> advertisementDTOOptional = userService.findById(userId).getUser_metadata().getAdvertisements()
                .stream().filter(e -> e.getId().equals((id))).findFirst();
        if (advertisementDTOOptional.isEmpty()) {
            throw new ResourceNotFoundException("User has not expected advertisement with ID: " + id);
        } else {
            AdvertisementDTO foundAdvertisementDTO = advertisementDTOOptional.get();
            return advertisementMapper.advertisementDTOToAdvertisement(foundAdvertisementDTO);
        }
    }

    @Override
    public Set<Advertisement> findAll() {
        Set<Advertisement> advertisements = new HashSet<>();
        advertisementRepository.findAll().iterator().forEachRemaining(advertisements::add);
        return advertisements;
    }

    @Override
    public Advertisement save(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    @Override
    public AdvertisementDTO save(Advertisement advertisement, User user) {
        return advertisementMapper.advertisementToAdvertisementDTO(
                handleAdvertisement(advertisement, user)
        );
    }

    @Override
    public AdvertisementDTO patch(AdvertisementDTO advertisementDTO, User user, Long id) {
        Advertisement patchedAdvertisement = findById(id);
        AdvertisementDTO patchedAdvertisementDTO = advertisementMapper.advertisementToAdvertisementDTO(patchedAdvertisement);
        DTOHandler.handleFields(advertisementDTO, patchedAdvertisementDTO);
        patchedAdvertisement = advertisementMapper.advertisementDTOToAdvertisement(patchedAdvertisementDTO);
        save(patchedAdvertisement, user);
        return advertisementMapper.advertisementToAdvertisementDTO(patchedAdvertisement);
    }

    @Override
    public void delete(Long advertisementId) {
        advertisementRepository.delete(findById(advertisementId));
    }

    @Override
    public void delete(Long advertisementId, User user) {
        UserDTO userDTO = user.clone();
        userDTO.getUserMetadata().getAdvertisements().removeIf(e -> e.getId().equals(advertisementId));
        userService.save(user, userDTO);
        advertisementRepository.delete(findById(advertisementId));
    }

    @Override
    public void refreshAdvertisementsType(Type type) {
        Set<Advertisement> advertisements = findAll().stream().filter(ad -> {
            long count = ad.getTypes().stream().filter(t -> t.getId().equals(type.getId())).count();
            return count > 0;
        }).collect(Collectors.toSet());
        for (Advertisement advertisement : advertisements) {
            save(advertisement, userService.findById(advertisement.getUserId()));
        }
    }

    @Override
    public AdvertisementDTO appointDriverToAdvertisement(Long advertisementId, Long driverId, String userId) {
        if (userService.findById(userId).getUser_metadata().getDriver().getId().equals(driverId)) {
            throw new BadRequestException("User cannot execute order what belongs to him!");
        }
        Advertisement advertisement = findUserAdvertisement(advertisementId, userId);
        Driver driver = driverService.findById(driverId);
        advertisement.setExecutor(driver);
        advertisement.setStatus(Status.APPOINTED);
        driver.getAdvertisements().add(advertisement);
        driverService.save(driver, userService.findById(driver.getUserId()));
        User client = userService.findById(driver.getUserId());
        DTOHandler.createNotificationOnEvent(client, "Вас назначили на нове замовлення!", "Вам назначено замовлення '" + advertisement.getTitle() + "'.", notificationService);
        save(advertisement, userService.findById(userId));
        return advertisementMapper.advertisementToAdvertisementDTO(advertisement);
    }

    private Advertisement handleAdvertisement(Advertisement advertisement, User user) {
        DTOHandler.patchAdvertisementTypes(advertisement, user, typeService);
        advertisement.setUserId(user.getUser_id());
        if (advertisement.getDate() == null) {
            advertisement.setDate(LocalDate.now());
        }
        advertisementRepository.save(advertisement);
        UserDTO userDTO = user.clone();
        userDTO.getUserMetadata().getAdvertisements().removeIf(e -> e.getId().equals(advertisement.getId()));
        userDTO.getUserMetadata().getAdvertisements().add(advertisementMapper.advertisementToAdvertisementDTO(advertisement));
        userService.save(user, userDTO);
        return advertisement;
    }

}
