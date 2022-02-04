package com.example.training.camel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.springframework.beans.factory.annotation.Autowired;



public class MyTypeConverter implements TypeConverters {

    private ObjectMapper mapper;
   
    @Autowired
    public MyTypeConverter(ObjectMapper mapper){
        this.mapper = mapper;
    }

    @Converter
    public InputStream toInputStream(User user) throws Exception {
        String json = mapper.writeValueAsString(user);
        InputStream targetStream = new ByteArrayInputStream(json.getBytes());
        return targetStream;
    }
}   