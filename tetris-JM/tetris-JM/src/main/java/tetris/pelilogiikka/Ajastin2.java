package tetris.pelilogiikka;

import java.util.Timer;
import java.util.TimerTask;

public class Ajastin2 extends TimerTask {
    private boolean loppunut;
    private Timer timer;
    
    public Ajastin2(Timer timer) {
        this.loppunut = false;
        this.timer = timer;
    }

    @Override
    public void run() {
        loppunut = true;
        timer.cancel();
    }
    
    public void reset() {
        loppunut = false;
        timer.cancel();
        run();
    }
    
    public boolean loppunut() {
        return loppunut;
    }
    
}
