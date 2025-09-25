package com.messageconverter.kfmi.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class AppInfoController {
    
    public Map<String, Object> getAppInfo() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("application", "Financial Message Converter");
        response.put("version", "1.0");
        
        Map<String, Object> response_owner = new LinkedHashMap<>();
        response_owner.put("division", "Kelompok Implementasi Pengembangan Sistem Financial Market Infrastructure");
        response_owner.put("departement", "Departemen Penyelenggaraan Sistem Pembayaran");
        response_owner.put("organization", "Kantor Pusat Bank Indonesia");

        response.put("owner", response_owner);

        return response;

    }

}
