package tetris.pelilogiikka;

import java.awt.event.ActionEvent;
import java.util.Timer;
import tetris.objects.Tetromino;

/**
 * pelin inputtien toteuttaja. vastaanottaa keybindingien tuottamia komentoja
 * kayttoliittymasta. liike-toimintoihin liittyvat ajastimet ja pyoritettava
 * koodi sailytetaan omassa luokassaan Liike. 
 * Toiminnat joihin pohjassa painaminen ei vaikuta toteutetaan ilman Liike -oliota.
 * peli-luokka lahettaa luokalle kaskyja toteuttaa ohjaus paivitysten yhteydessa.
 *
 * @param msPF millisekuntteja per pelin paivitys
 */
public class LiikeToiminnat implements SyotteenLukija {

    private Liike vasenL;
    private Liike oikeaL;
    private Liike alasL;
    private Liike ylosL;
    private Liike vasKaannosL;
    private Liike oikKaannosL;
    private Timer autorepeatFix;
    private final int FPS = 60;
    private final long msPF = 1000 / FPS;
    private TetrisPeli peli;
    private boolean alas;
    private boolean vasen;
    private boolean oikea;
    private boolean pudotaKokonaan;
    private boolean kaannaVasen;
    private boolean kaannaOikea;

    /**
     * Alustaa liikkeille niiden toiminnat. liikkeiden konstruktori:
     * (pyoritettava koodi(runnable), pohjassa painettuna toteutettavan toiston
     * viive, viite toistokorjaus -ajastimeen)
     *
     * @param peli peli jota liikkeet komentavat
     */
    public LiikeToiminnat(final TetrisPeli peli) {
        this.peli = peli;
        this.autorepeatFix = new Timer();

        this.vasenL = new Liike(new Runnable() {
            @Override
            public void run() {
                vasen = true;
            }
        }, 2 * msPF, 200, autorepeatFix);

        this.oikeaL = new Liike(new Runnable() {
            @Override
            public void run() {
                oikea = true;
            }
        }, 2 * msPF, 200, autorepeatFix);

        this.alasL = new Liike(new Runnable() {
            @Override
            public void run() {
                alas = true;
            }
        }, 1 * msPF, 200, autorepeatFix);

        this.ylosL = new Liike(new Runnable() {
            @Override
            public void run() {
                pudotaKokonaan = true;
            }
        }, 200, 340, autorepeatFix);

        this.vasKaannosL = new Liike(new Runnable() {
            @Override
            public void run() {
                kaannaVasen = true;
            }
        }, 140, 300, autorepeatFix);

        this.oikKaannosL = new Liike(new Runnable() {
            @Override
            public void run() {
                kaannaOikea = true;
            }
        }, 140, 300, autorepeatFix);

        this.vasenL.setPoisSuljettava(oikeaL);
        this.oikeaL.setPoisSuljettava(vasenL);
        this.alasL.setPoisSuljettava(ylosL);
        this.ylosL.setPoisSuljettava(alasL);
        this.vasKaannosL.setPoisSuljettava(oikKaannosL);
        this.oikKaannosL.setPoisSuljettava(vasKaannosL);
        
        nollaaOhjaimet();
    }

    /**
     * Nollaa luokan ohjaintiedot tyhjiksi.
     */
    public void nollaaOhjaimet() {
        alas = false;
        vasen = false;
        oikea = false;
        pudotaKokonaan = false;
        kaannaVasen = false;
        kaannaOikea = false;
    }

    /**
     * Toteuttaa pelin ohjauksen normaalitilassa. 
     * Huom: pudotuskomennot nostavat
     * frekvenssi-mittaria eli antavat pistebonuksia.
     */
    public void toteutaOhjaus() {
            if (kaannaVasen) {
                kaannaVasen = false;
                peli.kaannaVasen();
            } else if (kaannaOikea) {
                kaannaOikea = false;
                peli.kaannaOikea();
            }
            if (pudotaKokonaan) {
                pudotaKokonaan = false;
                peli.pudotaJaLukitsePalikka();
            } else {
                if (alas) {
                    alas = false;
                    peli.liikutaAlas();
                }
                if (vasen) {
                    vasen = false;
                    peli.liikutaVasen();
                } else if (oikea) {
                    oikea = false;
                    peli.liikutaOikea();
                }
            }
    }
    
    /**
     * Toteuttaa pelin ohjauksen vuorojen valisena aikana.
     */
    public void toteutaOhjausVaihtoaika() {
        if (kaannaVasen) {
            kaannaVasen = true;
            kaannaOikea = false;
        } else if (kaannaOikea) {
            kaannaOikea = true;
            kaannaVasen = false;
        }
        if (vasen) {
            vasen = true;
            oikea = false;
        } else if (oikea) {
            oikea = true;
            vasen = false;
        }
    }
    
    /**
     * Toteuttaa bufferoidun ohjauksen seuraavalle palikalle ennen kuin se
     * siirretaan peliin.
     */
    public void seuraavaPalikkaOhjaus() {
        if (peli.getNykyinenPalikka().getTetromino() != Tetromino.O) {
            if (kaannaVasen) {
                kaannaVasen = false;
                peli.kaannaVasen();
            } else if (kaannaOikea) {
                kaannaOikea = false;
                peli.kaannaOikea();
            }
        }
        if (vasen) {
            peli.liikutaVasen();
            vasen = false;
        } else if (oikea) {
            peli.liikutaOikea();
            oikea = false;
        }

    }

    public void vasenPush(ActionEvent e) {
        vasenL.push();
    }

    public void vasenRelease(ActionEvent e) {
        vasenL.release();
    }

    public void oikeaPush(ActionEvent e) {
        oikeaL.push();
    }

    public void oikeaRelease(ActionEvent e) {
        oikeaL.release();
    }

    public void alasPush(ActionEvent e) {
        alasL.push();
    }

    public void alasRelease(ActionEvent e) {
        alasL.release();
    }

    public void ylosPush(ActionEvent e) {
        ylosL.push();
    }

    public void ylosRelease(ActionEvent e) {
        ylosL.release();
    }

    public void vasenKaannosPush(ActionEvent e) {
        vasKaannosL.push();
    }

    public void vasenKaannosRelease(ActionEvent e) {
        vasKaannosL.release();
    }

    public void oikeaKaannosPush(ActionEvent e) {
        oikKaannosL.push();
    }

    public void oikeaKaannosRelease(ActionEvent ae) {
        oikKaannosL.release();
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
