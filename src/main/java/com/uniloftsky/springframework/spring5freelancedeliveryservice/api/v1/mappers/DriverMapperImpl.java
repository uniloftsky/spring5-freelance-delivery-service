package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.TypeDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.DriverService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class DriverMapperImpl implements DriverMapper {

    private final DriverService driverService;

    public DriverMapperImpl(@Lazy DriverService driverService) {
        this.driverService = driverService;
    }

    public DriverDTO driverToDriverDTO(Driver driver) {
        if (driver == null) {
            return null;
        } else {
            DriverDTO driverDTO = new DriverDTO();
            driverDTO.setId(driver.getId());
            driverDTO.setExperience(driver.getExperience());
            driverDTO.setDescription(driver.getDescription());
            driverDTO.setName(driver.getName());
            driverDTO.setTypes(this.typeSetToTypeDTOSet(driver.getTypes()));
            driverDTO.setAdvertisements(this.advertisementSetToAdvertisementDTOSet(driver.getAdvertisements()));
            driverDTO.setUserId(driver.getUserId());
            return driverDTO;
        }
    }

    public Driver driverDTOToDriver(DriverDTO driverDTO) {
        if (driverDTO == null) {
            return null;
        } else {
            Driver driver = new Driver();
            driver.setId(driverDTO.getId());
            driver.setExperience(driverDTO.getExperience());
            driver.setDescription(driverDTO.getDescription());
            driver.setName(driverDTO.getName());
            driver.setTypes(this.typeDTOSetToTypeSet(driverDTO.getTypes()));
            driver.setAdvertisements(this.advertisementDTOSetToAdvertisementSet(driverDTO.getAdvertisements()));
            driver.setUserId(driverDTO.getUserId());
            return driver;
        }
    }

    protected TypeDTO typeToTypeDTO(Type type) {
        if (type == null) {
            return null;
        } else {
            TypeDTO typeDTO = new TypeDTO();
            typeDTO.setId(type.getId());
            typeDTO.setName(type.getName());
            return typeDTO;
        }
    }

    protected Set<TypeDTO> typeSetToTypeDTOSet(Set<Type> set) {
        if (set == null) {
            return null;
        } else {
            Set<TypeDTO> set1 = new HashSet(Math.max((int) ((float) set.size() / 0.75F) + 1, 16));
            Iterator var3 = set.iterator();

            while (var3.hasNext()) {
                Type type = (Type) var3.next();
                set1.add(this.typeToTypeDTO(type));
            }

            return set1;
        }
    }

    protected Set<AdvertisementDTO> advertisementSetToAdvertisementDTOSet(Set<Advertisement> set) {
        if (set == null) {
            return null;
        } else {
            Set<AdvertisementDTO> set1 = new HashSet(Math.max((int) ((float) set.size() / 0.75F) + 1, 16));
            Iterator var3 = set.iterator();

            while (var3.hasNext()) {
                Advertisement advertisement = (Advertisement) var3.next();
                set1.add(this.advertisementToAdvertisementDTO(advertisement));
            }

            return set1;
        }
    }

    protected Type typeDTOToType(TypeDTO typeDTO) {
        if (typeDTO == null) {
            return null;
        } else {
            Type type = new Type();
            type.setId(typeDTO.getId());
            type.setName(typeDTO.getName());
            return type;
        }
    }

    protected Set<Type> typeDTOSetToTypeSet(Set<TypeDTO> set) {
        if (set == null) {
            return null;
        } else {
            Set<Type> set1 = new HashSet<>(Math.max((int) ((float) set.size() / 0.75F) + 1, 16));

            for (TypeDTO typeDTO : set) {
                set1.add(this.typeDTOToType(typeDTO));
            }

            return set1;
        }
    }

    protected Set<Advertisement> advertisementDTOSetToAdvertisementSet(Set<AdvertisementDTO> set) {
        if (set == null) {
            return null;
        } else {
            Set<Advertisement> set1 = new HashSet<>(Math.max((int) ((float) set.size() / 0.75F) + 1, 16));

            for (AdvertisementDTO advertisementDTO : set) {
                set1.add(this.advertisementDTOToAdvertisement(advertisementDTO));
            }

            return set1;
        }
    }

    protected AdvertisementDTO advertisementToAdvertisementDTO(Advertisement advertisement) {
        if (advertisement == null) {
            return null;
        } else {
            AdvertisementDTO advertisementDTO = new AdvertisementDTO();
            advertisementDTO.setDeliverFrom(advertisement.getDeliverFrom());
            advertisementDTO.setDeliverTo(advertisement.getDeliverTo());
            if (advertisement.getExecutor() != null) {
                advertisementDTO.setDriverId(advertisement.getExecutor().getId());
            }
            advertisementDTO.setId(advertisement.getId());
            advertisementDTO.setTitle(advertisement.getTitle());
            Set<Type> set = advertisement.getTypes();
            if (set != null) {
                advertisementDTO.setTypes(new HashSet<>(set));
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

    protected Advertisement advertisementDTOToAdvertisement(AdvertisementDTO advertisementDTO) {
        if (advertisementDTO == null) {
            return null;
        } else {
            Advertisement advertisement = new Advertisement();
            if (advertisementDTO.getDriverId() != null) {
                advertisement.setExecutor(driverService.findById(advertisementDTO.getDriverId()));
            }
            advertisement.setDeliverFrom(advertisementDTO.getDeliverFrom());
            advertisement.setDeliverTo(advertisementDTO.getDeliverTo());
            advertisement.setId(advertisementDTO.getId());
            advertisement.setTitle(advertisementDTO.getTitle());
            Set<Type> set = advertisementDTO.getTypes();
            if (set != null) {
                advertisement.setTypes(new HashSet<>(set));
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
