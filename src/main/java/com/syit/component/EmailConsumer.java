package com.syit.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.syit.model.EmailRequest;
import com.syit.model.SystemMessage;

@Component
public class EmailConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);

    @JmsListener(destination = "email-queue")
    public void messageListener(EmailRequest er) {
        LOGGER.info("Message received! {}", er);
    }
}
