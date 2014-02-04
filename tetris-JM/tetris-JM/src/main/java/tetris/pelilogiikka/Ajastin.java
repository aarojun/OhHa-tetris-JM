package tetris.pelilogiikka;

import java.util.Timer;

public class Ajastin {
    private double odotettavaAika;
    private boolean loppunut;
    private boolean paalla;
    
    public Ajastin(double odotettavaAika) {
        this.odotettavaAika = odotettavaAika;
        loppunut = false;
        paalla = false;
    }
    
    public void kaynnista() {
        kulutaAikaa();
        paalla = true;
    }
    
    private void kulutaAikaa() {
        try {
            Thread.sleep((int)(odotettavaAika*1000));
            loppunut = true;
        } catch (InterruptedException ex) {
            
        }
    }
    
    public void resetoiAjastin() {
        loppunut = false;
        kulutaAikaa();
    }
    
    public boolean loppunut() {
        return loppunut;
    }
    
    public boolean onkoPaalla() {
        return paalla;
    }
    
    public void setOdotettavaAika(int aika) {
        this.odotettavaAika = aika;
    }
}
