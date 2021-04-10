package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.NotificationDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationDTO notificationToNotificationDTO(Notification notification);
    Notification notificationDTOToNotification(NotificationDTO notificationDTO);

}
