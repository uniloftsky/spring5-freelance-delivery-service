package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.AdvertisementMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user/advertisements")
public class AdvertisementsController extends AbstractController {

    private final UserService userService;
    private final AdvertisementService advertisementService;
    private final AdvertisementMapper advertisementMapper;
    private final DriverService driverService;
    private final DriverMapper driverMapper;

    public AdvertisementsController(UserService userService, AdvertisementService advertisementService, AdvertisementMapper advertisementMapper, DriverService driverService, DriverMapper driverMapper) {
        super(userService);
        this.userService = userService;
        this.advertisementService = advertisementService;
        this.advertisementMapper = advertisementMapper;
        this.driverService = driverService;
        this.driverMapper = driverMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Set<AdvertisementDTO> getUserAdvertisements(Authentication authentication) {
        return advertisementService.findAllByUser(authentication.getName());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AdvertisementDTO getUserAdvertisement(@PathVariable("id") Long id, Authentication authentication) {
        return advertisementMapper.advertisementToAdvertisementDTO(advertisementService.findUserAdvertisement(id, authentication.getName()));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserAdvertisement(@PathVariable("id") Long id, Authentication authentication) {
        advertisementService.delete(id, getUser(authentication));
        return new ResponseEntity<>("Advertisement deleted", new HttpHeaders(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public AdvertisementDTO createUserAdvertisement(@RequestBody AdvertisementDTO advertisement, Authentication authentication) {
        return advertisementService.save(advertisementMapper.advertisementDTOToAdvertisement(advertisement), getUser(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public AdvertisementDTO patchUserAdvertisement(@PathVariable("id") Long id, @RequestBody AdvertisementDTO advertisement, Authentication authentication) {
        return advertisementService.patch(advertisement, getUser(authentication), id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/appoint", params = {"advertisement_id", "driver_id"})
    public AdvertisementDTO appointDriverToAdvertisement(@RequestParam("advertisement_id") Long advertisementId, @RequestParam("driver_id") Long driverId, Authentication authentication) {
        return advertisementService.appointDriverToAdvertisement(advertisementId, driverId, authentication.getName());
    }

}
