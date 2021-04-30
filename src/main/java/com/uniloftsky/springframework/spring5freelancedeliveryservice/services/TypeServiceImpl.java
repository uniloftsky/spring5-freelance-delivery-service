package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.TypeMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.TypeDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeRepository typeRepository, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    @Override
    public Set<TypeDTO> findAll() {
        return typeRepository.findAll().stream().map(typeMapper::typeToTypeDTO).collect(Collectors.toSet());
    }

    @Override
    public TypeDTO findById(Long id) {
        return typeRepository.findById(id).map(typeMapper::typeToTypeDTO).orElseThrow(RuntimeException::new);
    }

    @Override
    public TypeDTO save(TypeDTO typeDTO) {
        return null;
    }

    @Override
    public TypeDTO create(TypeDTO typeDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
