package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.TypeDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.Item;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.repositories.ItemRepository;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping(path = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class TypeController {

    private final TypeService typeService;
    private final ItemRepository itemRepository;

    public TypeController(TypeService typeService, ItemRepository itemRepository) {
        this.typeService = typeService;
        this.itemRepository = itemRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<TypeDTO> getTypes() {
        return typeService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/menu/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Item> getMenuItems() {
        return itemRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/menu/items")
    public Item postItem(@RequestBody Item item, Authentication authentication) {
        System.out.println(authentication.getName());
        return itemRepository.save(item);
    }

    @GetMapping("/private/types")
    public String getMessage(Authentication authentication) {
        return "All good. You can see this because you are Authenticated.";
    }
}
