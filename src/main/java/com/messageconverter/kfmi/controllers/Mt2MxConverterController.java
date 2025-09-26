package com.messageconverter.kfmi.controllers;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.messageconverter.kfmi.services.GetDataFrom50;
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
        @JacksonXmlProperty(localName = "CdtTrfTx")
        private CdtTrfTx cdtTrfTx;
    
        public FiToFiCstmrCdtTrf() {}
        public FiToFiCstmrCdtTrf(Map<String, Object> content) {
            this.grpHdr = new GrpHdr(content);
            this.cdtTrfTx = new CdtTrfTx(content);
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
                this.ttlIntrBkSttlmAmt = new TtlIntrBkSttlmAmt((String) content.get("settledAmount"), (String) content.get("settledCurrency"));
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
    
        public static class CdtTrfTx {
            @JacksonXmlProperty(localName = "PmtId")
            private PmtId pmtId;
            @JacksonXmlProperty(localName = "IntrBkSttlmAmt")
            private IntrBkSttlmAmt intrBkSttlmAmt;
            @JacksonXmlProperty(localName = "ChrgBr")
            private String chrgBr;
            @JacksonXmlProperty(localName = "Dbtr")
            private Dbtr dbtr;

            public CdtTrfTx() {}
            public CdtTrfTx(Map<String, Object> content) {
                this.pmtId = new PmtId(content);
                this.intrBkSttlmAmt = new IntrBkSttlmAmt((String) content.get("settletAmount"), (String) content.get("settledCurrency"));
                this.chrgBr = (String) content.get("charges");
                this.dbtr = new Dbtr(content);
            }

            public PmtId getPmtId() { return this.pmtId; }
            public void setPmtId(PmtId pmtId) { this.pmtId = pmtId; }
            public IntrBkSttlmAmt getIntrBkSttlmAmt() { return this.intrBkSttlmAmt; }
            public void setIntrBkSttlmAmt(IntrBkSttlmAmt intrBkSttlmAmt) { this.intrBkSttlmAmt = intrBkSttlmAmt; }
            public Dbtr getDbtr() { return this.dbtr; }
            public void setDbtr(Dbtr dbtr) { this.dbtr = dbtr; }

            public static class PmtId {
                @JacksonXmlProperty(localName = "InstrId")
                private String instrId;
                @JacksonXmlProperty(localName = "EndToEndId")
                private String endToEndId;
                @JacksonXmlProperty(localName = "TxId")
                private String txId;
                @JacksonXmlProperty(localName = "UETR")
                private String uetr;

                public PmtId() {}
                public PmtId(Map<String, Object> content) {
                    this.instrId = (String) content.get("sendersReference");
                    this.endToEndId = "end-to-end-id";
                    this.txId = "txidtxidtxid";
                    this.uetr = String.valueOf(UUID.randomUUID());
                }

                public String getInstrId() { return this.instrId; }
                public void setInstrId(String instrId) { this.instrId = instrId; }
                public String getEndToEndId() { return this.endToEndId; }
                public void setEndToEndId(String endToEndId) { this.endToEndId = endToEndId; }
                public String getTxId() { return this.txId; }
                public void setTxId(String txId) { this.txId = txId; }
                public String getUetr() { return this.uetr; }
                public void sertUetr(String uetr) { this.uetr = uetr; }
            }
        
            public static class IntrBkSttlmAmt {
                @JacksonXmlProperty(isAttribute = true, localName = "Ccy")
                private String ccy;
                @JacksonXmlText
                private String value;

                public IntrBkSttlmAmt() {}
                public IntrBkSttlmAmt(String value, String ccy) {
                    this.value = value;
                    this.ccy = ccy;
                }

                public String getCcy() { return ccy; }
                public void setCcy(String ccy) { this.ccy = ccy; }
                public String getValue() { return value; }
                public void setValue(String value) { this.value = value; }
            }
        
            // Masih harus diperbaiki!!!!!!
            public static class Dbtr {
                @JacksonXmlProperty(localName = "Nm")
                private String nm;
                @JacksonXmlProperty(localName = "Id")
                private Id id;

                public Dbtr() {}
                public Dbtr(Map<String, Object> content) {
                    String orderingCustomer = (String) content.get("orderingCustomer");
                    this.nm = GetDataFrom50.extractName(orderingCustomer);

                    if (((String) content.get("orderingCustomerType")).equals("50F")) {
                        this.id = new Id(GetDataFrom50.extractLastLine((String) content.get("orderingCustomer")));
                    }
                }

                public String getNm() { return this.nm; } 
                public void setNm(String nm) { this.nm = nm; }
                public Id getId() { return this.id; }
                public void setId(Id id) { this.id = id; }

                // dwjcdekjdskjdckjldskh
                public static class Id {
                    @JacksonXmlProperty(localName = "OrgId")
                    private OrgId orgId;
    
                    public Id() {}
                    public Id(String OrgId) {
                        this.orgId = new OrgId(OrgId);
                    }
    
                    public OrgId getOrgId() { return this.orgId; }
                    public void setOrgId(OrgId orgId) { this.orgId = orgId; }
    
                    public static class OrgId {
                        @JacksonXmlProperty(localName = "Lei")
                        private String lei;
    
                        public OrgId() {}
                        public OrgId(String lei) {
                            this.lei = lei;
                        }
    
                        public String setLei() { return this.lei; }
                        public void getLei(String lei) { this.lei = lei; }
                    }
                }
            }
        }
    
    }
    
}

