package tetris.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;

/**
 * Luokka joka sisaltaa grafiikanpiirto -metodeja jotka ovat monien JPanelien kaytossa.
 * @author zaarock
 */
public class PiirtoTyokalu {
    private int sivu;
    private int sisennys;
    private Color varjo = new Color(0,0,0,130);

    public PiirtoTyokalu(int nelionKoko) {
        this.sivu = nelionKoko;
        this.sisennys = nelionKoko/5;
    }
    
    public void piirraPalikat(Graphics g, ArrayList<Palikka> palikat) {
        for (Palikka pl : palikat) {
            piirraPalikka(g, pl);
        }
    }
    
    public void piirraSeuraavatPalikat(Graphics g, ArrayList<KaantyvaPalikka> palikat) {
        for (int i = 1; i<palikat.size() ; i++) {
            piirraPalikka(g, palikat.get(i));
        }
    }
    
    /**
     * Piirtaa palikka-objektin siihen liitetylla varilla.
     * @param g grafiikka -ilmentyma johon piirretaan
     * @param pl palikka joka piirretaan
     */
    public void piirraPalikka(Graphics g, Palikka pl) {
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
            g.fillRect(pisteXPos * sivu, pisteYPos * sivu, sivu, sivu);
            g.setColor(vari2);
            g.fillRect(pisteXPos * sivu + sisennys, pisteYPos * sivu + sisennys, sivu - sisennys*2, sivu - sisennys*2);

        }
        piirraVierusLiitokset(g,muoto,xPos,yPos);
    }
    
    public void piirraVarjopalikka(Graphics g, Palikka pl) {
        Color vari = pl.getVari().getColor();
        Color vari2 = vari.darker();
        int xPos = pl.getXpos();
        int yPos = pl.getYpos();
        ArrayList<int[]> muoto = pl.getMuoto();
        for (int i = 0; i < muoto.size(); i++) {
            int[] piste = muoto.get(i);
            g.setColor(vari);
            int pisteXPos = xPos + piste[0];
            int pisteYPos = yPos + piste[1];
            g.setColor(varjo);
            g.fillRect(pisteXPos * sivu, pisteYPos * sivu, sivu, sivu);
        }
        g.setColor(vari2);
        piirraVierusLiitokset(g,muoto,xPos,yPos);
    }
    
    public void piirraVierusLiitokset(Graphics g, ArrayList<int[]> muoto,int x,int y) {
        for(int i = 0; i< muoto.size(); i++) {
            int[] piste = muoto.get(i);
            int pisteXPos = x + piste[0];
            int pisteYPos = y + piste[1];
            if (vierusPiste(muoto, i, 1, 0)) {
                g.fillRect(pisteXPos * sivu + sisennys, pisteYPos * sivu + sisennys, sivu*2 - sisennys*2, sivu - sisennys*2);
            }
            if (vierusPiste(muoto, i, -1, 0)) {
                g.fillRect((pisteXPos-1) * sivu, pisteYPos * sivu + sisennys, sivu*2 - sisennys*2, sivu - sisennys*2);
            }
            if (vierusPiste(muoto, i, 0, 1)) {
                g.fillRect(pisteXPos * sivu + sisennys, pisteYPos * sivu + sisennys, sivu - sisennys*2, sivu*2 - sisennys*2);
            }
            if (vierusPiste(muoto, i, 0, -1)) {
                g.fillRect(pisteXPos * sivu + sisennys, (pisteYPos-1) * sivu, sivu - sisennys*2, sivu - sisennys*2);
            }
        }
    } 
    
    /**
     * Tarkistaa onko pisteen lahella toinen piste annetussa suunnassa. (tata algoritmia voisi viela parannella..)
     * @param muoto joukko pisteita.
     * @param pisteenIndeksi tarkasteltavan pisteen indeksi.
     * @param xMuutos x-koordinaatin muutos josta tarkistetaan onko vieruspiste olemassa.
     * @param yMuutos y-koordinaatin muutos josta tarkistetaan onko vieruspiste olemassa.
     * @return
     */
    public boolean vierusPiste(ArrayList<int[]> muoto, int pisteenIndeksi, int xMuutos, int yMuutos) {
        int[] piste1 = muoto.get(pisteenIndeksi);
        if(pisteenIndeksi == muoto.size()-1) {
            return false;
        }
        for (int i = pisteenIndeksi+1; i < muoto.size(); i++) {
            if (i > pisteenIndeksi) {
                int[] piste2 = muoto.get(i);
                if (piste1[0] + xMuutos == piste2[0] && piste1[1] + yMuutos == piste2[1]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
}
