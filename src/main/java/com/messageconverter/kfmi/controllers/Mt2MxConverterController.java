package com.messageconverter.kfmi.controllers;

import org.springframework.stereotype.Component;

@Component
public class Mt2MxConverterController {
    
    public String convertMessage(String message) {

        if ("hello".equalsIgnoreCase(message)) {
            return "hello, kami senang!";
        } else if ("hai".equalsIgnoreCase(message)) {
            return "hai, ada yang bisa kami bantu?";
        } else {
            return "kosong!";
        }

    }

}
