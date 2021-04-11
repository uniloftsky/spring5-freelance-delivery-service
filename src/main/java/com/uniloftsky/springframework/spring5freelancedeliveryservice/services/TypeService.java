package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.TypeDTO;

import java.util.Set;

public interface TypeService {

    Set<TypeDTO> getAll();
    TypeDTO getById(Long id);
    TypeDTO save(TypeDTO typeDTO);
    TypeDTO create(TypeDTO typeDTO);
    void delete(Long id);

}
