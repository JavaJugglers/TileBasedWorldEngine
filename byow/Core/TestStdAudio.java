package byow.Core;

import edu.princeton.cs.algs4.StdAudio;
import java.io.Serializable;
import java.io.*;


public class TestStdAudio implements Serializable {
    public static void playAudio(){
        /*
        File audioFile = new File ("sound.txt");
        audioFile.createNewFile();

         */
        while (true) {
            StdAudio.play("Never Gonna Give You Up Original.wav");
            StdAudio.close();
        }

        /*
        double concertA = 440.0;
        for (int i = 0; i < StdAudio.SAMPLE_RATE; i++) {
            double sample = 0.5 * Math.sin(2*Math.PI * concertA * i / StdAudio.SAMPLE_RATE);
            StdAudio.play(sample);
        }
        StdAudio.close();

         */


    }
    public static void main(String[] args) {

    }
}
