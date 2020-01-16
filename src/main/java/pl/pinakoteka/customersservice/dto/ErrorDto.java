package pl.pinakoteka.customersservice.dto;

import lombok.Data;

@Data
public class ErrorDto {

    private String message;
    private String exceptionClass;
}
