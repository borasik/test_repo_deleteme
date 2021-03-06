package com.example.training.camel.processor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TestProcessor implements Processor{

    @Autowired
    ObjectMapper jackson;

 
    private static final Logger Log = LoggerFactory.getLogger(TestProcessor.class);

    
    public void process(Exchange exchange) throws Exception {
     
        //get exchange message body
        String body = exchange.getIn().getBody(String.class);
        if (body == null || body.trim().length()==0)
            return;
        
        //load user from body String
        User user =  jackson.readValue(body, User.class);
        Log.info("Loaded user from file: " + user);
        
        //change firstName
        user.setFirstName("Alexander");
        Log.info("Altered user: " + user);
        

        String json = jackson.writeValueAsString(user);
        //InputStream targetStream = new ByteArrayInputStream(json.getBytes());
        //exchange.getIn().setBody(targetStream);
        exchange.getMessage().setBody(json);

        /**
         * vyudin
         * At this point I'm not sure if there is out of the box serialization method
         * in Camel, so will do it manually for now
        */ 
        /*
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        InputStream bis = null;
        byte[] userBytes;

        //jackson.writeValue(out, value);

        try {
            out = new ObjectOutputStream(bos);   
            out.writeObject(user);
            out.flush();
            userBytes = bos.toByteArray();
            // construct InputStream to pass to the message body
            bis = new ByteArrayInputStream(userBytes);
        }catch (IOException exc) {
            Log.error(exc.getMessage());
            throw exc;
        }finally {
            try {
                bos.close();
            } catch (IOException ignore) {}
        }
        //set message body    
        if(bis!=null )
            exchange.getIn().setBody(bis);
*/
  

        
    }
    
    
}
