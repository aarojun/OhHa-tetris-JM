package tetris.pelilogiikka;

import java.util.logging.Level;
import java.util.logging.Logger;
import tetris.gui.Paivitettava;

public class PeliLoop2 {

    private long viimeLoopAika = System.nanoTime();
    final int TARGET_FPS = 60;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    
    private int lastFpsTime;
    private int fps;
    private Peli peli;
    private Paivitettava gui;
    private boolean paalla;
    private double painovoimaFrame;
    private int painovoimaPaivitys;
    private double liukuFrame;
    private int liukuAika;

    public PeliLoop2(Peli peli, Paivitettava gui) {
        
        this.peli = peli;
        this.paalla = peli.onkoPaalla();
        this.gui = gui;
        
        this.painovoimaFrame = 0;
        this.liukuFrame = 0;
        this.painovoimaPaivitys = peli.getAikayksikko();
        this.liukuAika = peli.getAikayksikko()*2;
    }


    public void run() {
        while (paalla) {
            long now = System.nanoTime();
            long updateLength = now - viimeLoopAika;
            viimeLoopAika = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);
            
            lastFpsTime += updateLength;
            fps++;
            
            if(lastFpsTime >= 1000000000) {
                lastFpsTime = 0;
                fps = 0;
            }
            
            paivita(delta);

            gui.paivita();
            
            try {
                    Thread.sleep( (viimeLoopAika-System.nanoTime() + OPTIMAL_TIME)/1000000 );
                } catch (InterruptedException ex) {
                    Logger.getLogger(PeliLoop2.class.getName()).log(Level.SEVERE, null, ex);
                }
}
    }
    

    private void paivita(double delta) {
        if (peli.getPaused()) {
            peli.paivita();
        } else {
            tarkistaAjastimet(delta);

            peli.paivita();
            paivitaAikarajat();
        }
    }

    private void tarkistaAjastimet(double delta) {
        painovoimaFrame += delta;

        // liukuAika on paalla jos palikalla alusta
        if (peli.liukuAikaPaalla()) {
            liukuFrame += delta;
        } else {
            liukuFrame = 0;
            if (painovoimaFrame >= painovoimaPaivitys) {
                peli.pudota();
                painovoimaFrame = 0;
            }
        }

        // palikka lukitaan kun aika loppunut
        if (liukuFrame >= liukuAika) {
            peli.lukitse();
            painovoimaFrame = 0;
            liukuFrame = 0;
        }
    }

    private void paivitaAikarajat() {
        painovoimaPaivitys = peli.getAikayksikko();
    }

    public void nollaaAjastimet() {
        this.liukuFrame = 0;
        this.painovoimaFrame = 0;
    }

    private float getTickCount() {
        return System.nanoTime();
    }
}
