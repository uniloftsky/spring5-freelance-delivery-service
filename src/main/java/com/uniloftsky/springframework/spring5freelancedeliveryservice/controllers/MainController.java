package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter.AdvertisementPage;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter.AdvertisementSearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final AdvertisementService advertisementService;

    public MainController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
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

}
