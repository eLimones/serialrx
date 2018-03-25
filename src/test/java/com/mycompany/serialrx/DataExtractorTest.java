/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serialrx;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author manuel
 */
public class DataExtractorTest {
    
    public DataExtractorTest() {
    }

    @Test
    public void testSomeMethod() {
        String validInput = "$P,889.06,T,62.25,H,138.91,*64";
        DataExtractor dataExtractor = new DataExtractor();
        
        SondeData data = dataExtractor.extract(validInput);
        
        assertTrue("invalid data", data.isValid);
        assertEquals("pressure not correct", 889.06, data.pressure, 0.01);
        assertEquals("temperature not correct", 62.25, data.temperature, 0.01);
        assertEquals("humidity not correct", 138.91, data.humidity, 0.01);
    }
    
    @Test
    public void parsesNegativeNumbers() {
        String validInput = "$P,-889.06,T,-62.25,H,-138.91,*64";
        DataExtractor dataExtractor = new DataExtractor();
        
        SondeData data = dataExtractor.extract(validInput);
        
        assertTrue("invalid data", data.isValid);
        assertEquals("pressure not correct", -889.06, data.pressure, 0.01);
        assertEquals("temperature not correct", -62.25, data.temperature, 0.01);
        assertEquals("humidity not correct", -138.91, data.humidity, 0.01);
    }
    
    @Test
    public void setDataAsInvalidWhenIncompleteString() {
        String validInput = "889.06,T,-62.25,H,-138.91,*64";
        DataExtractor dataExtractor = new DataExtractor();
        
        SondeData data = dataExtractor.extract(validInput);
        
        assertFalse("invalid data", data.isValid);
    }
    
}
