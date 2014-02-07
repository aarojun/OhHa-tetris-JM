package tetris.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import tetris.pelilogiikka.Peli;

public class Nappaimistonkuuntelija implements KeyListener {

    private Peli peli;

    public Nappaimistonkuuntelija(Peli peli) {
        this.peli = peli;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_P) {
            // paused = true;
//            return;
        } else if (ke.getKeyCode() == KeyEvent.VK_Z) {
            peli.kaannaVasen();
        } else if (ke.getKeyCode() == KeyEvent.VK_X) {
            peli.kaannaOikea();
        }
        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            if (peli.liukuAikaPaalla()) {
                peli.lukitsePalikka();
            } else {
                peli.pudotaAlas();
            }
            peli.nollaaAjastimet();
        } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
            peli.pudotaJaLukitsePalikka();
            peli.nollaaAjastimet();
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            peli.liikutaVasen();
        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            peli.liikutaOikea();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
}
