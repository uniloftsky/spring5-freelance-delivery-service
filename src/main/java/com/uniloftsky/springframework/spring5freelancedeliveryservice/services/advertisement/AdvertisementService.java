package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;

import java.util.Set;

public interface AdvertisementService {

    Advertisement findById(Long id);

    Advertisement findUserAdvertisement(Long id, String userId);

    Set<Advertisement> findAll();

    Advertisement save(Advertisement advertisement);

    AdvertisementDTO save(Advertisement advertisement, User user);

    AdvertisementDTO patch(AdvertisementDTO advertisement, User user, Long id);

    void delete(Long advertisementId);

    void delete(Long advertisementId, User user);

    void refreshAdvertisementsType(Type type);

    AdvertisementDTO appointDriverToAdvertisement(Long advertisementId, Long driverId, String userId);

}
