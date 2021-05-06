package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.TypeDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    /*@ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<TypeDTO> getTypes() {
        return typeService.findAll();
    }

    @GetMapping("/private/types")
    public String getMessage(Authentication authentication) {
        return "All good. You can see this because you are Authenticated.";
    }*/

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/types")
    public Set<TypeDTO> getTypes() {
        return typeService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/admin/types")
    public TypeDTO createType(@RequestBody TypeDTO typeDTO) {
        return typeService.save(typeDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/admin/types/{id}")
    public TypeDTO patchType(@RequestBody TypeDTO typeDTO, @PathVariable("id") Long id) {
        return typeService.patch(typeDTO, id);
    }
}
