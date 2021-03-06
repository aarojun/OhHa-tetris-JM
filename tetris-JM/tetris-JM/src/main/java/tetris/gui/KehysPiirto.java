package tetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import tetris.pelilogiikka.PeliRajapinta;

/**
 * Kayttoliittyman graafinen taso, piirtaa pelin ymparille piirrettavat kehykset.
 * Kehyksiin kuuluvat myos menu-tekstit.
 * @author zaarock
 */

public class KehysPiirto extends BufferoituPanel {
    private int nelionKoko;
    private PeliRajapinta peli;
    private Font menuFont = new Font(Font.SANS_SERIF, 0, 22);
    private Font menuFont2 = new Font(Font.SANS_SERIF,2,28);
    
    /**
     * Alustaa piirtotasolle viitteet piirrettaviin elementteihin ja piirtotyokaluun.
     * @param peli piirrettava peli
     * @param nelionKoko palikan pisteen sivun pituus pikseleina, yleinen kokoyksikko
     */
    public KehysPiirto(PeliRajapinta peli, int nelionKoko) {
        this.setIgnoreRepaint(true);
        this.setOpaque(false);
        this.nelionKoko = nelionKoko;
        this.peli = peli;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.translate(-nelionKoko, -nelionKoko);
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
        
        int keskileveys = (int)(2.2*nelionKoko);
        
        if (peli.getStartMenu()) {
            g.drawString("SELECT GAME MODE", keskileveys, 12 * nelionKoko);
            g.drawString("Y: Normal    N: Death", keskileveys, 13 * nelionKoko);
        } else if (peli.getPaused()) {
            g.drawString("PAUSE", 5 * nelionKoko, 12 * nelionKoko);
        }

        if (peli.getGameover()) {
            g.setFont(menuFont2);
            if(peli.getTimeOver()) {
                g.drawString("TIME OVER", keskileveys+nelionKoko, 13* nelionKoko);
            } else {
                g.drawString("GAME OVER", keskileveys+nelionKoko, 13 * nelionKoko);
            }
            g.setFont(menuFont);
            g.drawString("restart?  Y/N", 4 * nelionKoko, 14 * nelionKoko);
        }
    }
    
    public void render(Graphics g) {
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
        
        int keskileveys = (int)(2.2*nelionKoko);
        
        if (peli.getStartMenu()) {
            g.drawString("SELECT GAME MODE", keskileveys, 12 * nelionKoko);
            g.drawString("Y: Normal    N: Death", keskileveys, 13 * nelionKoko);
        } else if (peli.getPaused()) {
            g.drawString("PAUSE", 5 * nelionKoko, 12 * nelionKoko);
        }

        if (peli.getGameover()) {
            g.setFont(menuFont2);
            if(peli.getTimeOver()) {
                g.drawString("TIME OVER", keskileveys+nelionKoko, 13* nelionKoko);
            } else {
                g.drawString("GAME OVER", keskileveys+nelionKoko, 13 * nelionKoko);
            }
            g.setFont(menuFont);
            g.drawString("restart?  Y/N", 4 * nelionKoko, 14 * nelionKoko);
        }
    }

    @Override
    public void paivita() {
        super.repaint();
    }

    @Override
    void uudelleenPiirra() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
