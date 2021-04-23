package com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Value("${auth0.management.api}")
    private String managementApi;

    @Value("${auth0.management.token}")
    private String token;

    private final RestTemplate restTemplate;

    public UserRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public User findById(String id) {
        String getUsersUri = managementApi + "users/";
        HttpEntity<User> response = restTemplate.exchange(getUsersUri + id, HttpMethod.GET, getTokenHeader(), User.class);
        return response.getBody();
    }

    @Override
    public Set<User> findAll() {
        String getUsersUri = managementApi + "users";
        HttpEntity<User[]> response = restTemplate.exchange(getUsersUri, HttpMethod.GET, getTokenHeader(), User[].class);
        return new HashSet<>(Arrays.asList(response.getBody()));
    }

    @Override
    public User save(String userId, UserDTO userDTO) throws IllegalAccessException {
        User foundUser = findById(userId);
        UserDTO patchUser = foundUser.clone();
        Field[] userFields = patchUser.getClass().getDeclaredFields();
        for (Field field : userFields) {
            field.setAccessible(true);
            if (field.get(userDTO) != null) {
                field.set(patchUser, field.get(userDTO));
            }
        }
        /*if (userDTO.getEmail() != null) {
            patchUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getFamily_name() != null) {
            patchUser.setFamily_name(userDTO.getFamily_name());
        }
        if (userDTO.getGiven_name() != null) {
            patchUser.setGiven_name(userDTO.getGiven_name());
        }
        if (userDTO.getName() != null) {
            patchUser.setName(userDTO.getName());
        }
        if (userDTO.getPicture() != null) {
            patchUser.setPicture(userDTO.getPicture());
        }
        if (userDTO.getUserMetadata() != null) {
            patchUser.setUserMetadata(userDTO.getUserMetadata());
        }
        if (userDTO.getNickname() != null) {
            patchUser.setNickname(userDTO.getNickname());
        }*/
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("authorization", "Bearer " + token);
        String getUsersUri = managementApi + "users/";
        HttpEntity<UserDTO> userEntity = new HttpEntity<>(patchUser, headers);
        return restTemplate.patchForObject(getUsersUri + userId, userEntity, User.class);
    }

    @Override
    public void delete(String userId) {
        String getUsersUri = managementApi + "users/";
        restTemplate.exchange(getUsersUri + userId, HttpMethod.DELETE, getTokenHeader(), Void.class);
    }

    private HttpEntity<?> getTokenHeader() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("authorization", "Bearer " + token);
        return new HttpEntity<>(headers);
    }
}
