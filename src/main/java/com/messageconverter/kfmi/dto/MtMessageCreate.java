package com.messageconverter.kfmi.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class MtMessageCreate {

    private String bicSender;
    private String session;
    private String sequence;

    private String bicReceiver;
    private String priority;

    private String disabledBankingPriorityCode;
    private String disabledMessageUserReference;
    private String bankingPriorityCode;
    private String messageUserReference;

    private String sendersReference;
    private String bankOperationCode;

    private String valueDate;
    private String settledCurrency;
    private String settledAmount;

    private Boolean disabledInstructed;
    private String instructedCurrency;
    private String instructedAmount;

    private String select50;
    private String _50account;
    private String _50bic;
    private String _50name;
    private String _50address;
    private String _50identifier;

    private String select59;
    private String _59account;
    private String _59name;
    private String _59address;
    private String _59bic;

    private Boolean disabledRemittanceInfo;
    private String remittanceInfo;  
    private String charges;

    public MtMessageCreate() {}

    public String getBicSender() { return bicSender; }
    public void setBicSender(String bicSender) { this.bicSender = bicSender; }

    public String getSession() { return session; }
    public void setSession(String session) { this.session = session; }

    public String getSequence() { return sequence; }
    public void setSequence(String sequence) { this.sequence = sequence; }

    public String getBicReceiver() { return bicReceiver; }
    public void setBicReceiver(String bicReceiver) { this.bicReceiver = bicReceiver; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getDisabledBankingPriorityCode() { return disabledBankingPriorityCode; }
    public void setDisabledBankingPriorityCode(String disabledBankingPriorityCode) { this.disabledBankingPriorityCode = disabledBankingPriorityCode; }

    public String getDisabledMessageUserReference() { return disabledMessageUserReference; }
    public void setDisabledMessageUserReference(String disabledMessageUserReference) { this.disabledMessageUserReference = disabledMessageUserReference; }

    public String getBankingPriorityCode() { return bankingPriorityCode; }
    public void setBankingPriorityCode(String bankingPriorityCode) { this.bankingPriorityCode = bankingPriorityCode; }

    public String getMessageUserReference() { return messageUserReference; }
    public void setMessageUserReference(String messageUserReference) { this.messageUserReference = messageUserReference; }

    public String getSendersReference() { return sendersReference; }
    public void setSendersReference(String sendersReference) { this.sendersReference = sendersReference; }

    public String getBankOperationCode() { return bankOperationCode; }
    public void setBankOperationCode(String bankOperationCode) { this.bankOperationCode = bankOperationCode; }

    public String getValueDate() { return valueDate; }
    public void setValueDate(String valueDate) { this.valueDate = valueDate; }
    public String getSettledCurrency() { return settledCurrency; }
    public void setSettledCurrency(String settledCurrency) { this.settledCurrency = settledCurrency; }
    public String getSettledAmount() { return settledAmount; }
    public void setSettledAmount(String settledAmount) { this.settledAmount = settledAmount; }

    public Boolean getDisabledInstructed() { return disabledInstructed; }
    public void setDisabledInstructed(Boolean disabledInstructed) { this.disabledInstructed = disabledInstructed; }
    public String getInstructedCurrency() { return instructedCurrency; }
    public void setInstructedCurrency(String instructedCurrency) { this.instructedCurrency = instructedCurrency; }
    public String getInstructedAmount() { return instructedAmount; }
    public void setInstructedAmount(String instructedAmount) { this.instructedAmount = instructedAmount; }

    public String getSelect50() { return select50; }
    public void setSelect50(String select50) { this.select50 = select50; }
    public String get_50account() { return _50account; }
    public void set_50account(String _50account) { this._50account = _50account; }
    public String get_50bic() { return _50bic; }
    public void set_50bic(String _50bic) { this._50bic = _50bic; }
    public String get_50name() { return _50name; }
    public void set_50name(String _50name) { this._50name = _50name; }
    public String get_50address() { return _50address; }
    public void set_50address(String _50address) { this._50address = _50address; }
    public String get_50identifier() { return _50identifier; }
    public void set_50identifier(String _50identifier) { this._50identifier = _50identifier; }

    public String getSelect59() { return select59; }
    public void setSelect59(String select59) { this.select59 = select59; }
    public String get_59account() { return _59account; }
    public void set_59account(String _59account) { this._59account = _59account; }
    public String get_59name() { return _59name; }
    public void set_59name(String _59name) { this._59name = _59name; }
    public String get_59address() { return _59address; }
    public void set_59address(String _59address) { this._59address = _59address; }
    public String get_59bic() { return _59bic; }
    public void set_59bic(String _59bic) { this._59bic = _59bic; }

    public Boolean getDisabledRemittanceInfo() { return disabledRemittanceInfo; }
    public void setDisabledRemittanceInfo(Boolean disabledRemittanceInfo) { this.disabledRemittanceInfo = disabledRemittanceInfo; }
    public String getRemittanceInfo() { return remittanceInfo; }
    public void setRemittanceInfo(String remittanceInfo) { this.remittanceInfo = remittanceInfo; }   

    public String getCharges() { return charges; }
    public void setCharges(String charges) { this.charges = charges; }

    public Map<String, Object> getRequest() {
        Map<String, Object> request = new LinkedHashMap<>();
        request.put("bicSender", bicSender);
        request.put("session", session);
        request.put("sequence", sequence);

        request.put("bicReceiver", bicReceiver);
        request.put("priority", priority);

        request.put("disabledBankingPriorityCode", disabledBankingPriorityCode);
        request.put("disabledMessageUserReference", disabledMessageUserReference);
        request.put("bankingPriorityCode", bankingPriorityCode);
        request.put("messageUserReference", messageUserReference);

        request.put("sendersReference", sendersReference);
        request.put("bankOperationCode", bankOperationCode);

        request.put("valueDate", valueDate);
        request.put("settledCurrency", settledCurrency);
        request.put("settledAmount", settledAmount);

        request.put("disabledInstructed", disabledInstructed);
        request.put("instructedCurrency", instructedCurrency);
        request.put("instructedAmount", instructedAmount);

        request.put("select50", select50);
        request.put("_50account", _50account);
        request.put("_50bic", _50bic);
        request.put("_50name", _50name);
        request.put("_50address", _50address);
        request.put("_50identifier", _50identifier);

        request.put("select59", select59);
        request.put("_59account", _59account);
        request.put("_59name", _59name);
        request.put("_59address", _59address);
        request.put("_59bic", _59bic);

        request.put("disabledRemittanceInfo", disabledRemittanceInfo);
        request.put("remittanceInfo", remittanceInfo);        
        request.put("charges", charges);

        return request;
    }

}
