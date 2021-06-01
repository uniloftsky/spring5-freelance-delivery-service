package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.notification;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;

import java.util.Set;

public interface NotificationService {

    Notification findById(Long id);
    Notification findUserNotification(Long id, String userId);


    Set<Notification> findAll();

    Notification save(Notification notification);
    Notification save(Notification notification, User user);

    void delete(Long notificationId);
    void delete(Long notificationId, User user);

}
