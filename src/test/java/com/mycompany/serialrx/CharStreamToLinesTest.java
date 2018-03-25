/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serialrx;

import io.reactivex.Flowable;
import org.junit.Test;

/**
 *
 * @author manuel
 */
public class CharStreamToLinesTest {

    @Test
    public void linesAreEmitted() {
        Flowable<Character> observable = Flowable.fromArray('a','b','\n','c','d','\n');
        observable
                .flatMap(new CharStreamToLines())
                .test()
                .assertResult("ab","cd");
    }
    
    @Test
    public void emptyLinesCanBeEmitted() {
        Flowable<Character> observable = Flowable.fromArray('\n','c','d','\n','e');
        observable
                .flatMap(new CharStreamToLines())
                .test()
                .assertResult("","cd");
    }
    
    @Test
    public void trailingCharactersAreNotEmitted() {
        Flowable<Character> observable = Flowable.fromArray('a','b','\n','c','d','\n','e');
        observable
                .flatMap(new CharStreamToLines())
                .test()
                .assertResult("ab","cd");
    }
    
}
