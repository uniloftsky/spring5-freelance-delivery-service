package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.user;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.AdvertisementService;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user/advertisements")
public class AdvertisementsController {

    private final UserService userService;
    private final AdvertisementService advertisementService;

    public AdvertisementsController(UserService userService, AdvertisementService advertisementService) {
        this.userService = userService;
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public Set<Advertisement> getUserAdvertisements(Authentication authentication) {
        return userService.findById(authentication.getName()).getUser_metadata().getAdvertisements();
    }

    @GetMapping("/{id}")
    public Advertisement getUserAdvertisement(@PathVariable("id") Long id, Authentication authentication) {
        return advertisementService.findUserAdvertisement(id, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserAdvertisement(@PathVariable("id") Long id, Authentication authentication) {
        advertisementService.delete(id, userService.findById(authentication.getName()));
        return new ResponseEntity<>("Advertisement deleted", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public Advertisement createUserAdvertisement(@RequestBody Advertisement advertisement, Authentication authentication) {
        return advertisementService.save(advertisement, userService.findById(authentication.getName()));
    }

    @PatchMapping("/{id}")
    public Advertisement patchUserAdvertisement(@PathVariable("id") Long id, @RequestBody AdvertisementDTO advertisement, Authentication authentication) {
        return advertisementService.patch(advertisement, userService.findById(authentication.getName()), id);
    }

}
