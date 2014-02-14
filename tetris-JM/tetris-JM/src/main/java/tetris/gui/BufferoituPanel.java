package tetris.gui;

import java.awt.Graphics;
import javax.swing.JPanel;

abstract class BufferoituPanel extends JPanel implements Paivitettava {
    abstract void uudelleenPiirra();
    
    abstract void render(Graphics g);
}
