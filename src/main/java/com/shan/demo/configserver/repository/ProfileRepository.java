package com.shan.demo.configserver.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class ProfileRepository {

    @Autowired
    private Firestore firestore;

    private String profileColleciton = "profiles";



    public Map<String, Object> getProfileDetails(String profile) throws ExecutionException, InterruptedException {
        log.info("getProfileDetails invoked for  " + profile);
        DocumentReference query =
                firestore.collection(profileColleciton).document(profile);
        DocumentSnapshot documentSnapshot = query.get().get();
        if(documentSnapshot.getData() == null || documentSnapshot.getData().isEmpty()){
            Map<String, Object> result = new HashMap<>();
            result.put("response", HttpStatus.NOT_FOUND.getReasonPhrase());
            result.put("currentTime", Timestamp.now().toSqlTimestamp());
            return result;

        }
        return documentSnapshot.getData();

    }


    public Map<String, Object> upsertProfile(Map<String, Object> profileDocumentMap, String id) throws ExecutionException, InterruptedException {

        String key = (String) profileDocumentMap.get(id);
        if(key !=null){
            profileDocumentMap.remove(id);
        }else
        {
            key = String.valueOf(new Random().nextDouble());
        }
        ApiFuture<WriteResult> profileAddedFuture = firestore.collection(profileColleciton).document(key).set(profileDocumentMap);

        Map<String, Object> result = new HashMap<>();

        Timestamp timestamp = profileAddedFuture.get().getUpdateTime();
        result.put("response", HttpStatus.ACCEPTED.getReasonPhrase());
        result.put("lastUpdatedTime", timestamp.toSqlTimestamp());
        result.put("currentTime", Timestamp.now().toSqlTimestamp());
        return  result;
    }
}
