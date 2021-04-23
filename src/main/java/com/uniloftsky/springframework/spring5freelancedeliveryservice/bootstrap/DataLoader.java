package com.uniloftsky.springframework.spring5freelancedeliveryservice.bootstrap;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.NotificationMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.UserMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Type;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.Item;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.ItemRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.NotificationRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.TypeRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.UserRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.NotificationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final TypeRepository typeRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final UserMapper userMapper;

    public DataLoader(TypeRepository typeRepository, UserRepository userRepository, ItemRepository itemRepository, NotificationMapper notificationMapper, NotificationRepository notificationRepository, NotificationService notificationService, UserMapper userMapper) {
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
        this.userMapper = userMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        Set<Type> types = new HashSet<>();
        types.add(new Type("Таксі"));
        types.add(new Type("Короткі відстані"));
        types.add(new Type("Довгі відстані"));
        types.add(new Type("Великі вантажі"));

        typeRepository.saveAll(types);

        User user1 = userRepository.findById("auth0|607d94db1c9629006daa7adf");
        UserDTO userDTO = user1.clone();

        Notification notification = new Notification(LocalDate.now(), "Test notification", "Test notification message", user1.getUser_id());
        Notification notification1 = new Notification(LocalDate.now(), "Test notification1", "Test notification message1", user1.getUser_id());
        notificationRepository.save(notification);
        notificationRepository.save(notification1);
        userDTO.getUserMetadata().getNotifications().add(notification);
        userDTO.getUserMetadata().getNotifications().add(notification1);
        userRepository.save(user1.getUser_id(), userDTO);

        System.out.println(userRepository.findById("auth0|607d94db1c9629006daa7adf").getUser_metadata().getNotifications());

        Notification notification2 = notificationService.findById(1L);
        notification2.setMessage("Changed message");
        notificationService.save(notification2, user1.getUser_id());

        System.out.println(userRepository.findById("auth0|607d94db1c9629006daa7adf").getUser_metadata().getNotifications());


        itemRepository.saveAll(loadItems());
    }

    public List<Item> loadItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Salad", 499, "Fresh", "https://cdn.auth0.com/blog/whatabyte/salad-sm.png"));
        items.add(new Item("Salad1", 499, "Fresh", "https://cdn.auth0.com/blog/whatabyte/salad-sm.png"));
        return items;
    }
}
