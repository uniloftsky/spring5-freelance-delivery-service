package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;

import java.util.Set;

public interface NotificationService {

    Notification findById(Long id);

    Set<Notification> findAll();

    Notification save(Notification notification);
    Notification save(Notification notification, User user) throws IllegalAccessException;

    void delete(Notification notification);
    void delete(Notification notification, User user) throws IllegalAccessException;

}
