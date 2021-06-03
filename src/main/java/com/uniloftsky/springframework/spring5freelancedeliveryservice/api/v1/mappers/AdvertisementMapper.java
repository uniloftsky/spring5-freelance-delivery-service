package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;

public interface AdvertisementMapper {

    AdvertisementDTO advertisementToAdvertisementDTO(Advertisement advertisement);
    Advertisement advertisementDTOToAdvertisement(AdvertisementDTO advertisementDTO);

}
