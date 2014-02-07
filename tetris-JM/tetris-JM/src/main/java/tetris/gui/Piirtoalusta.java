package tetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.pelilogiikka.Peli;

public class Piirtoalusta extends JPanel implements Paivitettava {

    private Peli peli;
    private int nelionKoko;
    private Color varjo;
    private Font pisteFont;
    private Font menuFont;

    public Piirtoalusta(Peli peli, int nelionSivunPituus) {
        this.peli = peli;
        this.nelionKoko = nelionSivunPituus;
        
        this.pisteFont = new Font(Font.SANS_SERIF,1,16);
        this.menuFont = new Font(Font.SANS_SERIF,0,20);
        this.varjo = new Color(255,255,255, 90);
        this.setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 10 * nelionKoko, 20 * nelionKoko);
        
        piirraVarjopalikka(g);
        piirraKaantPalikat(g, peli.getSeuraavatPalikat());
        piirraPalikat(g, peli.getPelilauta().getPalikat());

        piirraMittarit(g);
        
        piirraOhjausNaytto(g);

    }
    
    private void piirraKaantPalikat(Graphics g, ArrayList<KaantyvaPalikka> palikat) {
        for (Palikka pl : palikat) {
            piirraPalikka(g, pl);
        }
    }
    
    private void piirraPalikat(Graphics g, ArrayList<Palikka> palikat) {
        for (Palikka pl : palikat) {
            piirraPalikka(g, pl);
        }
    }

    private void piirraPalikka(Graphics g, Palikka pl) {
        Color vari = pl.getVari().getColor();
        Color vari2 = vari.darker().darker();
        int xPos = pl.getXpos();
        int yPos = pl.getYpos();
        ArrayList<int[]> muoto = pl.getMuoto();
        for (int i = 0; i < muoto.size(); i++) {
            int[] piste = muoto.get(i);
            g.setColor(vari);
            g.fillRect((xPos + piste[0]) * nelionKoko, (yPos + piste[1]) * nelionKoko, nelionKoko, nelionKoko);
            g.setColor(vari2);
            g.fillRect((xPos + piste[0]) * nelionKoko + 6, (yPos + piste[1]) * nelionKoko + 6, nelionKoko - 12, nelionKoko - 12);

            if (vierusPiste(muoto, i, 1, 0)) {
                g.fillRect((xPos + piste[0]) * nelionKoko + 6, (yPos + piste[1]) * nelionKoko + 6, nelionKoko - 6, nelionKoko - 12);
            }
            if (vierusPiste(muoto, i, -1, 0)) {
                g.fillRect((xPos + piste[0]) * nelionKoko, (yPos + piste[1]) * nelionKoko + 6, nelionKoko - 12, nelionKoko - 12);
            }
            if (vierusPiste(muoto, i, 0, 1)) {
                g.fillRect((xPos + piste[0]) * nelionKoko + 6, (yPos + piste[1]) * nelionKoko + 6, nelionKoko - 12, nelionKoko - 6);
            }
            if (vierusPiste(muoto, i, 0, -1)) {
                g.fillRect((xPos + piste[0]) * nelionKoko + 6, (yPos + piste[1]) * nelionKoko, nelionKoko - 12, nelionKoko - 12);
            }
        }
    }
    
    private void piirraVarjopalikka(Graphics g) {
        Palikka varjopalikka = peli.getVarjopalikka();
        g.setColor(varjo);
        int vXPos = varjopalikka.getXpos();
        int vYPos = varjopalikka.getYpos();
        ArrayList<int[]> varjoMuoto = varjopalikka.getMuoto();
        for (int i = 0; i < varjoMuoto.size(); i++) {
            int[] piste = varjoMuoto.get(i);
            g.fillRect((vXPos + piste[0]) * nelionKoko, (vYPos + piste[1]) * nelionKoko, nelionKoko, nelionKoko);
        }
    }

    private void piirraMittarit(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.drawLine(10*nelionKoko, 1*nelionKoko+16, 20*nelionKoko, 1*nelionKoko+16);
        g.drawLine(10*nelionKoko, 11*nelionKoko-6, 20*nelionKoko, 11*nelionKoko-6);
        
        g.setFont(pisteFont);
  
        g.setColor(Color.LIGHT_GRAY);
        int leveys = (int)(10.5*nelionKoko);
        g.drawString("NEXT", 12 * nelionKoko-10, 1 * nelionKoko);
        g.drawString("SCORE " + Integer.toString(peli.getPisteet()), leveys, 12 * nelionKoko);
        g.drawString("LEVEL " + Integer.toString(peli.getVaikeustaso()), leveys, 13 * nelionKoko);
        
        g.drawString("TIME " + peli.getAika(), leveys, 14 * nelionKoko);

        g.setColor(Color.WHITE);
        g.setFont(menuFont);
        
        if (peli.getPaused()) {
            g.drawString("PAUSE", 4 * nelionKoko, 8 * nelionKoko);
        }
        
        if (peli.getGameover()) {
            g.drawString("GAME OVER", 4 * nelionKoko, 8 * nelionKoko);
            g.drawString("restart?  Y/N", 4 * nelionKoko, 9 * nelionKoko);
        }
    }
    
    private void piirraOhjausNaytto(Graphics g) {
//        g.setColor(Color.BLACK);
//        
//        
//        if(peli.getSuunta()==Suunta.VASEN) {
//            g.drawPolygon(new Kolmio(10, 11*nelionKoko,18*nelionKoko));
//        }
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
    
    public void setPeli(Peli peli) {
        this.peli = peli;
    }

    @Override
    public void paivita() {
        super.repaint();
    }
}
