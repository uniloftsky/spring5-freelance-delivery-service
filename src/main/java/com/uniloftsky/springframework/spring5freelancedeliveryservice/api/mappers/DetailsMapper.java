package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DetailsDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Details;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DetailsMapper {

    DetailsMapper INSTANCE = Mappers.getMapper(DetailsMapper.class);

    DetailsDTO detailsToDetailsDTO(Details details);
    Details detailsDTOToDetails(DetailsDTO detailsDTO);

}
