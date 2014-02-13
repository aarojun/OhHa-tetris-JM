package tetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import tetris.pelilogiikka.PeliRajapinta;

public class LiikkuvienPiirto extends JPanel implements Paivitettava {
    private PeliRajapinta peli;
    private int nelionKoko;
    private int varjotusEro;
    private Color varjo = new Color(0,0,0,130);
    private Font pisteFont = new Font(Font.SANS_SERIF, 1, 16);
    private Font menuFont = new Font(Font.SANS_SERIF, 0, 20);
    private DecimalFormat nf = new DecimalFormat("0.0");
    private PiirtoTyokalu tyokalu;
    
    public LiikkuvienPiirto(PeliRajapinta peli, int nelionKoko, PiirtoTyokalu piirtoKalu) {
        this.setOpaque(false);
        this.peli = peli;
        this.nelionKoko = nelionKoko;
        this.varjotusEro = nelionKoko/5;
        this.tyokalu = piirtoKalu;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.translate(0,-2*nelionKoko);
        
        tyokalu.piirraVarjopalikka(g,peli.getVarjopalikka());
        tyokalu.piirraPalikka(g,peli.getNykyinenPalikka());
        
        piirraMittarit(g);
    }
    
    private void piirraMittarit(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.drawLine(10 * nelionKoko, 0, 20 * nelionKoko, 0);
        g.drawLine(10 * nelionKoko, 9 * nelionKoko - 6, 20 * nelionKoko, 9 * nelionKoko - 6);

        g.setFont(pisteFont);

        g.setColor(Color.LIGHT_GRAY);
        int leveys = (int) (10.5 * nelionKoko);
        g.drawString("NEXT", 12 * nelionKoko - 10, 1 * nelionKoko);

        int level = peli.getVaikeustaso();
        int combo = peli.getCombo();
        if(level<1000) {
        g.drawString("LEVEL " + Integer.toString(level), leveys, 11 * nelionKoko);
        } else {
            g.drawString("LEVEL", leveys, 11 * nelionKoko);
            g.setColor(Color.ORANGE);
            g.drawString("MAX",leveys+2*nelionKoko,11*nelionKoko);
            g.setColor(Color.LIGHT_GRAY);
        }
        g.drawString("PIECE " + peli.getVuoro(), leveys, 16 * nelionKoko);
        g.drawString("TIME " + peli.getAika(), leveys, 12 * nelionKoko);
        g.drawString("COMBO " + combo, leveys, 15 * nelionKoko);
         
        g.drawString("HI-SCORE: " + Integer.toString(peli.getHighScore()), leveys, 22 * nelionKoko);
        
        
        g.setFont(menuFont);
        g.setColor(Color.ORANGE);
        g.drawString("Score : " + Integer.toString(peli.getPisteet()), leveys, 10 * nelionKoko);
        g.drawString("Multiplier : " + nf.format((peli.getFrekvenssi()/30)*(combo+1)*(1+(int)(level/100))), leveys, 14*nelionKoko);
        
    }

    @Override
    public void paivita() {
        super.repaint();
    }
    
}
