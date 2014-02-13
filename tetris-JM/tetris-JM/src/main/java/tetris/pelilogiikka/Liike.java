package tetris.pelilogiikka;

import java.util.Timer;
import java.util.TimerTask;

public class Liike {

    private Timer toisto;
    private Timer autorepeatFix;
    private boolean paalla;
    private boolean release;
    //liikkeen automaattisten toistojen valinen viive millisekuntteina
    private long toistoAika;
    //viive ennen ensimmaista automaattista toistoa
    private long alkuViive;
    // poisSuljettavan toisto laitetaan pois paalta jos nappi painetaan
    // eli esim vasenta ja oikeaa ei voi toistaa samaan aikaan
    private Liike poisSuljettava;
    // liikkeen toteuttama toiminta, esim peli.vasemmalle()
    private Runnable toiminta;

    public Liike(Runnable toiminta, long toistoAika, long alkuViive, Timer autorepeatFix) {
        this.toisto = new Timer();
        this.toiminta = toiminta;
        this.toistoAika = toistoAika;
        this.alkuViive = alkuViive;
        this.autorepeatFix = autorepeatFix;
    }
    
    public void setPoisSuljettava(Liike liike) {
        this.poisSuljettava = liike;
    }

    public void peruToisto() {
        this.toisto.cancel();
    }

    public void poisPaalta() {
        this.paalla = false;
    }
    
    /** Toteutetaan kun nappia on painettu.
     * 
     */
    public void push() {
        release = false;
        if (!paalla) {
            paalla = true;
            this.poisSuljettava.peruToisto();
            toisto = new Timer();
            toiminta.run();
            toisto.scheduleAtFixedRate(
                    new TimerTaskAnnetullaSyotteella(toiminta), alkuViive, toistoAika);
        }
        Thread.yield();
    }
    
    /** toteuttaa 6ms tarkistusajan napin irtipaastolle. jos nappia painetaan heti releasen jalkeen release perutaan.
    * tama on toteutettu koska mm. ubuntu lahettaa pressed ja release komentoja samaan aikaan
    * automaattisessa nappaimien toistossaan. emme halua etta tama vaikuttaa ohjelmaan
    */
    public void release() {
        release = true;
        autorepeatFix(new TimerTask() {
            @Override
            public void run() {
                tarkistaRelease();
            }
        });
    }

    public void autorepeatFix(TimerTask timertask) {
        autorepeatFix.schedule(timertask, 6);
    }
    
    public void tarkistaRelease() {
        if (release) {
            toisto.cancel();
            paalla = false;
        }
    }
}
