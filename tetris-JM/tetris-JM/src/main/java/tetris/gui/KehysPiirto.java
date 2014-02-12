package tetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import tetris.pelilogiikka.Peli;

public class KehysPiirto extends JPanel implements Paivitettava {
    private int nelionKoko;
    private Peli peli;
    private Font menuFont = new Font(Font.SANS_SERIF, 0, 20);
    
    public KehysPiirto(Peli peli, int nelionKoko) {
        this.setOpaque(false);
        this.nelionKoko = nelionKoko;
        this.peli = peli;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.BLACK);

        g.fillRect(0, 0, 3 * nelionKoko, 4 * nelionKoko);
        g.fillRect(9*nelionKoko, 0, 2 * nelionKoko, 4 * nelionKoko);
        g.fillRect(0, 3*nelionKoko, 10 * nelionKoko, 1 * nelionKoko);
        
        g.setColor(Color.WHITE);
        g.drawRect(nelionKoko,4*nelionKoko,10*nelionKoko,20*nelionKoko);
        g.drawRect(3*nelionKoko,1*nelionKoko,6*nelionKoko,2*nelionKoko);
        
        g.setColor(Color.ORANGE);
        
        g.setFont(menuFont);
        g.drawString("NEXT", 12 * nelionKoko, 3 * nelionKoko);
        
        int keskileveys = (int)(2.5*nelionKoko);
        
        if (peli.getStartMenu()) {
            g.drawString("SELECT GAME MODE", keskileveys, 11 * nelionKoko);
            g.drawString("Y: Normal    N: Death", keskileveys, 12 * nelionKoko);
        } else if (peli.getPaused()) {
            g.drawString("PAUSE", 5 * nelionKoko, 12 * nelionKoko);
        }

        if (peli.getGameover()) {
            g.drawString("GAME OVER", 4 * nelionKoko, 11 * nelionKoko);
            g.drawString("restart?  Y/N", 4 * nelionKoko, 12 * nelionKoko);
        }
    }

    @Override
    public void paivita() {
        super.repaint();
    }
    
}
