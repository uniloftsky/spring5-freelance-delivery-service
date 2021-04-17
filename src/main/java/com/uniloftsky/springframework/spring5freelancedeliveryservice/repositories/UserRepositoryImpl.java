package com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private String domain;
    private final String token;

    private final String getUserUri = "https://" + domain + "/api/v2/users/";

    private final RestTemplate restTemplate;

    public UserRepositoryImpl(@Value("${auth0.domain}") String domain, @Value("${auth0.management.token}") String token, RestTemplate restTemplate) {
        this.domain = domain;
        this.token = token;
        this.restTemplate = restTemplate;
    }

    @Override
    public User findById(String id) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(getUserUri + id);
        System.out.println(uriBuilder.toUriString());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("authorization", token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<User> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, User.class);
        return response.getBody();
    }
}
