package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DetailsDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Details;

public interface DetailsMapper {

    DetailsDTO detailsToDetailsDTO(Details details);
    Details detailsDTOToDetails(DetailsDTO detailsDTO);

}
