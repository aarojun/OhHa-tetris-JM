/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.pelilogiikka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * lukee ja kirjoittaa dataa pelin high-score listaan
 *
 * @author zaarock
 */
public class HighscoreKasittelija {

    private final String FILE_PATH = "./hiscore.dat";
    private final Charset ENCODING = StandardCharsets.UTF_8;
    private File tiedosto;

    public HighscoreKasittelija() {
        tiedosto = new File("./hiscore.dat");        
    }

    public int lataaHighscore() throws IOException {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(tiedosto));
            try {
                int score = (int) inputStream.readObject();
                return score;
            } catch (ClassNotFoundException ex) {
                System.out.println("high score data not found");
            }
        } catch (IOException ex) {
            System.out.println("high score data not found");
        }
        return 0;
    }

    public void tallennaHighscore(int score) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(tiedosto));
            outputStream.writeObject(score);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
