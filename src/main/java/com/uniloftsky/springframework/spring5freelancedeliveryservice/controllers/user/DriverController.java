package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user/driver")
@RestController
public class DriverController {

    private final DriverService driverService;
    private final DriverMapper driverMapper;
    private final UserService userService;

    public DriverController(DriverService driverService, DriverMapper driverMapper, UserService userService) {
        this.driverService = driverService;
        this.driverMapper = driverMapper;
        this.userService = userService;
    }

    @GetMapping
    public DriverDTO getUserDriver(Authentication authentication) {
        return driverMapper.driverToDriverDTO(driverService.getUserDriver(userService.findById(authentication.getName())));
    }

    @PostMapping
    public DriverDTO createUserDriver(@RequestBody DriverDTO driverDTO, Authentication authentication) {
        return driverService.save(driverMapper.driverDTOToDriver(driverDTO), userService.findById(authentication.getName()));
    }

    @PatchMapping
    public DriverDTO patchUserDriver(@RequestBody DriverDTO driverDTO, Authentication authentication) {
        return driverService.patch(driverDTO, userService.findById(authentication.getName()));
    }

}
