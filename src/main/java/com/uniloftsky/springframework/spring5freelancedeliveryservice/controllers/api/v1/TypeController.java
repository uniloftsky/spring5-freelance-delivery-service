package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.TypeMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.TypeDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.type.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class TypeController {

    private final TypeService typeService;
    private final TypeMapper typeMapper;

    public TypeController(TypeService typeService, TypeMapper typeMapper) {
        this.typeService = typeService;
        this.typeMapper = typeMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/types")
    public Set<TypeDTO> getTypes() {
        return typeService.findAll().stream().map(typeMapper::typeToTypeDTO).collect(Collectors.toSet());
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
