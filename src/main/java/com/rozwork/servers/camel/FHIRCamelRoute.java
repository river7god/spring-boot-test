package com.rozwork.servers.camel;

import org.apache.camel.builder.RouteBuilder;

import org.springframework.stereotype.Component;

@Component
public class FHIRCamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:startRoute")
        .to("https://hapi.fhir.org/baseDstu3/Patient?gender=male")
        .process(exchange -> {
            String patientData = exchange.getIn().getBody(String.class);

            System.out.println("-------patientData--------" + patientData);
            // 在这里处理FHIR患者数据
        })
        .to("log:receivedData");
    }
}
