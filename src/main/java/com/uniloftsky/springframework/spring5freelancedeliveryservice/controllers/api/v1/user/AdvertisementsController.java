package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.AdvertisementMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "user's advertisements", description = "Controller what provides end-points to work with user's advertisements. Authentication is required.")
@RestController
@RequestMapping("/api/v1/user/advertisements")
public class AdvertisementsController extends AbstractController {

    private final UserService userService;
    private final AdvertisementService advertisementService;
    private final AdvertisementMapper advertisementMapper;

    public AdvertisementsController(UserService userService, AdvertisementService advertisementService, AdvertisementMapper advertisementMapper) {
        super(userService);
        this.userService = userService;
        this.advertisementService = advertisementService;
        this.advertisementMapper = advertisementMapper;
    }


    @Operation(summary = "Get all advertisements what belongs to a user.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Set<AdvertisementDTO> getUserAdvertisements(Authentication authentication) {
        return advertisementService.findAllByUser(authentication.getName());
    }

    @Operation(summary = "Get user's advertisement by ID.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AdvertisementDTO getUserAdvertisement(@PathVariable("id") Long id, Authentication authentication) {
        return advertisementMapper.advertisementToAdvertisementDTO(advertisementService.findUserAdvertisement(id, authentication.getName()));
    }

    @Operation(summary = "Delete user's advertisement by ID.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserAdvertisement(@PathVariable("id") Long id, Authentication authentication) {
        advertisementService.delete(id, getUser(authentication));
        return new ResponseEntity<>("Advertisement deleted", new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Create user's advertisement.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public AdvertisementDTO createUserAdvertisement(@RequestBody AdvertisementDTO advertisement, Authentication authentication) {
        return advertisementService.save(advertisementMapper.advertisementDTOToAdvertisement(advertisement), getUser(authentication));
    }

    @Operation(summary = "Patch user's advertisement by ID.")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public AdvertisementDTO patchUserAdvertisement(@PathVariable("id") Long id, @RequestBody AdvertisementDTO advertisement, Authentication authentication) {
        return advertisementService.patch(advertisement, getUser(authentication), id);
    }

    @Operation(summary = "Appoint driver to a specified user's advertisement.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/appoint", params = {"advertisement_id", "driver_id"})
    public AdvertisementDTO appointDriverToAdvertisement(@RequestParam("advertisement_id") Long advertisementId, @RequestParam("driver_id") Long driverId, Authentication authentication) {
        return advertisementService.appointDriverToAdvertisement(advertisementId, driverId, authentication.getName());
    }

}
