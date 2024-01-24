package com.markp.importer.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class MyMessageConsumer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "${queue-name}")
    public void receiveMessage(String message) {
        // Print the message to the console
        System.out.println("Received message: " + message);
    }
}
