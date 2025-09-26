package com.messageconverter.kfmi.controllers.pacs008structures;

import java.util.Map;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JacksonXmlRootElement(localName = "Document")
public class Pacs008 {

    @JacksonXmlProperty(localName = "GrpHdr")
    private Header Header;

    public Pacs008() {}
    public Pacs008(Map<String, Object> content) {
        this.Header = new Header(content);
    }

    public Header getHeader() { return Header; }
    public void setHeader(Header Header) { this.Header = Header; }

    public static class Header {
        @JacksonXmlProperty(localName = "MsgId")
        private String MsgId;

        public Header() {}
        public Header(Map<String, Object> content) {
            this.MsgId = (String) content.get("bicSender");
        }

        public String getMsgId() { return MsgId; }
        public void setMsgId(String MsgId) { this.MsgId = MsgId; }
    }
    
}
