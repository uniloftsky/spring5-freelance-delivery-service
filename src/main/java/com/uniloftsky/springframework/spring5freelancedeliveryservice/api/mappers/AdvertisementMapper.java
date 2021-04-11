package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.TypeDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdvertisementMapper {

    AdvertisementMapper INSTANCE = Mappers.getMapper(AdvertisementMapper.class);

    AdvertisementDTO advertisementToAdvertisementDTO(Advertisement advertisement);
    Advertisement advertisementDTOToAdvertisement(AdvertisementDTO advertisementDTO);
    @Mapping(target = "id", ignore = true)
   TypeDTO typeToTypeDTO(Type type);
    @Mapping(target = "id", ignore = true)
    Type typeDTOToType(TypeDTO typeDTO);

}
