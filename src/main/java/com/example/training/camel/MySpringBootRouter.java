package com.example.training.camel;

import com.example.model.User;
import com.example.training.camel.processor.TestProcessor;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.camel.component.jackson.JacksonDataFormat;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    @Autowired
    TestProcessor myProcessor;

    

    @Override
    public void configure() {
        /**
         * vyudin
         * will be using local to the project path for now with 
         * 'Test' subfolder
         * ideally this should be coming from configuration
         */

        //JacksonDataFormat jsonDataFormat = new JacksonDataFormat(User.class);

        from("file://./Test?fileName=user.json&noop=true")
        .routeId("hello")
        .log("FileContents:${body}")
        .process(myProcessor) // doing unmarshalling in processor seems a lot simpler

        //.unmarshal().json(JsonLibrary.Jackson, User.class)
        //.unmarshal(new JacksonDataFormat(User.class) )
        .to("file://./Test?fileName=modifiedUser.json");
    }

}
