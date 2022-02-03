package com.example.training.camel;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MySpringBootApplication {


    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }

    @Bean
	public ObjectMapper jacksonMapper() {
		ObjectMapper jackson = new ObjectMapper();
        jackson.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		jackson.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		return jackson;
	}


}
