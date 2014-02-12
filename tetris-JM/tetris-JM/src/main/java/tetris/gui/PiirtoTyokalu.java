package tetris.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;


// luokka sisaltaa monia grafiikanpiirto -metodit jotka ovat monen layerin / JPanelin kaytossa.

public class PiirtoTyokalu {
    private int nelionKoko;
    private int varjotusEro;
    private Color varjo = new Color(0,0,0,130);

    public PiirtoTyokalu(int nelionKoko) {
        this.nelionKoko = nelionKoko;
        this.varjotusEro = nelionKoko/5;
    }
    
    public void piirraPalikat(Graphics g, ArrayList<Palikka> palikat) {
        for (Palikka pl : palikat) {
            piirraPalikka(g, pl);
        }
    }
    
    public void piirraKaantyvatPalikat(Graphics g, ArrayList<KaantyvaPalikka> palikat) {
        for (int i = 1; i<palikat.size() ; i++) {
            piirraPalikka(g, palikat.get(i));
        }
    }
    
    public void piirraPalikka(Graphics g, Palikka pl) {
        piirraPalikka(g,pl,pl.getVari().getColor());
    }
    
    public void piirraPalikka(Graphics g, Palikka pl, Color vari) {
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
    
    public void piirraVarjopalikka(Graphics g, Palikka pl) {
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
    
    public boolean vierusPiste(ArrayList<int[]> muoto, int pisteenIndeksi, int xMuutos, int yMuutos) {
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
    
    
}
