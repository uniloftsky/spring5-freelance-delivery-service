package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.NotificationDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mappings({
            @Mapping(source = "timeStamp", target = "time_stamp"),
            @Mapping(source = "userId", target = "user_id")
    })
    NotificationDTO notificationToNotificationDTO(Notification notification);

    Notification notificationDTOToNotification(NotificationDTO notificationDTO);

}
