/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serialrx;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author manuel
 */
public class App {
    static SimpleSerialPort port;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
        // TODO code application logic here
        System.out.println("Hello world");
        String[] ports = SimpleSerialPort.getPorts();
        System.out.println("port:" + ports[0]);
        port = new SimpleSerialPort(ports[0]);
        port.subscribe((data) -> {
            System.out.print(data);
        });
        
        Thread thread = new Thread(() -> {
            while (true) {
                Thread.yield();
            }
        });
        thread.start();
    }
    
}
