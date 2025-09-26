package com.messageconverter.kfmi.services;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class CcyCheckerService {

    public static boolean isValid(String ccy) {
        try {
            InputStream xmlStream = CcyCheckerService.class.getResourceAsStream("/static/iso/iso4217.xml");
            if (xmlStream == null) {
                throw new RuntimeException("iso4217.xml not found in resources/static/iso/");
            }
    
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlStream);
            doc.getDocumentElement().normalize();
    
            NodeList entries = doc.getElementsByTagName("CcyNtry");
    
            for (int i = 0; i < entries.getLength(); i++) {
                Node node = entries.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
    
                    Node ccyNode = element.getElementsByTagName("Ccy").item(0);
                    if (ccyNode == null) continue; // skip kalau gak ada <Ccy>
    
                    String xmlCcy = ccyNode.getTextContent().trim(); // <<< penting: trim()
                    if (ccy.equalsIgnoreCase(xmlCcy)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }    
}
