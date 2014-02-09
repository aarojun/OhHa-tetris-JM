package tetris.pelilogiikka;

import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

// pelin inputtien lukija. keybindingit lahettaa komennon SyotteenLukija -rajapinnassa maariteltyyn liikkeeseen.

public class LiikeToiminnat implements SyotteenLukija {

    // liike-toiminnat ja niihin liittyvat ajastimet sailytetaan omassa luokassaan
    // toiminnat joihin pohjassa painaminen ei vaikuta toteutetaan ilman Liike -oliota.
    private Liike vasen;
    private Liike oikea;
    private Liike alas;
    private Liike ylos;
    private Liike vasKaannos;
    private Liike oikKaannos;
    private Timer autorepeatFix;
    
    private Peli peli;

    public LiikeToiminnat(final Peli peli) {
        this.peli = peli;
        this.autorepeatFix = new Timer();


        // luodaan liikkeet. konstruktori: (pyoritettava koodi, 
        // pohjassa painettuna toteutettavan toiston viive, viite toistokorjaus -ajastimeen)
        this.vasen = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.vasemmalle();
                }
            }, 140, autorepeatFix);
        
        this.oikea = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.oikealle();
                }
            }, 140, autorepeatFix);
        
        this.alas = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.alas();
                }
            }, 140, autorepeatFix);
        
        this.ylos = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.pudotaKokonaan();
                }
            }, 200, autorepeatFix);
        
        this.vasKaannos = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.kaannaVasen();
                }
            }, 180, autorepeatFix);
        
        this.oikKaannos = new Liike(new Runnable() {
                @Override
                public void run() {
                        peli.kaannaOikea();
                }
            }, 180, autorepeatFix);
        
        this.vasen.setPoisSuljettava(oikea);
        this.oikea.setPoisSuljettava(vasen);
        this.alas.setPoisSuljettava(ylos);
        this.ylos.setPoisSuljettava(alas);
        this.vasKaannos.setPoisSuljettava(oikKaannos);
        this.oikKaannos.setPoisSuljettava(vasKaannos);        
    }

    public void autorepeatFix(TimerTask timertask) {
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
