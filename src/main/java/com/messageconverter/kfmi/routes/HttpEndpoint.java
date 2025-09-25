package com.messageconverter.kfmi.routes;

import org.springframework.web.bind.annotation.RestController;

import com.messageconverter.kfmi.controllers.AppInfoController;
import com.messageconverter.kfmi.controllers.GenerateMtMessageController;
import com.messageconverter.kfmi.controllers.ReadMtMessageController;
import com.messageconverter.kfmi.controllers.Mt2MxConverterController;
import com.messageconverter.kfmi.dto.MtMessage;
import com.messageconverter.kfmi.dto.MtMessageCreate;
import com.messageconverter.kfmi.dto.MtMessageRead;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class HttpEndpoint {
    
    private final AppInfoController appInfoController;
    private final Mt2MxConverterController mt2MxConverterController;
    private final GenerateMtMessageController generateMtMessageController;
    private final ReadMtMessageController readMtMessageController;

    public HttpEndpoint(
        AppInfoController appInfoController,
        Mt2MxConverterController mt2MxConverterController,
        GenerateMtMessageController generateMtMessageController,
        ReadMtMessageController readMtMessageController
    ) {
        this.appInfoController = appInfoController;
        this.mt2MxConverterController = mt2MxConverterController;
        this.generateMtMessageController = generateMtMessageController;
        this.readMtMessageController = readMtMessageController;
    }

    @GetMapping("/")
    public Map<String, Object> getAppInfo() {
        return appInfoController.getAppInfo();
    }

    @PostMapping("/readmtmessage")
    public Map<String, Object> readMtMessage(@RequestBody MtMessageRead request) {
        return readMtMessageController.readMessage(request.getMtMessage());
    }
    
    @PostMapping("/generatemtmessage")
    public Map<String, Object> generateMtMessage(@RequestBody MtMessageCreate request) {       
        return generateMtMessageController.generateMessage(request.getRequest());
    }

    @PostMapping("/mt2mxconverter")
    public String mt2MxConverter(@Valid @RequestBody MtMessage request) {        
        return mt2MxConverterController.convertMessage(request.getMessage());
    }
    
}
