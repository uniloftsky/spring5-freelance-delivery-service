package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DetailsDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Details;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.DriverService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdvertisementMapperImpl implements AdvertisementMapper {

    private final DriverService driverService;
    private final AdvertisementService advertisementService;

    public AdvertisementMapperImpl(DriverService driverService, @Lazy AdvertisementService advertisementService) {
        this.driverService = driverService;
        this.advertisementService = advertisementService;
    }

    public AdvertisementDTO advertisementToAdvertisementDTO(Advertisement advertisement) {
        if (advertisement == null) {
            return null;
        } else {
            AdvertisementDTO advertisementDTO = new AdvertisementDTO();
            advertisementDTO.setDeliverFrom(advertisement.getDeliverFrom());
            advertisementDTO.setDeliverTo(advertisement.getDeliverTo());
            if (advertisement.getExecutor() != null) {
                advertisementDTO.setDriverId(advertisement.getExecutor().getId());
            }
            advertisementDTO.setResponded(advertisement.getResponded());
            advertisementDTO.setId(advertisement.getId());
            advertisementDTO.setTitle(advertisement.getTitle());
            Set<Type> set = advertisement.getTypes();
            if (set != null) {
                advertisementDTO.setTypes(new HashSet(set));
            }

            advertisementDTO.setDetails(detailsToDetailsDTO(advertisement.getDetails()));
            advertisementDTO.setPrice(advertisement.getPrice());
            advertisementDTO.setDate(advertisement.getDate());
            advertisementDTO.setPeriod(advertisement.getPeriod());
            advertisementDTO.setDescription(advertisement.getDescription());
            advertisementDTO.setStatus(advertisement.getStatus());
            advertisementDTO.setUserId(advertisement.getUserId());
            return advertisementDTO;
        }
    }

    public Advertisement advertisementDTOToAdvertisement(AdvertisementDTO advertisementDTO) {
        if (advertisementDTO == null) {
            return null;
        } else {
            Advertisement advertisement = new Advertisement();
            if (advertisementDTO.getDriverId() != null) {
                advertisement.setExecutor(driverService.findById(advertisementDTO.getDriverId()));
            }
            advertisement.setResponded(advertisementDTO.getResponded());
            advertisement.setDeliverFrom(advertisementDTO.getDeliverFrom());
            advertisement.setDeliverTo(advertisementDTO.getDeliverTo());
            advertisement.setId(advertisementDTO.getId());
            advertisement.setTitle(advertisementDTO.getTitle());
            Set<Type> set = advertisementDTO.getTypes();
            if (set != null) {
                advertisement.setTypes(new HashSet(set));
            }
            if (advertisementDTO.getDetails() != null) {
                advertisement.setDetails(detailsDTOToDetails(advertisementDTO.getDetails()));
            }

            advertisement.setPrice(advertisementDTO.getPrice());
            advertisement.setDate(advertisementDTO.getDate());
            advertisement.setPeriod(advertisementDTO.getPeriod());
            advertisement.setDescription(advertisementDTO.getDescription());
            advertisement.setStatus(advertisementDTO.getStatus());
            advertisement.setUserId(advertisementDTO.getUserId());
            return advertisement;
        }
    }

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
            if(detailsDTO.getAdvertisementId() != null) {
                details.setAdvertisement(advertisementService.findById(detailsDTO.getAdvertisementId()));
            }
            return details;
        }
    }
}
