package tetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import tetris.objects.Palikka;
import tetris.pelilogiikka.Peli;

public class LiikkuvienPiirto extends JPanel implements Paivitettava {
    private Peli peli;
    private int nelionKoko;
    private int varjotusEro;
    private Color varjo = new Color(0,0,0,130);
    private Font pisteFont = new Font(Font.SANS_SERIF, 1, 16);
    private Font menuFont = new Font(Font.SANS_SERIF, 0, 20);
    
    public LiikkuvienPiirto(Peli peli, int nelionKoko) {
        this.setOpaque(false);
        this.peli = peli;
        this.nelionKoko = nelionKoko;
        this.varjotusEro = nelionKoko/5;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.translate(0,-2*nelionKoko);
        
        piirraVarjopalikka(g);
        piirraPalikka(g,peli.getNykyinenPalikka());
        
        piirraMittarit(g);
    }
    
    private void piirraPalikat(Graphics g, ArrayList<Palikka> palikat) {
        for (Palikka pl : palikat) {
            piirraPalikka(g, pl);
        }
    }

    private void piirraPalikka(Graphics g, Palikka pl) {
        piirraPalikka(g,pl,pl.getVari().getColor());
    }
    
    private void piirraPalikka(Graphics g, Palikka pl, Color vari) {
        Color vari2 = vari.darker().darker();
        int xPos = pl.getXpos();
        int yPos = pl.getYpos();
        ArrayList<int[]> muoto = pl.getMuoto();
        for (int i = 0; i < muoto.size(); i++) {
            int[] piste = muoto.get(i);
            g.setColor(vari);
            int pisteXPos = xPos + piste[0];
            int pisteYPos = yPos + piste[1];
            g.fillRect(pisteXPos * nelionKoko, pisteYPos * nelionKoko, nelionKoko, nelionKoko);
            g.setColor(vari2);
            g.fillRect(pisteXPos * nelionKoko + varjotusEro, pisteYPos * nelionKoko + varjotusEro, nelionKoko - varjotusEro*2, nelionKoko - varjotusEro*2);

            if (vierusPiste(muoto, i, 1, 0)) {
                g.fillRect(pisteXPos * nelionKoko + varjotusEro, pisteYPos * nelionKoko + varjotusEro, nelionKoko - varjotusEro, nelionKoko - varjotusEro*2);
            }
            if (vierusPiste(muoto, i, -1, 0)) {
                g.fillRect(pisteXPos * nelionKoko, pisteYPos * nelionKoko + varjotusEro, nelionKoko - varjotusEro*2, nelionKoko - varjotusEro*2);
            }
            if (vierusPiste(muoto, i, 0, 1)) {
                g.fillRect(pisteXPos * nelionKoko + varjotusEro, pisteYPos * nelionKoko + varjotusEro, nelionKoko - varjotusEro*2, nelionKoko - varjotusEro);
            }
            if (vierusPiste(muoto, i, 0, -1)) {
                g.fillRect(pisteXPos * nelionKoko + varjotusEro, pisteYPos * nelionKoko, nelionKoko - varjotusEro*2, nelionKoko - varjotusEro*2);
            }
        }
    }
    
    private void piirraVarjopalikka(Graphics g) {
        Palikka pl = peli.getVarjopalikka();

        Color vari = pl.getVari().getColor();
        Color vari2 = vari.darker().darker();
        int xPos = pl.getXpos();
        int yPos = pl.getYpos();
        ArrayList<int[]> muoto = pl.getMuoto();
        for (int i = 0; i < muoto.size(); i++) {
            int[] piste = muoto.get(i);
            g.setColor(vari);
            int pisteXPos = xPos + piste[0];
            int pisteYPos = yPos + piste[1];
            g.fillRect(pisteXPos * nelionKoko, pisteYPos * nelionKoko, nelionKoko, nelionKoko);
            g.setColor(vari2);
            g.fillRect(pisteXPos * nelionKoko + varjotusEro, pisteYPos * nelionKoko + varjotusEro, nelionKoko - varjotusEro*2, nelionKoko - varjotusEro*2);

            if (vierusPiste(muoto, i, 1, 0)) {
                g.fillRect(pisteXPos * nelionKoko + varjotusEro, pisteYPos * nelionKoko + varjotusEro, nelionKoko - varjotusEro, nelionKoko - varjotusEro*2);
            }
            if (vierusPiste(muoto, i, -1, 0)) {
                g.fillRect(pisteXPos * nelionKoko, pisteYPos * nelionKoko + varjotusEro, nelionKoko - varjotusEro*2, nelionKoko - varjotusEro*2);
            }
            if (vierusPiste(muoto, i, 0, 1)) {
                g.fillRect(pisteXPos * nelionKoko + varjotusEro, pisteYPos * nelionKoko + varjotusEro, nelionKoko - varjotusEro*2, nelionKoko - varjotusEro);
            }
            if (vierusPiste(muoto, i, 0, -1)) {
                g.fillRect(pisteXPos * nelionKoko + varjotusEro, pisteYPos * nelionKoko, nelionKoko - varjotusEro*2, nelionKoko - varjotusEro*2);
            }
            g.setColor(varjo);
            g.fillRect((xPos + piste[0]) * nelionKoko, (yPos + piste[1]) * nelionKoko, nelionKoko, nelionKoko);
        }
    }
    
    private boolean vierusPiste(ArrayList<int[]> muoto, int pisteenIndeksi, int xMuutos, int yMuutos) {
        int[] piste1 = muoto.get(pisteenIndeksi);
        for (int i = 0; i < muoto.size(); i++) {
            if (i != pisteenIndeksi) {
                int[] piste2 = muoto.get(i);
                if (piste1[0] + xMuutos == piste2[0] && piste1[1] + yMuutos == piste2[1]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void piirraMittarit(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.drawLine(10 * nelionKoko, 0, 20 * nelionKoko, 0);
        g.drawLine(10 * nelionKoko, 9 * nelionKoko - 6, 20 * nelionKoko, 9 * nelionKoko - 6);

        g.setFont(pisteFont);

        g.setColor(Color.LIGHT_GRAY);
        int leveys = (int) (10.5 * nelionKoko);
        g.drawString("NEXT", 12 * nelionKoko - 10, 1 * nelionKoko);

        g.drawString("SCORE " + Integer.toString(peli.getPisteet()), leveys, 10 * nelionKoko);
        g.drawString("LEVEL " + Integer.toString(peli.getVaikeustaso()), leveys, 11 * nelionKoko);
        g.drawString("PIECE " + peli.getVuoro(), leveys, 14 * nelionKoko);
        g.drawString("TIME " + peli.getAika(), leveys, 12 * nelionKoko);
        
        
        g.drawString("HI-SCORE " + Integer.toString(peli.getHighScore()), leveys, 16 * nelionKoko);
    }

    @Override
    public void paivita() {
        super.repaint();
    }
    
}
