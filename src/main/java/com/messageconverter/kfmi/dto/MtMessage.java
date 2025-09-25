package com.messageconverter.kfmi.dto;

import jakarta.validation.constraints.NotBlank;

public class MtMessage {
    
    @NotBlank(message = "not blank")
    private String message;

    public String getMessage() {
        return message;
    }

}
