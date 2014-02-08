package tetris.pelilogiikka;

import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class LiikeToiminnat implements SyotteenLukija {

    // paalla-merkinnat ja toisto-ajastimet liikkeille jotka toistuvat automaattisesti
    private boolean vasenPaalla;
    private boolean oikeaPaalla;
    private boolean alasPaalla;
    private boolean ylosPaalla;
    private boolean vasKaannosPaalla;
    private boolean oikKaannosPaalla;
    private Timer vasenToisto;
    private Timer oikeaToisto;
    private Timer alasToisto;
    private Timer ylosToisto;
    private Timer vasKaannosToisto;
    private Timer oikKaannosToisto;
    private Peli peli;

    public LiikeToiminnat(Peli peli) {
        vasenPaalla = false;
        oikeaPaalla = false;
        alasPaalla = false;
        ylosPaalla = false;
        vasKaannosPaalla = false;
        oikKaannosPaalla = false;
        this.peli = peli;
    }

    public void vasenPush(ActionEvent e) {
        if (!vasenPaalla) {
            vasenPaalla = true;
            vasenToisto = new Timer();
            vasenToisto.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                peli.vasemmalle();
            }
            },
                    0, 140);
    }
    }
    
    public void vasenRelease(ActionEvent e) {
        vasenToisto.cancel();
        vasenPaalla = false;
    }

    public void oikeaPush(ActionEvent e) {
        if (!oikeaPaalla) {
            oikeaPaalla = true;
            oikeaToisto = new Timer();
            oikeaToisto.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                peli.oikealle();
            }
            },
                    0, 140);
    }
    }
    
    public void oikeaRelease(ActionEvent e) {
        oikeaToisto.cancel();
        oikeaPaalla = false;
    }

    public void alasPush(ActionEvent e) {
        if(!alasPaalla) {
            alasPaalla = true;
            alasToisto = new Timer();
            alasToisto.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                peli.alas();
            }
            },
                    0, 140);
        }
    }
    
    public void alasRelease(ActionEvent e) {
        alasToisto.cancel();
        alasPaalla = false;
    }

    public void ylosPush(ActionEvent e) {
        if(!ylosPaalla) {
            ylosPaalla = true;
            ylosToisto = new Timer();
            ylosToisto.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                peli.pudotaKokonaan();
            }
            },
                    0, 200);
        }
    }
    
    public void ylosRelease(ActionEvent e) {
        ylosToisto.cancel();
        ylosPaalla = false;
    }

    public void vasenKaannosPush(ActionEvent e) {
        if(!vasKaannosPaalla) {
            vasKaannosPaalla = true;
            vasKaannosToisto = new Timer();
            vasKaannosToisto.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                peli.kaannaVasen();
            }
            },
                    0, 160);
        }
    }
    
    public void vasenKaannosRelease(ActionEvent e) {
        vasKaannosToisto.cancel();
        vasKaannosPaalla = false;
    }

    public void oikeaKaannosPush(ActionEvent e) {
        if(!oikKaannosPaalla) {
            oikKaannosPaalla = true;
            oikKaannosToisto = new Timer();
            oikKaannosToisto.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                peli.kaannaOikea();
            }
            },
                    0, 160);
        }
    }
    
    public void oikeaKaannosRelease(ActionEvent ae) {
        oikKaannosToisto.cancel();
        oikKaannosPaalla = false;
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
