package pl.pinakoteka.customersservice.dto;

import lombok.Data;

@Data
public class MessageDto {

    private NotificationType notificationType;
    private MessageType messageType;
    private UserDto recipient;

}


