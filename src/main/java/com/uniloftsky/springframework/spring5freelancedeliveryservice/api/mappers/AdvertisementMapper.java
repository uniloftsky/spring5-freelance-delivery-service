package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;

public interface AdvertisementMapper {

    AdvertisementDTO advertisementToAdvertisementDTO(Advertisement advertisement);
    Advertisement advertisementDTOToAdvertisement(AdvertisementDTO advertisementDTO);

}
