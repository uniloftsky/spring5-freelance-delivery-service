package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.TypeDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping(path = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<TypeDTO> getTypes() {
        return typeService.findAll();
    }

    @GetMapping("/private/types")
    public String getMessage(Authentication authentication) {
        return "All good. You can see this because you are Authenticated.";
    }
}
