package com.markp.importer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markp.importer.model.ProductMessage;
import com.markp.importer.service.ImporterProductService;
import com.markp.importer.service.ImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class ProductMessageConsumer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ImporterService importerService;

    ProductMessage productMessage;

    @JmsListener(destination = "${queue-name}")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            productMessage = objectMapper.readValue(message, ProductMessage.class);
            importerService.processMessage(productMessage);
        } catch (JsonProcessingException e) {
            System.out.println("JsonProcessing error" + e);
        }
    }
}
