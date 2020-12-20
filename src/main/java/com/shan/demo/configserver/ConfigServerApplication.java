package com.shan.demo.configserver;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
@Slf4j
public class ConfigServerApplication {


	@Value("${GOOGLE_APPLICATION_CREDENTIALS}")
	private String jsonPath;


	public static void main(String[] args) {
		log.info("hello world");
		SpringApplication.run(ConfigServerApplication.class, args);
	}


	@Bean
	public Firestore firestore(){

		Firestore firestore = null;
		log.info(" initializing firestore with key " + jsonPath);
		GoogleCredentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
					.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
			// Create an authorized Datastore service using Application Default Credentials.
			firestore = FirestoreOptions.newBuilder().setCredentials(credentials).build().getService();

		} catch (IOException e) {
			log.error(e.getMessage());
		}

//		sample to show that we are in right place
		firestore.listCollections().forEach(doc -> log.info( "  reterieved firestore collection  " + doc.getId()));

		return firestore;

	}
}
