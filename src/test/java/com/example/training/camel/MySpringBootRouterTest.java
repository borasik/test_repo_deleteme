package com.example.training.camel;

import java.util.concurrent.TimeUnit;

import com.example.model.User;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.ShutdownTimeout;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
@EnableAutoConfiguration
@ShutdownTimeout(value = 30, timeUnit = TimeUnit.SECONDS)
public class MySpringBootRouterTest {

    @Autowired
	private CamelContext camelContext;
    
	@Autowired
	private ProducerTemplate producerTemplate;

	@Test
    void testHelloRoute() throws Exception{
		MockEndpoint mock = camelContext.getEndpoint("mock:out:hello", MockEndpoint.class);

		// Eenhance/modify an existing route "hello" with fictional final endpoint and replacement of FROM endpoint
		AdviceWith.adviceWith(camelContext, "hello",
				// intercepting an exchange on route
				r -> {
					// replacing consumer with direct component
					r.replaceFromWith("direct:hello"); // this is where we'll send test messages to
					//add fictional final endpoint to the whole pipeline, this is where everything will end up going to
					r.weaveAddLast().to("mock:out:hello");
				}
		);

		// setting expectations
		mock.expectedMessageCount(1);
		mock.expectedBodiesReceived("{\"firstName\":\"AleXXander\",\"lastName\":\"Yudin\"}");

		// invoking consumer
		producerTemplate.sendBody("direct:hello", "{\"firstName\":\"Valeriy\",\"lastName\":\"Yudin\"}");
		// asserting mock is satisfied
		mock.assertIsSatisfied();
    }


	@Test
    void testGreetingRoute() throws Exception{
		MockEndpoint mock = camelContext.getEndpoint("mock:out:greet", MockEndpoint.class);	
		
		// Advice(enhance/modify) an existing route "greeting"
		AdviceWith.adviceWith(camelContext, "greeting",
				// intercepting an exchange on route
				r -> {
					// replacing consumer with direct component
					r.replaceFromWith("direct:start");
					//adding test endpoint to the end
					r.weaveAddLast().to("mock:out:greet");
				}
		);
		// setting expectations
		mock.expectedMessageCount(2);

		//mock.expectedBodiesReceived("Nice to meet you stranger");
		
		//will expect 2 messages received:
		mock.expectedBodiesReceived( 
			new String[]{
			"Nice to meet you stranger", 
			"Nice to meet you master Val!"
		});

		// invoking consumer, sending 2 messages:
		producerTemplate.sendBody("direct:start", "Bla"); // this should succeed
		producerTemplate.sendBody("direct:start", "Val");// this should succeed
		
		// asserting mock is satisfied
		mock.assertIsSatisfied();
    }	
	
}
