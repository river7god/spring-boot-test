package com.rozwork.servers.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyCamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:myTimer?period=100000")
            .setBody().constant("Hello, Camel!")
            .to("log:myCamelRoute");
    }
}
 