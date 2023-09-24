package com.rozwork.servers.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;

@Service
public class ProducerService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public Object helloWorldSend(String message) throws AmqpException, UnsupportedEncodingException {
		//设置部分请求参数
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

		//发消息
		rabbitTemplate.send("helloWorldqueue",new Message(message.getBytes("UTF-8"),messageProperties));
		return message;
	}

	public Object workqueueSend(String message) throws AmqpException, UnsupportedEncodingException {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
		//制造多个消息进行发送操作
		for (int i = 0; i <10 ; i++) {
			rabbitTemplate.send("work_sb_mq_q",  new Message(message.getBytes("UTF-8"),messageProperties));
		}
		return message;
	}


	public Object fanoutSend(String message) throws AmqpException, UnsupportedEncodingException {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
		//fanout模式只往exchange里发送消息。分发到exchange下的所有queue
		rabbitTemplate.send("fanoutExchange", "", new Message(message.getBytes("UTF-8"),messageProperties));
		return message;
	}

	public Object routingSend(String routingKey,String message) throws AmqpException, UnsupportedEncodingException {

		if(null == routingKey) {
			routingKey="china.changsha";
		}
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
		//fanout模式只往exchange里发送消息。分发到exchange下的所有queue
		rabbitTemplate.send("directExchange", routingKey, new Message(message.getBytes("UTF-8"),messageProperties));
		return message;
	}

	public Object topicSend(String routingKey,String message) throws AmqpException, UnsupportedEncodingException {

		if(null == routingKey) {
			routingKey="changsha.kf";
		}
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
		//fanout模式只往exchange里发送消息。分发到exchange下的所有queue
		rabbitTemplate.send("topicExchange", routingKey, new Message(message.getBytes("UTF-8"),messageProperties));
		return message;
	}

}
