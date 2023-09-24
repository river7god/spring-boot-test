package com.rozwork.servers.camel;

import org.apache.camel.builder.RouteBuilder;

import org.springframework.stereotype.Component;

@Component
public class MqCamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
      /*  from("fhir://read/Patient?serverUrl=http://hapi.fhir.org/baseDstu3")
            .process(exchange -> {
                String patientData = exchange.getIn().getBody(String.class);

                System.out.println("-------patientData--------" + patientData);
                // 在这里处理FHIR患者数据
            })  */
            //from("rabbitmq:fanoutExchange?queue=fanoutq3")
            from("rabbitmq:fanoutq3?queue=fanoutq3&autoDelete=false")
            .process(exchange -> {
                String patientData = exchange.getIn().getBody(String.class);

                System.out.println("-------patientData--------" + patientData);
                // 在这里处理FHIR患者数据
            })
            .to("log:patientInfo") // 可以将结果记录到日志
            .to("direct:processPatient"); // 将结果传递给下一步处理
    }
}
