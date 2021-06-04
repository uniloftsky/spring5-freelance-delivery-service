package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "user's driver", description = "Controller what provides end-points to work with user's driver. Authentication is required.")
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

    @Operation(summary = "Get user's driver entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "User isn't a driver")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public DriverDTO getUserDriver(Authentication authentication) {
        return driverMapper.driverToDriverDTO(driverService.getUserDriver(getUser(authentication)));
    }

    @Operation(summary = "Create driver entity for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "User is already a driver.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public DriverDTO createUserDriver(@Parameter(description = "Driver what will be created") @RequestBody DriverDTO driverDTO, Authentication authentication) {
        return driverMapper.driverToDriverDTO(driverService.save(driverMapper.driverDTOToDriver(driverDTO), getUser(authentication)));
    }

    @Operation(summary = "Patch user's driver entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "User isn't a driver.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public DriverDTO patchUserDriver(@Parameter(description = "Fields of driver to patch") @RequestBody DriverDTO driverDTO, Authentication authentication) {
        return driverService.patch(driverDTO, getUser(authentication));
    }

    @Operation(summary = "Respond by driver on advertisement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "User isn't a driver.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/respond", params = "advertisement_id")
    public AdvertisementDTO respondOnAdvertisement(@Parameter(description = "ID of advertisement on which driver will respond") @RequestParam("advertisement_id") Long id, Authentication authentication) {
        return driverService.respondOnAdvertisement(id, getUser(authentication));
    }

    @Operation(summary = "Get user's driver entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "User isn't a driver."),
            @ApiResponse(responseCode = "400", description = "User cannot execute advertisement what not belongs to him.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/executing", params = "advertisement_id")
    public AdvertisementDTO executingAdvertisement(@Parameter(description = "ID of advertisement which status will be changed to IN_PROCESS") @RequestParam("advertisement_id") Long id, Authentication authentication) {
        return driverService.executingAdvertisement(id, getUser(authentication));
    }

    @Operation(summary = "Get user's driver entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "User isn't a driver"),
            @ApiResponse(responseCode = "400", description = "User cannot execute advertisement what not belongs to him.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/done", params = "advertisement_id")
    public AdvertisementDTO finishAdvertisement(@Parameter(description = "ID of advertisement which status will be changed to READY") @RequestParam("advertisement_id") Long id, Authentication authentication) {
        return driverService.finishAdvertisement(id, getUser(authentication));
    }

    @Operation(summary = "Get recommended advertisements for a driver.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Auth needed. Provide authentication header with Bearer token."),
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "User isn't a driver")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/recommended")
    public Set<AdvertisementDTO> getRecommendedAdvertisements(Authentication authentication) {
        return driverService.findRecommendedAdvertisements(getUser(authentication));
    }

}
