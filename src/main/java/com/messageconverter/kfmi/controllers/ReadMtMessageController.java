package com.messageconverter.kfmi.controllers;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.*;

import org.springframework.stereotype.Component;

import com.messageconverter.kfmi.services.CcyCheckerService;

@Component
public class ReadMtMessageController {
    
    public Map<String, Object> readMessage(String request) {
        String mtMessage = request;

        Map<String, Object> invalid = new LinkedHashMap<>();

        // Start: Content
        Map<String, Object> content = new LinkedHashMap<>();

        // 1 - Basic Header Block
        Matcher basicHeaderBlockMatcher = Pattern.compile("\\{1:(.*?)\\}").matcher(mtMessage);
        if (basicHeaderBlockMatcher.find()) {
            String basicHeaderBlock = basicHeaderBlockMatcher.group(1);
            switch (basicHeaderBlock.length()) {
                case 25:
                    String bicSenderWithLt = basicHeaderBlock.substring(3, 15);
                    String bicSender = getBic(bicSenderWithLt);
                    content.put("bicSender", bicSender);
                    break;
            
                default:
                    invalid.put("1", "Header length is invalid.");
                    break;
            }
        } else {
            invalid.put("1", "Header is invalid.");
        }

        // 2 - Application Header Block
        Matcher applicationHeaderBlockMatcher = Pattern.compile("\\{2:(.*?)\\}").matcher(mtMessage);
        if (applicationHeaderBlockMatcher.find()) {
            String applicationHeaderBlock = applicationHeaderBlockMatcher.group(1);
            String applicationHeaderIO = applicationHeaderBlock.substring(0, 1);

            String applicationHeaderMessageType = applicationHeaderBlock.substring(1, 4);



            if (applicationHeaderIO.equals("I")) {
                switch (applicationHeaderBlock.length()) {
                    case 17:
                        String bicReceiverWithLt = applicationHeaderBlock.substring(4, 16);
                        String bicReceiver = getBic(bicReceiverWithLt);
                        content.put("bicReceiver", bicReceiver);
                        String priority = applicationHeaderBlock.substring(16, 17);
                        content.put("priority", priority);
                        break;
    
                    default:
                        invalid.put("2", "Header length is invalid.");
                        break;
                }
            } else if (applicationHeaderIO.equals("O")) {
                // Format O: O<MT><Input Time><MIR Date><MIR LT><MIR Session><MIR Seq><Output Date><Output Time><Priority>
                try {
                    Integer applicationHeaderInputTime = Integer.parseInt(applicationHeaderBlock.substring(4, 8));
                    Integer applicationHeaderMirDate = Integer.parseInt(applicationHeaderBlock.substring(8, 14));

                    String bicReceiverWithLt = applicationHeaderBlock.substring(14, 26);
                    String bicReceiver = getBic(bicReceiverWithLt);

                    Integer applicationHeaderMirSession  = Integer.parseInt(applicationHeaderBlock.substring(26, 30));
                    Integer applicationHeaderMirSequence = Integer.parseInt(applicationHeaderBlock.substring(30, 36));
                    Integer applicationHeaderOutputDate  = Integer.parseInt(applicationHeaderBlock.substring(36, 42));
                    Integer applicationHeaderOutputTime  = Integer.parseInt(applicationHeaderBlock.substring(42, 46));
                    String priority    = applicationHeaderBlock.substring(46, 47);

                    content.put("bicReceiver", bicReceiver);
                    content.put("priority", priority);

                } catch (Exception e) {
                    invalid.put("2", "Header O parsing error: " + e.getMessage());
                }
            } else {
                invalid.put("2", "I/O application header is invalid.");
            }

        } else {
            invalid.put("2", "Header is invalid.");
        }

        // 3 - User Header Block
        Matcher userHeaderBlockMatcher = Pattern.compile("\\{3:((?:\\{\\d+:.*?\\})+)\\}").matcher(mtMessage);
        if (userHeaderBlockMatcher.find()) {
            String userHeaderBlock = userHeaderBlockMatcher.group(1);
        
            // 113 - Banking Priority Code
            Matcher bankingPriorityCodeMatcher = Pattern.compile("\\{113:(.*?)\\}").matcher(userHeaderBlock);
            if (bankingPriorityCodeMatcher.find()) {
                String bankingPriorityCode = bankingPriorityCodeMatcher.group(1);
                if (bankingPriorityCode.length() == 4) {
                    content.put("bankingPriorityCode", bankingPriorityCode);
                } else {
                    invalid.put("113", "Banking priority code length is invalid.");
                }
            }
        
            // 108 - Message User Reference
            Matcher messageUserReferenceMatcher = Pattern.compile("\\{108:(.*?)\\}").matcher(userHeaderBlock);
            if (messageUserReferenceMatcher.find()) {
                String messageUserReference = messageUserReferenceMatcher.group(1);
                if (messageUserReference.length() > 16) {
                    invalid.put("108", "Message user reference is too long.");
                } else {
                    content.put("messageUserReference", messageUserReference);
                }
            }
        }     
        
        // 4 - Text Block
        Matcher textBlockMatcher = Pattern.compile("\\{4:(.*?)-\\}", Pattern.DOTALL).matcher(mtMessage);
        if (textBlockMatcher.find()) {
            String textBlock = textBlockMatcher.group(1);

            // 20 - Sender's Reference
            Matcher sendersReferenceMatcher = Pattern.compile(":20:(.*?)(?=:\\d{2}[A-Z]?:)", Pattern.DOTALL).matcher(textBlock);
            if (sendersReferenceMatcher.find()) {
                String senderReference = sendersReferenceMatcher.group(1).trim();
                if (senderReference.length() > 36) {
                    invalid.put("20", "Sender's reference is too long.");
                } else {
                    content.put("sendersReference", senderReference);
                }
            } else {
                invalid.put("20", "Sender's reference not found.");
            }

            // 23B - Bank Operation Code
            Matcher bankOperationCodeMatcher = Pattern.compile(":23B:(.*?)(?=:\\d{2}[A-Z]?:)", Pattern.DOTALL).matcher(textBlock);
            if (bankOperationCodeMatcher.find()) {
                String bankOperationCode = bankOperationCodeMatcher.group(1).trim();
                switch (bankOperationCode) {
                    case "CRED", "SPAY", "SSTD", "SPRI":
                        content.put("bankOperationCode", bankOperationCode);
                        break;
                
                    default:
                        invalid.put("23B", "Invalid input of bank operation code.");
                        break;
                }
            } else {
                invalid.put("23B", "Bank operation code not found.");
            }

            // 32A - Value Date/Currency/Interbank Settled Amount
            Matcher valueDateCurrencyInterbankSettledAmountMatcher = Pattern.compile(":32A:(.*?)(?=:\\d{2}[A-Z]?:)", Pattern.DOTALL).matcher(textBlock);
            if (valueDateCurrencyInterbankSettledAmountMatcher.find()) {
                String valueDateCurrencyInterbankSettledAmount = valueDateCurrencyInterbankSettledAmountMatcher.group(1).trim();

                // Settled Date
                try {
                    String settledDate = valueDateCurrencyInterbankSettledAmount.substring(0, 6);
                    Integer checkSettledDate = Integer.parseInt(settledDate);
                    content.put("settledDate", settledDate);
                } catch (Exception e) {
                    invalid.put("32A", "Invalid settled date.");
                }

                // Settled Currency
                String settledCcy = valueDateCurrencyInterbankSettledAmount.substring(6, 9);
                Boolean isValidCcy = CcyCheckerService.isValid(settledCcy);
                if (isValidCcy) {
                    content.put("settledCurrency", settledCcy);
                } else {
                    invalid.put("32A", "Settled currency (" + settledCcy + ") is invalid.");
                }

                // Settled Amount
                try {
                    String settledAmount = valueDateCurrencyInterbankSettledAmount.substring(9);
                    String settledAmountStr = settledAmount.replace(",", ".");
                    BigDecimal settledAmountValid = new BigDecimal(settledAmountStr);
                    content.put("settledAmount", settledAmount);
                } catch (Exception e) {
                    invalid.put("32A", "Invalid settled amount.");
                }

            } else {
                invalid.put("32A", "Value Date/Currency/Interbank Settled Amount not found.");
            }

            // 33B - Currency/Instructed Amount
            Matcher currencyInstructedAmountMatcher = Pattern.compile(":33B:(.*?)(?=:\\d{2}[A-Z]?:)", Pattern.DOTALL).matcher(textBlock);
            if (currencyInstructedAmountMatcher.find()) {
                String currencyInstructedAmount = currencyInstructedAmountMatcher.group(1).trim();

                // Instructed Currency
                String instructedCcy = currencyInstructedAmount.substring(0, 3);
                Boolean isValidCcy = CcyCheckerService.isValid(instructedCcy);
                if (isValidCcy) {
                    content.put("instructedCurrency", instructedCcy);
                } else {
                    invalid.put("33B", "Instructed currency (" + instructedCcy + ") is invalid.");
                }

                // Instructed Amount
                try {
                    String instructedAmount = currencyInstructedAmount.substring(3);
                    String instructedAmountStr = instructedAmount.replace(",", ".");
                    BigDecimal instructedAmountValid = new BigDecimal(instructedAmountStr);
                    
                    content.put("instructedAmount", instructedAmount);
                } catch (Exception e) {
                    invalid.put("33B", "Invalid instructed amount.");
                }

            }

            // 50A/50F/50K - Ordering Customer
            Matcher orderingCustomerMatcher = Pattern.compile(":(50[AFK]):(.*?)(?=:\\d{2}[A-Z]?:|-})", Pattern.DOTALL).matcher(textBlock);
            if (orderingCustomerMatcher.find()) {
                String orderingCustomerType = orderingCustomerMatcher.group(1);
                String orderingCustomer = orderingCustomerMatcher.group(2).trim();

                content.put("orderingCustomerType", orderingCustomerType);
                content.put("orderingCustomer", orderingCustomer);
            } else {
                invalid.put("50a", "Ordering customer (50A/50F/50K) not found.");
            }

            // 59/59A Beneficiary Customer
            Matcher beneficiaryMatcher = Pattern.compile(":(59[A]?):(.*?)(?=:\\d{2}[A-Z]?:|-})", Pattern.DOTALL).matcher(textBlock);
            if (beneficiaryMatcher.find()) {
                String beneficiaryType = beneficiaryMatcher.group(1);
                String beneficiary = beneficiaryMatcher.group(2).trim();

                content.put("beneficiaryType", beneficiaryType);
                content.put("beneficiary", beneficiary);
            } else {
                invalid.put("59", "Beneficiary customer (59/59A) not found.");
            }

            // 70 - Remittance information
            Matcher remittanceInformationMatcher = Pattern.compile(":70:(.*?)(?=:\\d{2}[A-Z]?:)", Pattern.DOTALL).matcher(textBlock);
            if (remittanceInformationMatcher.find()) {
                String remittanceInformation = remittanceInformationMatcher.group(1).replace("\n", "").trim();
                if (remittanceInformation.length() < 100) {
                    content.put("remittanceInformation", remittanceInformation);
                } else {
                    invalid.put("70", "Remittance information is too long.");
                }
            }

            // 71A - Charges
            Matcher chargesMatcher = Pattern.compile(":71A:(.*?)(?=:\\d{2}[A-Z]?:|$)", Pattern.DOTALL).matcher(textBlock);
            if (chargesMatcher.find()) {
                String charges = chargesMatcher.group(1);
                switch (charges) {
                    case "BEN", "OUR", "SHA":
                        content.put("charges", charges);
                        break;
                
                    default:
                        invalid.put("71A", "Invalid value of charges.");
                        break;
                }
            } else {
                invalid.put("71A", "Charges not found.");
            }

        } else {
            invalid.put("4", "Text block not found.");
        }
        // End: Content

        // Start: Response
        Map<String, Object> response = new LinkedHashMap<>();

        if (invalid.size() > 0) {
            response.put("status", "invalid");
            response.put("invalid", invalid); 
            return response;
        }

        response.put("status", "success");
        response.put("content", content);
        return response;
        // End: Response
    }

    public String getBic(String bicWithLt) {
        if (bicWithLt == null || bicWithLt.length() < 11) {
            return bicWithLt; // fallback
        }
        // Ambil 8 karakter pertama (BIC8) + 3 terakhir (branch)
        return bicWithLt.substring(0, 8) + bicWithLt.substring(9, 12);
    }
    

}
