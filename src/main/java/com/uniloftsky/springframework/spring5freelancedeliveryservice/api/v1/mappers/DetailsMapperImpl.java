package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DetailsDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Details;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import org.springframework.stereotype.Component;

@Component
public class DetailsMapperImpl implements DetailsMapper {

    private final AdvertisementService advertisementService;

    public DetailsMapperImpl(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @Override
    public DetailsDTO detailsToDetailsDTO(Details details) {
        if (details == null) {
            return null;
        } else {
            DetailsDTO detailsDTO = new DetailsDTO();
            detailsDTO.setId(details.getId());
            detailsDTO.setHeight(details.getHeight());
            detailsDTO.setWidth(details.getWidth());
            detailsDTO.setLength(details.getLength());
            detailsDTO.setWeight(details.getWeight());
            detailsDTO.setPeopleCount(details.getPeopleCount());
            detailsDTO.setAdvertisementId(details.getAdvertisement().getId());
            return detailsDTO;
        }
    }

    @Override
    public Details detailsDTOToDetails(DetailsDTO detailsDTO) {
        if (detailsDTO == null) {
            return null;
        } else {
            Details details = new Details();
            details.setId(detailsDTO.getId());
            details.setHeight(detailsDTO.getHeight());
            details.setWidth(detailsDTO.getWidth());
            details.setLength(detailsDTO.getLength());
            details.setWeight(detailsDTO.getWeight());
            details.setPeopleCount(detailsDTO.getPeopleCount());
            details.setAdvertisement(advertisementService.findById(detailsDTO.getAdvertisementId()));
            return details;
        }
    }
}
