/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serialrx;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.reactivestreams.Publisher;

/**
 *
 * @author manuel
 */
public class CharStreamToLinesTest {

    @Test
    public void testSomeMethod() {
        Flowable<Character> observable = Flowable.fromArray('a','b','\n','c','d','\n','e');
        observable
                .flatMap(new Function<Character, Publisher<String>>() {
            private StringBuffer buffer = new StringBuffer(1024);
            @Override
            public Publisher<String> apply(Character data) throws Exception {
                if(data == '\n'){
                    String line = buffer.toString();
                    buffer.setLength(0);
                    return Flowable.just(line);
                }else{
                    buffer.append(data);
                    return Flowable.empty();
                }
            }
        }, 1)
                .test()
                .assertResult("ab","cd");
    }
    
}
