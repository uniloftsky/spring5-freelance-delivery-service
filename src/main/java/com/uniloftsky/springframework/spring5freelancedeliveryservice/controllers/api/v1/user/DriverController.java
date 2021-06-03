package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api/v1/user/driver")
@RestController
public class DriverController extends AbstractController {

    private final DriverService driverService;
    private final DriverMapper driverMapper;

    public DriverController(DriverService driverService, DriverMapper driverMapper, UserService userService) {
        super(userService);
        this.driverService = driverService;
        this.driverMapper = driverMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public DriverDTO getUserDriver(Authentication authentication) {
        return driverMapper.driverToDriverDTO(driverService.getUserDriver(getUser(authentication)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public DriverDTO createUserDriver(@RequestBody DriverDTO driverDTO, Authentication authentication) {
        return driverMapper.driverToDriverDTO(driverService.save(driverMapper.driverDTOToDriver(driverDTO), getUser(authentication)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public DriverDTO patchUserDriver(@RequestBody DriverDTO driverDTO, Authentication authentication) {
        return driverService.patch(driverDTO, getUser(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/respond", params = "advertisement_id")
    public AdvertisementDTO respondOnAdvertisement(@RequestParam("advertisement_id") Long id, Authentication authentication) {
        return driverService.respondOnAdvertisement(id, getUser(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/executing", params = "advertisement_id")
    public AdvertisementDTO executingAdvertisement(@RequestParam("advertisement_id") Long id, Authentication authentication) {
        return driverService.executingAdvertisement(id, getUser(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/done", params = "advertisement_id")
    public AdvertisementDTO finishAdvertisement(@RequestParam("advertisement_id") Long id, Authentication authentication) {
        return driverService.finishAdvertisement(id, getUser(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/recommended")
    public Set<AdvertisementDTO> getRecommendedAdvertisements(Authentication authentication) {
        return driverService.findRecommendedAdvertisements(getUser(authentication));
    }

}