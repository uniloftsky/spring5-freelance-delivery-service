package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;

import java.util.Set;

public interface AdvertisementService {

    Advertisement findById(Long id);

    Set<Advertisement> findAll();

    Advertisement save(Advertisement advertisement);

    Advertisement save(Advertisement advertisement, User user) throws IllegalAccessException;

    void delete(Advertisement advertisement);

    void delete(Advertisement advertisement, User user) throws IllegalAccessException;

}
