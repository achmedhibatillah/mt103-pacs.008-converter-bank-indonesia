package com.messageconverter.kfmi.controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GenerateMtMessageController {
    
    public Map<String, Object> generateMessage(Map<String, Object> request) {

        String bicSender = getLogicalTerminal(request.get("bicSender").toString());

        int sess;
        if (request.get("session") != null && !request.get("session").toString().isEmpty()) {
            sess = Integer.parseInt(request.get("session").toString());
            sess = sess % 10000;
        } else {
            sess = (int)(Math.random() * 9000) + 1000;
        }
        String session = String.format("%04d", sess);

        int seq;
        if (request.get("sequence") != null && !request.get("sequence").toString().isEmpty()) {
            seq = Integer.parseInt(request.get("sequence").toString());
            seq = seq % 1000000; // ambil 6 digit terakhir
        } else {
            seq = (int)(Math.random() * 999999) + 1;
        }
        String sequence = String.format("%06d", seq);

        
        String bicReceiver = getLogicalTerminal(request.get("bicReceiver").toString());

        StringBuilder sb = new StringBuilder();
        sb.append("{1:F01" + bicSender + session + sequence + "}\n");
        sb.append("{2:I103" + bicReceiver + request.get("priority") + "}\n");

        boolean disabledBPC = Boolean.parseBoolean(String.valueOf(request.get("disabledBankingPriorityCode")));
        boolean disabledMUR = Boolean.parseBoolean(String.valueOf(request.get("disabledMessageUserReference")));
        if (!disabledBPC || !disabledMUR) {
            sb.append("{3:");
            if (!"".equals(request.get("bankingPriorityCode"))) {
                sb.append("{113:" + request.get("bankingPriorityCode") + "}");
            }
            if (!"".equals(request.get("messageUserReference"))) {
                sb.append("{108:" + request.get("messageUserReference") + "}");
            }            
            sb.append("}\n");
        }

        sb.append("{4:\n");
        sb.append(":20:" + request.get("sendersReference") + "\n");
        sb.append(":23B:" + request.get("bankOperationCode") + "\n");

        sb.append(":32A:" + request.get("valueDate") + request.get("settledCurrency"));

        String settledAmount = getAmountFormat(request.get("settledAmount").toString());
        sb.append(settledAmount + "\n");

        Boolean disabledInstructed = Boolean.parseBoolean(String.valueOf(request.get("disabledInstructed")));
        if (!disabledInstructed) {
            String instructedAmount = getAmountFormat(request.get("instructedAmount").toString());
            sb.append(":33B:" + request.get("instructedCurrency") + instructedAmount + "\n");
        }

        sb.append(
            processAFK(
                request.get("select50").toString(),
                request.get("_50account").toString(),
                request.get("_50bic").toString(),
                request.get("_50name").toString(), 
                request.get("_50address").toString(),
                request.get("_50identifier").toString()
            ) + "\n"
        );

        sb.append(
            process59(
                request.get("select59").toString(),
                request.get("_59account").toString(),
                request.get("_59name").toString(), 
                request.get("_59address").toString(),
                request.get("_59bic").toString()
            ) + "\n"
        );

        if (!Boolean.parseBoolean(String.valueOf(request.get("disabledRemittanceInfo")))) {
            sb.append(":70:" + request.get("remittanceInfo") + "\n");
        }

        sb.append(":71A:" + request.get("charges") + "\n");
        sb.append("-}"); 

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("content", sb.toString());

        return response;
    }

    private String getLogicalTerminal(String bic) {
        Random random = new Random();
        char LT = (char) ('A' + random.nextInt(26));

        if (bic.length() < 11) {
            bic += LT + "XXX";
        } else {
            String bicBank = bic.substring(0, 8);
            String bicBranch = bic.substring(8, 11);
            bic = bicBank + LT + bicBranch;
        }

        return bic;
    } 

    private String getAmountFormat(String settledAmount) {
        settledAmount = String.format("%03d", Long.parseLong(settledAmount));
        String main_settledAmount = settledAmount.substring(0, settledAmount.length() - 2);
        String decimal_settledAmount = settledAmount.substring(settledAmount.length() - 2);
        settledAmount = main_settledAmount + "," + decimal_settledAmount;

        return settledAmount;
    }

    private String processAFK(
            String selectAFK,
            String account,
            String bic,
            String name,
            String address,
            String identifier
    ) {
        String lastChar = selectAFK.substring(selectAFK.length() - 1);
        StringBuilder sb = new StringBuilder();

        sb.append(":").append(selectAFK).append(":");

        switch (lastChar) {
            case "A":
                sb.append("/").append(account).append("\n");
                sb.append(bic);
                break;

            case "F":
                sb.append("/").append(account).append("\n");
                sb.append(name).append("\n");
                sb.append(address).append("\n");
                if (identifier != null && !identifier.isEmpty()) {
                    sb.append(identifier);
                }
                break;

            case "K":
                sb.append("/").append(account).append("\n");
                sb.append(name).append("\n");
                sb.append(address);
                break;

            default:
                return "";
        }

        return sb.toString();
    }

    private String process59(
        String select59,
        String account,
        String name,
        String address,
        String bic
    ) {
        StringBuilder sb = new StringBuilder();

        sb.append(":").append(select59).append(":");
        
        sb.append("/").append(account).append("\n");
        sb.append(name).append("\n");
        sb.append(address);
        
        if (select59.equals("59A".toString())) {
            sb.append("\n").append(bic);
        }

        return sb.toString();
    }
}
