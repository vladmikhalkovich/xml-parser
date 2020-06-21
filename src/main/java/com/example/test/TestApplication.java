package com.example.test;

import com.example.test.storage.StorageProperties;
import com.example.test.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return args -> storageService.init();
	}

	@Bean
	Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

		marshaller.setClassesToBeBound(com.example.test.entity.User.class,
										com.example.test.entity.Users.class);

		Map<String, Object> properties = new HashMap<>();
		properties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setMarshallerProperties(properties);

//		marshaller.setAdapters();

		return marshaller;
	}

}
