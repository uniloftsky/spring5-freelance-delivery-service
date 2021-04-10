package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdvertisementMapper {

    AdvertisementMapper INSTANCE = Mappers.getMapper(AdvertisementMapper.class);

    AdvertisementDTO advertisementToAdvertisementDTO(Advertisement advertisement);
    Advertisement advertisementDTOToAdvertisement(AdvertisementDTO advertisementDTO);

}
