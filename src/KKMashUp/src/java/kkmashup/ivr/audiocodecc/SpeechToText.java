
package kkmashup.ivr.audiocodecc;

import com.ozonetel.kookoo.TestClient;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaFlacEncoder.FLAC_FileEncoder;
import javax.servlet.ServletContext;
import javazoom.jl.converter.Converter;

/**
 *
 * @author Arun Kumar
 */
public class SpeechToText {

    private String speechText;

    private final long MAX_WAITING_TIME_IN_SEC = 20;

    private boolean IsResponseReceived;

    public String getAuddioText(String audioUrl, ServletContext context) {
        RecognizerChunked chunked = new RecognizerChunked("AIzaSyCj7lkmUFWn6K5Rjb6jI_YLzON8zRU_azo", "en-in");

        try {
            IsResponseReceived = false;
            chunked.addResponseListener(new GSpeechResponseListener() {

                @Override
                public void onResponse(GoogleResponse gr) {

                    if (gr != null) {
                        speechText = gr.getResponse();
                    }
                    IsResponseReceived = true;
                }
            });
            chunked.setContentType("audio/x-flac");
            String temFileName = context.getRealPath("audio/") + getAudioFileName();
            URL audioFile = new URL(audioUrl);
            ReadableByteChannel rbc = Channels.newChannel(audioFile.openStream());
            FileOutputStream fos = new FileOutputStream(temFileName + ".mp3");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            Converter convert = new Converter();
            convert.convert(temFileName + ".mp3", temFileName + ".wav");

            FLAC_FileEncoder flacEncoder = new FLAC_FileEncoder();

            File inputFile = new File(temFileName + ".wav");
            File outputFile = new File(temFileName + ".flac");

            flacEncoder.encode(inputFile, outputFile);

            chunked.getRecognizedDataForFlac(temFileName + ".flac", 8000);

            long timeout = MAX_WAITING_TIME_IN_SEC;
            while (timeout > 0 && !IsResponseReceived) {
                Thread.sleep(1000);
                timeout--;
            }

            /*  File file = new File(temFileName + ".mp3");
             file.delete();
             file.deleteOnExit();
             File file1 = new File(temFileName + ".wav");
             file1.deleteOnExit();*/
        } catch (Exception ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return speechText ;

    }

    public static String getAudioFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        return sdf.format(new Date()).toUpperCase(Locale.getDefault());
    }
}
