package tetris.pelilogiikka;

import java.util.TimerTask;

// Ajastimille syotettava TimerTask - olio jolle voi konstruktorissa maarittaa pyoritettavan komennon
// hyodyllinen koska TimerTask-olio taytyy aina luoda uudestaan jos se ajastetaan
// nain voimme toteuttaa viitteena olevaa komentoa timertaskilla

public class TimerTaskAnnetullaSyotteella extends TimerTask {
    protected Runnable komento;
    
    public TimerTaskAnnetullaSyotteella(Runnable komento) {
        this.komento = komento;
    }

    @Override
    public void run() {
        komento.run();
    }
   
}
