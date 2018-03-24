/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serialrx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */

    public static SimpleSerialPort port;
    public static Disposable subscription;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Original thread"  + Thread.currentThread().getName());
        
        
        Flowable<Character> stringObservable = Flowable.create((emitter) -> {
            System.out.println("Subscribing thread"  + Thread.currentThread().getName());
            String[] ports = SimpleSerialPort.getPorts();
            System.out.println("port:" + ports[0]);
            port = new SimpleSerialPort(ports[0]);
            port.setOnDataReceivedListener((Character data) -> {
                emitter.onNext(data);
            });
            System.out.println("Registered");
        }, BackpressureStrategy.BUFFER);
        
        
        Disposable subscription = stringObservable.subscribeOn(Schedulers.single()).subscribe((t) -> {
            System.out.print(t);
        }, (e)->{
            System.out.println("Error:");
            e.printStackTrace();
        });

        while (true) {
            Thread.yield();
        }
    }
}
