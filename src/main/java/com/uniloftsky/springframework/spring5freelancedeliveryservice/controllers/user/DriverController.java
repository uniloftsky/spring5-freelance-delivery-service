package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user/driver")
@RestController
public class DriverController extends AbstractController {

    private final DriverService driverService;
    private final DriverMapper driverMapper;
    private final UserService userService;

    public DriverController(DriverService driverService, DriverMapper driverMapper, UserService userService) {
        super(userService);
        this.driverService = driverService;
        this.driverMapper = driverMapper;
        this.userService = userService;
    }

    @GetMapping
    public DriverDTO getUserDriver(Authentication authentication) {
        return driverMapper.driverToDriverDTO(driverService.getUserDriver(getUser(authentication)));
    }

    @PostMapping
    public DriverDTO createUserDriver(@RequestBody DriverDTO driverDTO, Authentication authentication) {
        return driverMapper.driverToDriverDTO(driverService.save(driverMapper.driverDTOToDriver(driverDTO), getUser(authentication)));
    }

    @PatchMapping
    public DriverDTO patchUserDriver(@RequestBody DriverDTO driverDTO, Authentication authentication) {
        return driverService.patch(driverDTO, getUser(authentication));
    }

    @GetMapping(value = "/executing", params = {"advertisement_id"})
    public AdvertisementDTO executeAdvertisement(@RequestParam("advertisement_id") Long id, Authentication authentication) {
        return driverService.executingAdvertisement(id, getUser(authentication));
    }

}
