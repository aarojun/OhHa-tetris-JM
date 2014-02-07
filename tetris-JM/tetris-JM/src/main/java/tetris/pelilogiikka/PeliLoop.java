package tetris.pelilogiikka;

import java.util.TimerTask;
import tetris.gui.Paivitettava;

public class PeliLoop extends TimerTask {

    private Peli peli;
    private Paivitettava gui;
    private int frame;
    private int painovoimaFrame;
    private int painovoimaPaivitys;
    private int liukuFrame;
    private int liukuAika;
    private int liikkumisFrame;
    private int liikkumisAika;
    private boolean paused;

    public PeliLoop(Peli peli, Paivitettava paivitettava) {
        this.peli = peli;
        this.gui = paivitettava;
        this.frame = 0;
        this.painovoimaFrame = 0;
        this.painovoimaPaivitys = peli.getAikayksikko();
        this.liukuFrame = 0;
        this.liukuAika = peli.getAikayksikko() * 2;
        this.paused = false;
    }

    @Override
    public void run() {
        if (peli.onkoPaalla()) {
            if (peli.getPaused()) {
                peli.paivita();
            } else {
                tarkistaAjastimet();

                peli.paivita();
                paivitaAikarajat();
            }

        }
    }

    private void tarkistaAjastimet() {
        painovoimaFrame++;

        // liukuAika on paalla jos palikalla alusta
        if (peli.liukuAikaPaalla()) {
            liukuFrame++;
        } else {
            liukuFrame = 0;
            if (painovoimaFrame >= painovoimaPaivitys) {
                peli.pudotaPalikkaa();
                painovoimaFrame = 0;
            }
        }

        // palikka lukitaan kun aika loppunut
        if (liukuFrame >= liukuAika) {
            peli.lukitsePalikkaJosAlusta();
            liukuFrame = 0;
        }
    }

    // nopeuttaa painovoimaa kun vaikeustaso etenee
    private void paivitaAikarajat() {
        painovoimaPaivitys = peli.getAikayksikko();
    }

    public void nollaaAjastimet() {
        this.liukuFrame = 0;
        this.painovoimaFrame = 0;
    }

    public boolean getPaused() {
        return this.paused;
    }

    public int getFrame() {
        return this.frame;
    }
}
