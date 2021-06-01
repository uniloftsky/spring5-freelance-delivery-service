package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.notification;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.NotificationRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Notification findUserNotification(Long id, String userId) {
        Optional<Notification> notificationOptional = userService.findById(userId).getUser_metadata().getNotifications()
                .stream().filter(e -> e.getId().equals(id)).findFirst();
        if (notificationOptional.isEmpty()) {
            throw new RuntimeException("User has not expected notification!");
        } else {
            return notificationOptional.get();
        }
    }

    @Override
    public Set<Notification> findAll() {
        Set<Notification> notifications = new HashSet<>();
        notificationRepository.findAll().iterator().forEachRemaining(notifications::add);
        return notifications;
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification save(Notification notification, User user) {
        notificationRepository.save(notification);
        UserDTO userDTO = user.clone();
        userDTO.getUserMetadata().getNotifications().removeIf(e -> e.getId().equals(notification.getId()));
        userDTO.getUserMetadata().getNotifications().add(notification);
        userService.save(user, userDTO);
        return notification;
    }

    @Override
    public void delete(Long notificationId) {
        notificationRepository.delete(findById(notificationId));
    }

    @Override
    public void delete(Long notificationId, User user)  {
        UserDTO userDTO = user.clone();
        userDTO.getUserMetadata().getNotifications().removeIf(e -> e.getId().equals(notificationId));
        userService.save(user, userDTO);
        notificationRepository.delete(findById(notificationId));
    }
}
