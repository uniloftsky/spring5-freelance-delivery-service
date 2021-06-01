package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.DriverService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdvertisementMapperImpl implements AdvertisementMapper {

    private final DriverService driverService;

    public AdvertisementMapperImpl(DriverService driverService) {
        this.driverService = driverService;
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

            advertisementDTO.setDetails(advertisement.getDetails());
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

            advertisement.setDetails(advertisementDTO.getDetails());
            advertisement.setPrice(advertisementDTO.getPrice());
            advertisement.setDate(advertisementDTO.getDate());
            advertisement.setPeriod(advertisementDTO.getPeriod());
            advertisement.setDescription(advertisementDTO.getDescription());
            advertisement.setStatus(advertisementDTO.getStatus());
            advertisement.setUserId(advertisementDTO.getUserId());
            return advertisement;
        }
    }
}
