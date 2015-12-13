/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ozonetel.kookoo;

import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arun Kumar
 */
public class SpeechTestClient {
    
    public static void main(String[] args){
        try {
            GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyCj7lkmUFWn6K5Rjb6jI_YLzON8zRU_azo");
            duplex.setLanguage("en-us");
            duplex.addResponseListener(new GSpeechResponseListener() {
                
                @Override
                public void onResponse(GoogleResponse gr) {
                    System.out.println(gr.getResponse());
                }
            });
            
            duplex.recognize(new File("D:\\speech\\recordFile1.flac"), 8000);
        } catch (IOException ex) {
            Logger.getLogger(SpeechTestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
