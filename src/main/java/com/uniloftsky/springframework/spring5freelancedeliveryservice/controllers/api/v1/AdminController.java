package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Notification;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin controller", description = "Controller what provides end-points for blocking advertisements.")
@RequestMapping("/api/v1/admin")
@RestController
public class AdminController {

    private final AdvertisementService advertisementService;

    public AdminController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @Operation(summary = "Block user's advertisement.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "403", description = "Forbidden. User hasn't permission to perform block operation.")
    })
    @PostMapping("/blockAdvertisement/{id}")
    @PreAuthorize("hasAuthority('block:advertisements')")
    public AdvertisementDTO blockAdvertisement(@Parameter(description = "ID of advertisement what will be blocked") @PathVariable("id") Long id, @Parameter(description = "Notification about blocking what will be sent to advertisement owner") @RequestBody Notification notification) {
        return advertisementService.blockAdvertisement(id, notification);
    }

}
