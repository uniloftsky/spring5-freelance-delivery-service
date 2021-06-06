package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.TypeMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.TypeDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.type.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Tag(name = "Advertisement types", description = "Controller what provides end-points to work with advertisement types.")
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class TypeController {

    private final TypeService typeService;
    private final TypeMapper typeMapper;

    public TypeController(TypeService typeService, TypeMapper typeMapper) {
        this.typeService = typeService;
        this.typeMapper = typeMapper;
    }

    @Operation(summary = "Get list of types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/public/types")
    public Set<TypeDTO> getTypes() {
        return typeService.findAll().stream().map(typeMapper::typeToTypeDTO).collect(Collectors.toSet());
    }

    @Operation(summary = "Create a new type. Admin role is required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('create:types')")
    @PostMapping("/admin/types")
    public TypeDTO createType(@Parameter(description = "Type what will be created") @RequestBody TypeDTO typeDTO) {
        return typeService.save(typeDTO);
    }

    @Operation(summary = "Patch a specified type. Admin role is required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "Cannot find type with specified ID")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('patch:types')")
    @PatchMapping("/admin/types/{id}")
    public TypeDTO patchType(@Parameter(description = "Fields of type to patch")@RequestBody TypeDTO typeDTO, @Parameter(description = "ID of type what will be patched") @PathVariable("id") Long id) {
        return typeService.patch(typeDTO, id);
    }
}
