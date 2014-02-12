package tetris.pelilogiikka;

import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * pelin inputtien toteuttaja. vastaanottaa keybindingien tuottamia komentoja kayttoliittymasta.
 * liike-toimintoihin liittyvat ajastimet ja pyoritettava koodi sailytetaan omassa luokassaan
 * toiminnat joihin pohjassa painaminen ei vaikuta toteutetaan ilman Liike -oliota.
 * 
 * @param msPF millisekuntteja per pelin paivitys
*/
public class LiikeToiminnat implements SyotteenLukija {
    private Liike vasen;
    private Liike oikea;
    private Liike alas;
    private Liike ylos;
    private Liike vasKaannos;
    private Liike oikKaannos;
    private Timer autorepeatFix;
    
    private final int FPS = 60;
    private final long msPF = 1000/FPS;
    
    private TetrisPeli peli;

    
    /**
     * Alustaa liikkeille niiden toiminnat. 
     * liikkeiden konstruktori: 
     * (pyoritettava koodi(runnable), pohjassa painettuna toteutettavan toiston viive, viite toistokorjaus -ajastimeen)
     * @param peli peli jota liikkeet komentavat
     */
    public LiikeToiminnat(final TetrisPeli peli) {
        this.peli = peli;
        this.autorepeatFix = new Timer();


        // luodaan liikkeet. konstruktori: (pyoritettava koodi, 
        // pohjassa painettuna toteutettavan toiston viive, viite toistokorjaus -ajastimeen)
        this.vasen = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.vasemmalle();
                }
            }, 2*msPF, 200, autorepeatFix);
        
        this.oikea = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.oikealle();
                }
            }, 2*msPF, 200, autorepeatFix);
        
        this.alas = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.alas();
                }
            }, 1*msPF, 200, autorepeatFix);
        
        this.ylos = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.pudotaKokonaan();
                }
            }, 200, 340, autorepeatFix);
        
        this.vasKaannos = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.kaannaVasen();
                }
            }, 140, 300, autorepeatFix);
        
        this.oikKaannos = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.kaannaOikea();
                }
            }, 140, 300, autorepeatFix);
        
        this.vasen.setPoisSuljettava(oikea);
        this.oikea.setPoisSuljettava(vasen);
        this.alas.setPoisSuljettava(ylos);
        this.ylos.setPoisSuljettava(alas);
        this.vasKaannos.setPoisSuljettava(oikKaannos);
        this.oikKaannos.setPoisSuljettava(vasKaannos);        
    }

    private void autorepeatFix(TimerTask timertask) {
        this.autorepeatFix = new Timer();
        autorepeatFix.schedule(timertask, 4);
    }

    public void vasenPush(ActionEvent e) {
        vasen.push();
    }

    public void vasenRelease(ActionEvent e) {
        vasen.release();
    }

    public void oikeaPush(ActionEvent e) {
        oikea.push();
    }

    public void oikeaRelease(ActionEvent e) {
        oikea.release();
    }

    public void alasPush(ActionEvent e) {
        alas.push();
    }

    public void alasRelease(ActionEvent e) {
        alas.release();
    }

    public void ylosPush(ActionEvent e) {
        ylos.push();
    }

    public void ylosRelease(ActionEvent e) {
        ylos.release();
    }

    public void vasenKaannosPush(ActionEvent e) {
        vasKaannos.push();
    }

    public void vasenKaannosRelease(ActionEvent e) {
        vasKaannos.release();
    }

    public void oikeaKaannosPush(ActionEvent e) {
        oikKaannos.push();
    }

    public void oikeaKaannosRelease(ActionEvent ae) {
        oikKaannos.release();
    }

    public void pausePush(ActionEvent ae) {
        peli.pause();
    }

    public void yesPush(ActionEvent ae) {
        peli.yes();
    }

    public void noPush(ActionEvent ae) {
        peli.no();
    }
}
