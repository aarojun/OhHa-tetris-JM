package tetris.pelilogiikka;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Nappaimistonkuuntelija implements KeyListener {

    private Peli peli;

    public Nappaimistonkuuntelija(Peli peli) {
        this.peli = peli;
    }
    
    public void setPeli(Peli peli) {
        this.peli = peli;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        peli.setPaivitaOhjaus(true);
        if (ke.getKeyCode() == KeyEvent.VK_P) {
            peli.setPainike(Painike.P);
        } else if (ke.getKeyCode() == KeyEvent.VK_Z) {
            peli.setPainike(Painike.Z);
        } else if (ke.getKeyCode() == KeyEvent.VK_X) {
            peli.setPainike(Painike.X);
        } else if (ke.getKeyCode() == KeyEvent.VK_Y) {
            peli.setPainike(Painike.Y);
        } else if (ke.getKeyCode() == KeyEvent.VK_N) {
            peli.setPainike(Painike.N);
        }
        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            peli.setSuunta(Suunta.ALAS);
        } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
            peli.setSuunta(Suunta.YLOS);
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            peli.setSuunta(Suunta.VASEN);
        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            peli.setSuunta(Suunta.OIKEA);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
}
