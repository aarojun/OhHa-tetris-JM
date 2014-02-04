package tetris.pelilogiikka;

import java.util.Timer;
import java.util.TimerTask;
import tetris.objects.KaantyvaPalikka;

public class Painovoima {

    private Timer timer;
    private boolean paalla;

    public Painovoima(Peli peli) {
        this.paalla = peli.painovoimaPaalla();
        this.timer = new Timer();
        timer.schedule(new RemindTask(peli.getNykyinenPalikka(), peli),
                0,
                (long) (peli.getAikayksikko() * 1000));
    }

    class RemindTask extends TimerTask {

        private KaantyvaPalikka nykyinenPalikka;
        private Peli peli;

        public RemindTask(KaantyvaPalikka palikka, Peli peli) {
            this.nykyinenPalikka = palikka;
            this.peli = peli;
        }

        @Override
        public void run() {
            if (!paalla) {
                timer.cancel();
            } else {
                peli.getTormaysLogiikka().yritaPudottaaLiikutettavaaPalikkaa(nykyinenPalikka);
            }
        }
    }
}
