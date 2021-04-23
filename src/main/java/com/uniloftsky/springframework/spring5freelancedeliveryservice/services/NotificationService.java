package com.uniloftsky.springframework.spring5freelancedeliveryservice.services;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;

import java.util.Set;

public interface NotificationService {

    Notification findById(Long id);

    Set<Notification> findAll();

    Notification save(Notification notification);
    Notification save(Notification notification, String userId) throws IllegalAccessException;

    void delete(Notification notification);

}
