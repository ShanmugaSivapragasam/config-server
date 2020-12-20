package com.shan.demo.configserver.controller;


import com.shan.demo.configserver.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class ProfileController {

    @Autowired
    ProfileService profileService;


    @GetMapping("/profiles/{profileId}")
    public ResponseEntity<Map<String, Object>> getProfileDetails(@PathVariable String profileId) throws ExecutionException, InterruptedException {

        Map<String, Object> response = profileService.getProfileDetails(profileId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/profiles")
    public  ResponseEntity<Map<String, Object>> createProflie(@RequestBody String profile) throws ExecutionException, InterruptedException {

        Map<String, Object> newProfile = profileService.createProfile(profile);
        return  new ResponseEntity<>(newProfile, HttpStatus.CREATED);
    }


    @PutMapping("/profiles/{profileId}")
    public ResponseEntity<Map<String, Object>> updateProfile(@PathVariable String profileId, @RequestBody String profile ) throws ExecutionException, InterruptedException {

        Map<String, Object> response = profileService.updateProfile(profileId, profile);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }
}
