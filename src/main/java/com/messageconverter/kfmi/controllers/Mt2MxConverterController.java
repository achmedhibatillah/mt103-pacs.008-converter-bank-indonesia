package com.messageconverter.kfmi.controllers;

import java.util.Map;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JacksonXmlRootElement(localName = "Document")
public class Mt2MxConverterController {

    @JacksonXmlProperty(localName = "GrpHdr")
    private GrpHdr grpHdr;

    public Mt2MxConverterController() {}
    public Mt2MxConverterController(Map<String, Object> content) {
        this.grpHdr = new GrpHdr(content);
    }

    public GrpHdr getGrpHdr() { return grpHdr; }
    public void setGrpHdr(GrpHdr grpHdr) { this.grpHdr = grpHdr; }

    public static class GrpHdr {

        @JacksonXmlProperty(localName = "MsgId")
        private String msgId;
        @JacksonXmlProperty(localName = "CreDtTm")
        private String creDtTm;

        public GrpHdr() {}
        public GrpHdr(Map<String, Object> content) {
            this.msgId = (String) content.get("sendersReference");
            this.creDtTm = (String) "x";
        }

        public String getMsgId() { return msgId; }
        public void setMsgId(String msgId) { this.msgId = msgId; }
        public String getCreDtTm() { return creDtTm; }
        public void setCreDtTm(String creDtTm) { this.creDtTm = creDtTm; }
    }
}

