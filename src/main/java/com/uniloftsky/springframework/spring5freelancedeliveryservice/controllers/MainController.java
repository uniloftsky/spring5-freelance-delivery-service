package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter.AdvertisementPage;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter.AdvertisementSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/public")
@RestController
public class MainController {

    private final AdvertisementService advertisementService;

    public MainController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping("/advertisements")
    public Page<AdvertisementDTO> filterAdvertisements(AdvertisementPage advertisementPage, AdvertisementSearchCriteria advertisementSearchCriteria) {
        return advertisementService.filter(advertisementPage, advertisementSearchCriteria);
    }

}
