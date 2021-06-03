package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.type;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.TypeDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;

import java.util.Set;

public interface TypeService {

    Set<Type> findAll();

    Type findById(Long id);

    Type findByName(String name);

    Type save(Type type);

    TypeDTO save(TypeDTO typeDTO);

    TypeDTO patch(TypeDTO typeDTO, Long id);

    void delete(Long id);

}
