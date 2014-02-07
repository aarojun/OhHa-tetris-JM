package tetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class TaustaPiirto extends JPanel implements Paivitettava {
    private int nelionKoko;
    
    public TaustaPiirto(int nelionSivunPituus) {
        this.nelionKoko = nelionSivunPituus;
        this.setBackground(Color.BLACK);
//        this.setOpaque(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 10 * nelionKoko, 20 * nelionKoko);
        
        g.drawLine(10*nelionKoko, 1*nelionKoko+16, 20*nelionKoko, 1*nelionKoko+16);
        g.drawLine(10*nelionKoko, 11*nelionKoko-6, 20*nelionKoko, 11*nelionKoko-6);
        
        Font font = new Font(Font.SANS_SERIF,1,16);
        g.setFont(font);
  
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("NEXT", 12 * nelionKoko-10, 1 * nelionKoko);
        
       
    }
    
    @Override
    public void paivita() {
        super.repaint();
    }
}
