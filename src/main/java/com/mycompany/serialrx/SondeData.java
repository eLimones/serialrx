/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serialrx;

/**
 *
 * @author manuel
 */
class SondeData {

    double pressure;
    double temperature;
    double humidity;
    boolean isValid;

    public SondeData(double pressure, double temperature, double humidity) {
        this.isValid = true;
        this.pressure = pressure;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public SondeData() {
        this.isValid = false;
    }

}
