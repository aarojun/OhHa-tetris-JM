package tetris.pelilogiikka;

import tetris.gui.Paivitettava;

/**
 * Pelien kayttama paivitys-loop. Lahettaa pelille ja kayttoliittymalle paivityskaskyja 60 kertaa sekunnissa.
 * Sisaltaa interpolaatio-toiminnallisuuden mutta siita ei vaikuta olevan hyotya tetris-pelissa.
 * Luokka sisaltaa pelin kayttamat fysiikka-ajastimet.
 * 
 * @author zaarock
 */
public class PeliLoop {

    private long viimeLoopAika = System.nanoTime();
    final int TARGET_FPS = 60;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    private int lastFpsTime;
    private int fps;
    private TetrisPeli peli;
    private Paivitettava gui;
    private boolean paalla;
    private double painovoimaFrame;
    private int painovoimaPaivitys;
    private double liukuFrame;
    private int liukuAika;
    private double fxFrame;
    private int fxAika;

    /**
     * Alustaa peliloopin ja pelin aikayksikot.
     * @param peli
     */
    public PeliLoop(TetrisPeli peli) {

        this.peli = peli;
        this.paalla = peli.onkoPaalla();

        this.liukuFrame = 0;
        this.painovoimaPaivitys = peli.getAikayksikko();
        this.liukuAika = 34;

        this.fxFrame = 0;
        this.fxAika = 12;

    }
    
    /**
     * Asettaa loopille graafisen kayttoliittyman jota paivittaa.
     * @param gui graafinen kayttoliittyma
     */
    public void setPaivitettava(Paivitettava gui) {
        this.gui = gui;
    }

    public void run() {
        while (paalla) {
            long now = System.nanoTime();
            long updateLength = now - viimeLoopAika;
            viimeLoopAika = now;
            int delta = 1;

            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1000000000) {
                lastFpsTime = 0;
                fps = 0;
            }

            paivita(delta);

            gui.paivita();

            try {
                Thread.sleep((viimeLoopAika - System.nanoTime() + OPTIMAL_TIME) / 1000000);
            } catch (Exception ex) {
            }
        }
    }
    
    /**
     * Run -metodin versio joka toteuttaa interpolaation (fysiikka paivitetaan suhteessa paivitysnopeuteen jotta pysyy tasaana).
     */
    public void runInterp() {
        while (paalla) {
            long now = System.nanoTime();
            long updateLength = now - viimeLoopAika;
            viimeLoopAika = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);

            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1000000000) {
                lastFpsTime = 0;
                fps = 0;
            }

            paivita(delta);

            gui.paivita();

            try {
                Thread.sleep((viimeLoopAika - System.nanoTime() + OPTIMAL_TIME) / 1000000);
            } catch (Exception ex) {
            }
        }
    }

    private void paivita(double delta) {
        peli.paivita();
        if (!peli.getPaused() && !peli.getGameover()) {
            tarkistaAjastimet(delta);
            paivitaAikarajat();
        }
    }

    /**
     * Tarkistaa pelin ajastimet ja lahettaa pelille paivityksia jos ajastimet saavuttaa maksiminsa.
     * @param delta yksikko jolla nostaa ajastimia.
     */
    private void tarkistaAjastimet(double delta) {
        painovoimaFrame += delta;
        liukuFrame += delta;

        if (liukuFrame >= liukuAika) {
            peli.lukitse();
            painovoimaFrame = 0;
            liukuFrame = 0;
        } else if (painovoimaFrame >= painovoimaPaivitys) {
            peli.pudota();
            painovoimaFrame = 0;
        }

        if (peli.onkoVaihtoAika()) {
            fxFrame += delta;
            if (fxFrame >= fxAika) {
                peli.seuraavaVuoro();
                fxFrame = 0;
            }
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
