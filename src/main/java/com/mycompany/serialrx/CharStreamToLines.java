/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serialrx;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;

/**
 *
 * @author manuel
 */
public class CharStreamToLines implements Function<Character, Publisher<String>> {

    private final StringBuffer buffer;

    public CharStreamToLines() {
        this.buffer = new StringBuffer(1024);
    }

    @Override
    public Publisher<String> apply(Character data) throws Exception {
        if (data == '\n') {
            String line = buffer.toString();
            buffer.setLength(0);
            return Flowable.just(line);
        } else {
            buffer.append(data);
            return Flowable.empty();
        }
    }
}
