package tetris.pelilogiikka;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.EventQueue;
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
        if (!peli.onkoPaalla()) {
            peli.resetoiAjastin();
        } else {
            if (paused) {
                toteutaOhjausPaused();
            } else {
                toteutaOhjaus();
                tarkistaAjastimet();

                peli.paivitaFysiikka();
                paivitaAikarajat();
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        gui.paivita();
                    }
                });
                frame++;
            }

        }
    }

    private void kasvataAjastimia() {
        if (peli.painovoimaPaalla()) {
            painovoimaFrame++;
        } else {
            painovoimaFrame = 0;
        }
        if (peli.liukuAikaPaalla()) {
            liukuFrame++;
        } else {
            liukuFrame = 0;
        }
    }

    private void tarkistaAjastimet() {
        if (peli.painovoimaPaalla()) {
            painovoimaFrame++;
        } else {
            painovoimaFrame = 0;
        }

        if (peli.liukuAikaPaalla()) {
            liukuFrame++;
        } else {
            liukuFrame = 0;
            if (painovoimaFrame >= painovoimaPaivitys) {
                peli.pudotaAlas();
                painovoimaFrame = 0;
            }
        }

        if (liukuFrame >= liukuAika) {
            peli.lukitsePalikkaJosAlusta();
            liukuFrame = 0;
        }
    }

    private void paivitaAikarajat() {
        painovoimaPaivitys = peli.getAikayksikko();
        liukuAika = peli.getAikayksikko() * 2;
    }

    private void toteutaOhjaus() {
//            if (painike == p) {
//                paused = true;
//                return;
//            } else if (painike == z) {
//                peli.kaannaVasen();
//            } else if (painike == x) {
//                peli.kaannaOikea();
//            }
//            if (suunta == alas) {
//                if(peli.liukuAikaPaalla()) {
//                    peli.lukitsePalikka();
//                } else { 
//                    peli.pudotaAlas();
//                }            
//                painovoimaFrame = 0;
//                liukuFrame = 0;
//            } else if (suunta == ylos) {
//                peli.pudotaJaLukitsePalikka();
//                painovoimaFrame = 0;
//                liukuFrame = 0;
//            } else if (suunta == vasen) {
//                peli.liikutaVasen();
//            } else if (suunta == oikea) {
//                peli.liikutaOikea();
//            }
    }

    private void toteutaOhjausPaused() {
//            if (painike == esc) {
//                peli.quit();
//            } else if (painike == p) {
//                paused = false;
//            }
    }
}
