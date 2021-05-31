package com.uniloftsky.springframework.spring5freelancedeliveryservice.utils;


import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.NotificationService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.TypeService;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public final class DTOHandler {

    private static final Set<Type> types = new HashSet<>();

    public static <T> void handleFields(T dto, T patchingDTO) {
        Field[] objectFields = dto.getClass().getDeclaredFields();
        for (Field field : objectFields) {
            field.setAccessible(true);
            try {
                if (field.get(dto) != null) {
                    field.set(patchingDTO, field.get(dto));
                }
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void patchAdvertisementTypes(Advertisement advertisement, User user, TypeService typeService) {
        types.clear();
        for (Type type : advertisement.getTypes()) {
            types.add(typeService.findById(type.getId()));
        }
        advertisement.setUserId(user.getUser_id());
        advertisement.getTypes().clear();
        advertisement.getTypes().addAll(types);
    }

    public static void patchAdvertisementTypes(Driver driver, User user, TypeService typeService) {
        types.clear();
        for (Type type : driver.getTypes()) {
            types.add(typeService.findById(type.getId()));
        }
        driver.setUserId(user.getUser_id());
        driver.getTypes().clear();
        driver.getTypes().addAll(types);
    }

    private static Notification createNotification(String message, String title, String userId) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTitle(title);
        notification.setTimeStamp(LocalDate.now());
        notification.setUserId(userId);
        return notification;
    }

    public static void createNotificationOnEvent(User client, String notificationTitle, String notificationMessage, NotificationService notificationService) {
        Notification notification = DTOHandler.createNotification(notificationMessage, notificationTitle, client.getUser_id());
        notificationService.save(notification, client);
    }

}
