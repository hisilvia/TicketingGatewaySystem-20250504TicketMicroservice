package com.syit.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.syit.model.SystemMessage;

@Component
public class MessageConsumer {

	/*
	@JmsListener(destination = "myQueue")
	public void receiveMessage(String message) {
		System.out.println("Received message: "+message);
	}
	*/
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @JmsListener(destination = "bridgingcode-queue")
    public void messageListener(SystemMessage systemMessage) {
        LOGGER.info("Message received! {}", systemMessage);
    }
}
