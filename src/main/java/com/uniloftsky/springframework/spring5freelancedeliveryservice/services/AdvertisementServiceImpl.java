package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.AdvertisementMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.TypeMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.AdvertisementRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.utils.FieldsHandler;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;
    private final TypeService typeService;
    private final TypeMapper typeMapper;
    private final UserService userService;

    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, AdvertisementMapper advertisementMapper, TypeService typeService, TypeMapper typeMapper, UserService userService) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementMapper = advertisementMapper;
        this.typeService = typeService;
        this.typeMapper = typeMapper;
        this.userService = userService;
    }

    @Override
    public Advertisement findById(Long id) {
        return advertisementRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Advertisement findUserAdvertisement(Long id, String userId) {
        Optional<Advertisement> advertisementOptional = userService.findById(userId).getUser_metadata().getAdvertisements()
                .stream().filter(e -> e.getId().equals((id))).findFirst();
        if (advertisementOptional.isEmpty()) {
            throw new RuntimeException("User has not expected advertisement!");
        } else {
            return advertisementOptional.get();
        }
    }

    @Override
    public Set<Advertisement> findAll() {
        Set<Advertisement> advertisements = new HashSet<>();
        advertisementRepository.findAll().iterator().forEachRemaining(advertisements::add);
        return advertisements;
    }

    @Override
    public Advertisement save(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    @Override
    public Advertisement save(Advertisement advertisement, User user) {
        Advertisement savedAdvertisement = handleAdvertisement(advertisement, user);
        UserDTO userDTO = user.clone();
        userDTO.getUserMetadata().getAdvertisements().removeIf(e -> e.getId().equals(advertisement.getId()));
        userDTO.getUserMetadata().getAdvertisements().add(savedAdvertisement);
        userService.save(user, userDTO);
        return savedAdvertisement;
    }

    @Override
    public Advertisement patch(AdvertisementDTO advertisementDTO, User user, Long id) {
        Advertisement patchedAdvertisement = findById(id);
        AdvertisementDTO patchedAdvertisementDTO = advertisementMapper.advertisementToAdvertisementDTO(patchedAdvertisement);
        FieldsHandler.handleFields(advertisementDTO, patchedAdvertisementDTO);
        patchedAdvertisement = advertisementMapper.advertisementDTOToAdvertisement(patchedAdvertisementDTO);
        save(patchedAdvertisement, user);
        return patchedAdvertisement;
    }

    @Override
    public void delete(Long advertisementId) {
        advertisementRepository.delete(findById(advertisementId));
    }

    @Override
    public void delete(Long advertisementId, User user) {
        UserDTO userDTO = user.clone();
        userDTO.getUserMetadata().getAdvertisements().removeIf(e -> e.getId().equals(advertisementId));
        userService.save(user, userDTO);
        advertisementRepository.delete(findById(advertisementId));
    }

    @Override
    public void refreshAdvertisementsType(Type type) {
        Set<Advertisement> advertisements = findAll().stream().filter(ad -> {
            long count = ad.getTypes().stream().filter(t -> t.getId().equals(type.getId())).count();
            return count > 0;
        }).collect(Collectors.toSet());
        for (Advertisement advertisement : advertisements) {
            save(advertisement, userService.findById(advertisement.getUserId()));
        }
    }

    private Advertisement handleAdvertisement(Advertisement advertisement, User user) {
        Set<Type> types = new HashSet<>();
        for (Type type : advertisement.getTypes()) {
            types.add(typeMapper.typeDTOToType(typeService.findById(type.getId())));
        }
        advertisement.setUserId(user.getUser_id());
        advertisement.getTypes().clear();
        advertisement.getTypes().addAll(types);
        return advertisementRepository.save(advertisement);
    }

}
