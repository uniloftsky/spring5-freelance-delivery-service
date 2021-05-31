package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.AdvertisementMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping
    public Set<AdvertisementDTO> getUserAdvertisements() {
        return advertisementService.findAll().stream().map(advertisementMapper::advertisementToAdvertisementDTO).collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public AdvertisementDTO getUserAdvertisement(@PathVariable("id") Long id, Authentication authentication) {
        return advertisementMapper.advertisementToAdvertisementDTO(advertisementService.findUserAdvertisement(id, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserAdvertisement(@PathVariable("id") Long id, Authentication authentication) {
        advertisementService.delete(id, getUser(authentication));
        return new ResponseEntity<>("Advertisement deleted", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public AdvertisementDTO createUserAdvertisement(@RequestBody Advertisement advertisement, Authentication authentication) {
        return advertisementService.save(advertisement, getUser(authentication));
    }

    @PatchMapping("/{id}")
    public AdvertisementDTO patchUserAdvertisement(@PathVariable("id") Long id, @RequestBody AdvertisementDTO advertisement, Authentication authentication) {
        return advertisementService.patch(advertisement, getUser(authentication), id);
    }

    @GetMapping(value = "/appoint", params = {"advertisement_id", "driver_id"})
    public AdvertisementDTO appointDriverToAdvertisement(@RequestParam("advertisement_id") Long advertisementId, @RequestParam("driver_id") Long driverId, Authentication authentication) {
        return advertisementService.appointDriverToAdvertisement(advertisementId, driverId, authentication.getName());
    }

}
