package tetris.pelilogiikka;

import tetris.gui.Paivitettava;

public class PeliLoop {

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
    private double fxFrame;
    private int fxAika;

    public PeliLoop(Peli peli, Paivitettava gui) {
        
        this.peli = peli;
        this.paalla = peli.onkoPaalla();
        this.gui = gui;
        
        this.liukuFrame = 0;
        this.painovoimaPaivitys = peli.getAikayksikko();
        this.liukuAika = 35;
        
        this.fxFrame = 0;
        this.fxAika = 12;
        
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
        
        if (peli.onkoEfektitPaalla()) {
                fxFrame+=delta;
                if(fxFrame>= fxAika) {
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
