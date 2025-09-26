package com.messageconverter.kfmi.services;

public record GetDataFrom50() {
    public static String extractName(String block) {
        String[] lines = block.split("\n");

        if (lines.length > 1) {
            return lines[1].trim();
        }
        return "";
    }
    public static String extractLastLine(String block) {
        String[] lines = block.split("\n");
        if (lines.length > 0) {
            String lastLine = lines[lines.length - 1].trim();
            if (lastLine.length() > 4) {
                return lastLine.substring(4);
            } else {
                return "";
            }
        }
        return "";
    }    
}
