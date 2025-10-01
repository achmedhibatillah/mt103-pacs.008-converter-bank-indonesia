package com.messageconverter.kfmi.routes;

import org.springframework.web.bind.annotation.RestController;

import com.messageconverter.kfmi.controllers.AppInfoController;
import com.messageconverter.kfmi.controllers.GenerateMtMessageController;
import com.messageconverter.kfmi.controllers.Mt2MxConverterController;
import com.messageconverter.kfmi.controllers.ReadMtMessageController;
// import com.messageconverter.kfmi.controllers.Mt2MxConverterController;
import com.messageconverter.kfmi.dto.MtMessageCreate;
import com.messageconverter.kfmi.dto.MtMessageRead;


import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class HttpEndpoint {
    
    private final AppInfoController appInfoController;
    // private final Mt2MxConverterController mt2MxConverterController;
    private final GenerateMtMessageController generateMtMessageController;
    private final ReadMtMessageController readMtMessageController;

    public HttpEndpoint(
        AppInfoController appInfoController,
        // Mt2MxConverterController mt2MxConverterController,
        GenerateMtMessageController generateMtMessageController,
        ReadMtMessageController readMtMessageController
    ) {
        this.appInfoController = appInfoController;
        // this.mt2MxConverterController = mt2MxConverterController;
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
    @PostMapping(value = "/mt2mxconverter", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> mt2MxConverter(@RequestBody MtMessageRead request) {
        Map<String, Object> mtMessage = readMtMessageController.readMessage(request.getMtMessage());
    
        if ("invalid".equals(mtMessage.get("status"))) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mtMessage);
        }
    
        @SuppressWarnings("unchecked")
        Map<String, Object> content = (Map<String, Object>) mtMessage.get("content");
    
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(new Mt2MxConverterController(content));
    }    

}
