package com.shan.demo.configserver.service;


import com.shan.demo.configserver.repository.ProfileRepository;
import com.shan.demo.configserver.util.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    public final static String id = "id";

    public Map<String, Object> getProfileDetails(String profile) throws ExecutionException, InterruptedException {

        return profileRepository.getProfileDetails(profile);
    }

    public Map<String, Object> createProfile(String profile) throws ExecutionException, InterruptedException {
        Map<String, Object> documentProfile = JsonHelper.createJsonObject(profile).toMap();
        return  profileRepository.upsertProfile(documentProfile, id);
    }

    public Map<String, Object> updateProfile(String profileId, String profile) throws ExecutionException, InterruptedException {
        Map<String, Object> documentProfile = JsonHelper.createJsonObject(profile).toMap();
        documentProfile.put(id, profileId);
        return  profileRepository.upsertProfile(documentProfile, id);
    }
}
