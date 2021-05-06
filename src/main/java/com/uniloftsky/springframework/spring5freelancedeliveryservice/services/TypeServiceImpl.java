package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.TypeMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.TypeDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.TypeRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.utils.FieldsHandler;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final AdvertisementService advertisementService;
    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeRepository typeRepository, @Lazy AdvertisementService advertisementService, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.advertisementService = advertisementService;
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
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public TypeDTO save(TypeDTO typeDTO) {
        Type savedType = typeMapper.typeDTOToType(typeDTO);
        return typeMapper.typeToTypeDTO(typeRepository.save(savedType));
    }

    @Override
    public TypeDTO patch(TypeDTO typeDTO, Long id) {
        Type patchedType = typeMapper.typeDTOToType(findById(id));
        TypeDTO patchedTypeDTO = typeMapper.typeToTypeDTO(patchedType);
        FieldsHandler.handleFields(typeDTO, patchedTypeDTO);
        patchedType = typeMapper.typeDTOToType(patchedTypeDTO);
        save(patchedType);
        advertisementService.refreshAdvertisementsType(patchedType);
        return patchedTypeDTO;
    }

    @Override
    public void delete(Long id) {
        typeRepository.delete(typeMapper.typeDTOToType(findById(id)));
    }
}
