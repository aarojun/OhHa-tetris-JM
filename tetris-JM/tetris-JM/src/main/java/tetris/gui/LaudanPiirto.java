package tetris.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.pelilogiikka.Peli;

public class LaudanPiirto extends BufferoituPanel {
    private BufferedImage lauta;
    private int korkeus;
    private int leveys;
    private Peli peli;
    private int nelionKoko;
    private int varjotusEro;
    private Color varjo = new Color(0,0,0,130);

    public LaudanPiirto(Peli peli, int nelionKoko) {
//        initComponents();
        this.setOpaque(false);
        this.nelionKoko = nelionKoko;
        this.varjotusEro = nelionKoko/5;
        this.peli = peli;
        this.korkeus = peli.getPelilauta().getKorkeus();
        this.leveys = peli.getPelilauta().getLeveys();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        if(lauta == null || peli.getLautaPaivita()) {
            uudelleenPiirra();
            peli.lautaPaivitetty();
        }
        g2.drawImage(lauta, null, 0,0);
 
        
    }
    
    private void piirraPelilauta(Graphics g) {
        g.setColor(Color.WHITE);
        Pelilauta grid = peli.getPelilauta();
        int[][] matriisi = grid.getAlueMatriisi();
        for(int j = 0; j<korkeus; j++) {
            for(int i = 0; i<leveys; i++) {
                if(matriisi[i][j]==1) {
                    g.fillRect(i*nelionKoko-1, j*nelionKoko-1, nelionKoko+2, nelionKoko+2);
                }
            }
        }
    }

    private void piirraSeuraavatPalikat(Graphics g, ArrayList<KaantyvaPalikka> palikat) {
        for (int i = 1; i<palikat.size() ; i++) {
            piirraPalikka(g, palikat.get(i));
        }
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
    
    private void piirraEfektiPalikka(Graphics g) {
        Palikka pl = peli.getEfektiPalikka();
        Color vari = new Color(255,255,255,100);
        piirraPalikkaOverlay(g,pl,vari);
    }
    
    private void piirraPalikkaOverlay(Graphics g, Palikka pl, Color vari) {
        int xPos = pl.getXpos();
        int yPos = pl.getYpos();
        ArrayList<int[]> muoto = pl.getMuoto();
        for (int i = 0; i < muoto.size(); i++) {
            int[] piste = muoto.get(i);
            g.setColor(vari);
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
   

    @Override
    public void paivita() {
        super.repaint();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }

    @Override
    void uudelleenPiirra() {
        int lev = this.getWidth();
        int kor = this.getHeight();
        lauta = new BufferedImage(lev,kor,BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D gc = lauta.createGraphics();
        piirraPelilauta(gc);

        piirraPalikat(gc, peli.getPelilauta().getPalikat());
        piirraSeuraavatPalikat(gc, peli.getSeuraavatPalikat());
        
        if(peli.onkoEfektitPaalla()){
            piirraEfektiPalikka(gc);
        }
    }
    
}
