/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serialrx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author manuel
 */
public class DataExtractor {

    private final Pattern pattern;

    public DataExtractor() {
        String numberPattern = "(-?\\d+(?:\\.\\d+)?)";
        pattern = Pattern.compile("^\\$P," + numberPattern + ",T," + numberPattern + ",H," + numberPattern + ",\\*(\\d\\d)$");
    }
    

    SondeData extract(String validInput) {   
        Matcher matcher = pattern.matcher(validInput);
        
        int checksum;
        boolean isValid = false;
        
        if (matcher.find()) {
            checksum = Integer.parseInt(matcher.group(4));
            isValid = (checksum == 64);
        }
        
        if(isValid){
            double pressure =   Double.parseDouble(matcher.group(1));
            double temperature =   Double.parseDouble(matcher.group(2));
            double humidity =   Double.parseDouble(matcher.group(3));
            return new SondeData(pressure, temperature, humidity);
        } else {
            return new SondeData();
        }  
    }

}
