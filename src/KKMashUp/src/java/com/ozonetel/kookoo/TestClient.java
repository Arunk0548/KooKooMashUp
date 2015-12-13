package com.ozonetel.kookoo;

import com.darkprograms.speech.recognizer.FlacEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaFlacEncoder.FLACFileWriter;
import javaFlacEncoder.FLAC_FileEncoder;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.Decoder;
import kkmashup.ivr.audiocodecc.GSpeechResponseListener;
import kkmashup.ivr.audiocodecc.GoogleResponse;
import kkmashup.ivr.audiocodecc.RecognizerChunked;
import kkmashup.ivr.audiocodecc.SpeechToText;
import kkmashup.ivr.demo.OutboundSms;
import org.apache.catalina.ha.tcp.SendMessageData;

public class TestClient {

    public static void main(String[] args) {
        try {
            Response resp = new Response();
            resp.sendSms("Hi KooKoo", "9490607378");
            resp.sayAs(Response.SayAs.DIGITS, "1234");
            System.out.println("" + resp.getXML());
            
            String message = "abcdefgh";
            String submessage = message.substring(3, 4);
            
            int index = 161/150;
            System.out.println(" " +index);
            
    
            /*
            
             String fileName = SpeechToText.getAudioFileName();
            
             RecognizerChunked chunked = new RecognizerChunked("AIzaSyCj7lkmUFWn6K5Rjb6jI_YLzON8zRU_azo", "en-us");
             chunked.addResponseListener(new GSpeechResponseListener() {

             @Override
             public void onResponse(GoogleResponse gr) {

             System.out.println("Response " + gr.getResponse());
             }
             });
             chunked.setContentType("audio/l16");

             URL audioFile = new URL("http://recordings.kookoo.in/arun0548/recordFile1.mp3");
             ReadableByteChannel rbc = Channels.newChannel(audioFile.openStream());
             FileOutputStream fos = new FileOutputStream("recordFile1.mp3");
             fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
             fos.close();
             Converter convert = new Converter();
             convert.convert("recordFile1.mp3", "recordFile1.wav");
             chunked.getRecognizedDataForFlac("recordFile1.wav", 8000);
            
             File file = new File("recordFile1.mp3");file.delete();
             file.deleteOnExit();
             File file1 = new File("recordFile1.wav");
             file1.deleteOnExit();
             */
            Converter convert = new Converter();
            convert.convert("D:\\speech\\recordFile1.mp3", "D:\\speech\\recordFile1.wav");
                        
            FLAC_FileEncoder flacEncoder = new FLAC_FileEncoder();

            File inputFile = new File("D:\\speech\\recordFile1.wav");
            File outputFile = new File("D:\\speech\\recordFile1.flac");

            flacEncoder.encode(inputFile, outputFile);
            System.out.println("Done");

            //convert.convert("D:\\speech\\recordFile1.wav", "D:\\speech\\recordFile1.flac");
//            ConvertWavToFlac encoder = new ConvertWavToFlac();
//            encoder.convertWaveToFlac(new File("D:\\speech\\recordFile1.wav"),
            //             new File("D:\\speech\\recordFile1.flac"));
        } catch (Exception ex) {
            System.out.println("Exception");
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
