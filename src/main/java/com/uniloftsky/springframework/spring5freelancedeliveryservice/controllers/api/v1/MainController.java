package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter.AdvertisementPage;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter.AdvertisementSearchCriteria;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.DriverService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.filter.DriverPage;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.filter.DriverSearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin("*")
@RestController
public class MainController {

    private final AdvertisementService advertisementService;
    private final DriverService driverService;
    private final DriverMapper driverMapper;

    public MainController(AdvertisementService advertisementService, DriverService driverService, DriverMapper driverMapper) {
        this.advertisementService = advertisementService;
        this.driverService = driverService;
        this.driverMapper = driverMapper;
    }

    @Operation(hidden = true)
    @GetMapping({"/", "*", "/*", "*/"})
    public String redirectToApiPage() {
        return "redirect:/api";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/public/advertisements")
    public Page<AdvertisementDTO> filterAdvertisements(AdvertisementPage advertisementPage, AdvertisementSearchCriteria advertisementSearchCriteria) {
        return advertisementService.filter(advertisementPage, advertisementSearchCriteria);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/public/drivers")
    public Page<DriverDTO> filterDrivers(DriverPage driverPage, DriverSearchCriteria driverSearchCriteria) {
        return driverService.filterDrivers(driverPage, driverSearchCriteria);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/api/v1/public/advertisements/recommended", params = "advertisement_id")
    public Set<DriverDTO> getRecommendedDriversForAdvertisement(@RequestParam("advertisement_id") Long advertisementId) {
        return advertisementService.findAllRecommendedDriversForAdvertisement(advertisementId);
    }

}
