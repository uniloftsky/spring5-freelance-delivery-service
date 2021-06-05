package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.AdvertisementMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.auth0.User;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter.AdvertisementPage;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter.AdvertisementSearchCriteria;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.filter.DriverPage;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.filter.DriverSearchCriteria;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "Main controller", description = "Controller what provides generic, public end-points.")
@CrossOrigin("*")
@RestController
public class MainController {

    private final AdvertisementService advertisementService;
    private final DriverService driverService;
    private final UserService userService;

    private final AdvertisementMapper advertisementMapper;
    private final DriverMapper driverMapper;

    public MainController(AdvertisementService advertisementService, DriverService driverService, UserService userService, AdvertisementMapper advertisementMapper, DriverMapper driverMapper) {
        this.advertisementService = advertisementService;
        this.driverService = driverService;
        this.userService = userService;
        this.advertisementMapper = advertisementMapper;
        this.driverMapper = driverMapper;
    }

    @Operation(hidden = true)
    @GetMapping({"/", "*", "/*", "*/"})
    public String redirectToApiPage() {
        return "redirect:/api";
    }

    @Operation(summary = "Get paged list of advertisements with specified criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/public/advertisements")
    public Page<AdvertisementDTO> filterAdvertisements(@Parameter(description = "Page setting") AdvertisementPage advertisementPage, @Parameter(description = "Advertisement filtering criteria") AdvertisementSearchCriteria advertisementSearchCriteria) {
        return advertisementService.filter(advertisementPage, advertisementSearchCriteria);
    }

    @Operation(summary = "Get paged list of drivers with specified criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/public/drivers")
    public Page<DriverDTO> filterDrivers(@Parameter(description = "Page setting") DriverPage driverPage, @Parameter(description = "Driver filtering criteria") DriverSearchCriteria driverSearchCriteria) {
        return driverService.filterDrivers(driverPage, driverSearchCriteria);
    }

    @Operation(summary = "Get list of recommended drivers for an advertisement.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/api/v1/public/advertisements/recommended", params = "advertisement_id")
    public Set<DriverDTO> getRecommendedDriversForAdvertisement(@Parameter(description = "ID of advertisement to find recommended drivers") @RequestParam("advertisement_id") Long advertisementId) {
        return advertisementService.findAllRecommendedDriversForAdvertisement(advertisementId);
    }

    @Operation(summary = "Get advertisement by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "Cannot find an advertisement with specified ID.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/public/advertisements/{id}")
    public AdvertisementDTO getAdvertisement(@Parameter(description = "ID of advertisement what will be found.") @PathVariable("id") Long id) {
        return advertisementMapper.advertisementToAdvertisementDTO(advertisementService.findById(id));
    }

    @Operation(summary = "Get driver by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "Cannot find an driver with specified ID.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/public/drivers/{id}")
    public DriverDTO getDriver(@Parameter(description = "ID of driver what will be found.") @PathVariable("id") Long id) {
        return driverMapper.driverToDriverDTO(driverService.findById(id));
    }

    @Operation(summary = "Get user by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "Cannot find an user with specified ID.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/public/users/{id}")
    public User getUser(@Parameter(description = "ID of user will be found.") @PathVariable("id") String id) {
        return userService.findById(id);
    }

}
