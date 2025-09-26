package com.messageconverter.kfmi.controllers;

import java.util.Map;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.messageconverter.kfmi.services.GetDateTimeService;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JacksonXmlRootElement(localName = "Document")
public class Mt2MxConverterController {

    @JacksonXmlProperty(localName = "FiToFiCstmrCdtTrf")
    private FiToFiCstmrCdtTrf fiToFiCstmrCdtTrf;

    public Mt2MxConverterController() {}
    public Mt2MxConverterController(Map<String, Object> content) {
        this.fiToFiCstmrCdtTrf = new FiToFiCstmrCdtTrf(content);
    }

    public FiToFiCstmrCdtTrf getFiToFiCstmrCdtTrf() { return this.fiToFiCstmrCdtTrf; }
    public void setFiToFiCstmrCdtTrf(FiToFiCstmrCdtTrf fiToFiCstmrCdtTrf) { this.fiToFiCstmrCdtTrf = fiToFiCstmrCdtTrf; }

    public static class FiToFiCstmrCdtTrf {
        @JacksonXmlProperty(localName = "GrpHdr")
        private GrpHdr grpHdr;
    
        public FiToFiCstmrCdtTrf() {}
        public FiToFiCstmrCdtTrf(Map<String, Object> content) {
            this.grpHdr = new GrpHdr(content);
        }
    
        public GrpHdr getGrpHdr() { return grpHdr; }
        public void setGrpHdr(GrpHdr grpHdr) { this.grpHdr = grpHdr; }
    
        public static class GrpHdr {
            @JacksonXmlProperty(localName = "MsgId")
            private String msgId;
            @JacksonXmlProperty(localName = "CreDtTm")
            private String creDtTm;
            @JacksonXmlProperty(localName = "NbOfTxs")
            private String nbOfTxs;    
            @JacksonXmlProperty(localName = "TtlIntrBkSttlmAmt")
            private TtlIntrBkSttlmAmt ttlIntrBkSttlmAmt;
            @JacksonXmlProperty(localName = "InstgAgt")
            private InstgAgt instgAgt;
        
            public GrpHdr() {}
            public GrpHdr(Map<String, Object> content) {
                this.msgId = (String) content.get("sendersReference");
                this.creDtTm = (String) GetDateTimeService.getDateTime((String) content.get("settledDate"), null);
                this.nbOfTxs = "1";
                this.ttlIntrBkSttlmAmt = new TtlIntrBkSttlmAmt((String) content.get("settledAmount"), "USD");
                this.instgAgt = new InstgAgt((String) content.get("bicSender"));
            }
        
            public String getMsgId() { return msgId; }
            public void setMsgId(String msgId) { this.msgId = msgId; }
            public String getCreDtTm() { return creDtTm; }
            public void setCreDtTm(String creDtTm) { this.creDtTm = creDtTm; }
            public String getNbOfTxs() { return nbOfTxs; }
            public void setNbOfTxs(String nbOfTxs) { this.nbOfTxs = nbOfTxs; }
            public TtlIntrBkSttlmAmt getTtlIntrBkSttlmAmt() { return ttlIntrBkSttlmAmt; }
            public void setTtlIntrBkSttlmAmt(TtlIntrBkSttlmAmt ttlIntrBkSttlmAmt) { this.ttlIntrBkSttlmAmt = ttlIntrBkSttlmAmt; }  
            public InstgAgt getInstgAgt() { return instgAgt; }  
            public void setInstgAgt(InstgAgt instgAgt) { this.instgAgt = instgAgt; }
    
            public static class TtlIntrBkSttlmAmt {
                @JacksonXmlProperty(isAttribute = true, localName = "Ccy")
                private String ccy;
                @JacksonXmlText
                private String value;
    
                public TtlIntrBkSttlmAmt() {}
                public TtlIntrBkSttlmAmt(String value, String ccy) {
                    this.value = value;
                    this.ccy = ccy;
                }
    
                public String getCcy() { return ccy; }
                public void setCcy(String ccy) { this.ccy = ccy; }
                public String getValue() { return value; }
                public void setValue(String value) { this.value = value; }
            }
    
            public static class InstgAgt {
                @JacksonXmlProperty(localName = "FinInstnId")
                private FinInstnId finInstnId;
    
                public InstgAgt() {}
                public InstgAgt(String bicfi) {
                    this.finInstnId = new FinInstnId(bicfi);
                }
    
                public FinInstnId getFinInstnId() { return finInstnId; }
                public void setFinInstnId(FinInstnId finInstnId) { this.finInstnId = finInstnId; }
    
                public static class FinInstnId {
                    @JacksonXmlProperty(localName = "BICFI")
                    private String bicfi;
    
                    public FinInstnId() {}
                    public FinInstnId(String bicfi) {
                        this.bicfi = bicfi;
                    }
    
                    public String getBICFI() { return bicfi; }
                    public void setBICFI(String bicfi) { this.bicfi = bicfi; }
                }
            }
        }
    }
    
}

