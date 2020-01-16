package pl.pinakoteka.customersservice.service;


import pl.pinakoteka.customersservice.dto.MessageType;

public interface MessageService {
    void sendMessage(String recipient, String message, MessageType messageType);

}

