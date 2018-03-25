/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serialrx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

public class App {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static SimpleSerialPort port;
    public static Disposable subscription;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Original thread" + Thread.currentThread().getName());

        Flowable<Character> myObservable = Flowable.create((emitter) -> {
            System.out.println("Subscribing thread" + Thread.currentThread().getName());
            String[] ports = SimpleSerialPort.getPorts();
            System.out.println("port:" + ports[0]);
            port = new SimpleSerialPort(ports[0]);
            port.setOnDataReceivedListener((Character data) -> {
                emitter.onNext(data);
            });
            System.out.println("Registered");
        }, BackpressureStrategy.BUFFER);

        ArrayList<Character> arrayList = new ArrayList<>(1024);

        Disposable subscription = myObservable
                .subscribeOn(Schedulers.single())
                .flatMap(new CharStreamToLines())
                .map(new DataExtractor()::extract)
                .filter((probeData)-> {return probeData.isValid;})
                .subscribe((probeData) -> {
                    System.out.println(
                            "valid: " + probeData.isValid 
                            + " P:" + probeData.pressure
                            + " T:" + probeData.temperature
                            + " H:" + probeData.humidity
                    );
                }, (e) -> {
                    System.out.println("Error:");
                    e.printStackTrace();
                });

        while (true) {
            Thread.yield();
        }
    }
}
