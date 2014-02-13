package tetris.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.pelilogiikka.PeliRajapinta;

public class LaudanPiirto extends BufferoituPanel {
    private BufferedImage lauta;
    private int korkeus;
    private int leveys;
    private PeliRajapinta peli;
    private int nelionKoko;
    private int varjotusEro;
    private PiirtoTyokalu tyokalu;

    public LaudanPiirto(PeliRajapinta peli, int nelionKoko, PiirtoTyokalu tyokalu) {
        this.setOpaque(false);
        this.nelionKoko = nelionKoko;
        this.varjotusEro = nelionKoko/5;
        this.peli = peli;
        this.tyokalu = tyokalu;
        this.korkeus = peli.getPelilauta().getKorkeus();
        this.leveys = peli.getPelilauta().getLeveys();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(peli.getLautaPaivita()) {
            uudelleenPiirra();
            peli.lautaPaivitetty();
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(lauta, null, 0,0);       
    }
    
    void uudelleenPiirra() {
        if(lauta != null) {
            lauta.flush();
        }
        int lev = this.getWidth();
        int kor = this.getHeight();
        lauta = new BufferedImage(lev,kor,BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D gc = lauta.createGraphics();
        piirraPelilauta(gc);

        tyokalu.piirraPalikat(gc, peli.getPelilauta().getPalikat());
        tyokalu.piirraSeuraavatPalikat(gc, peli.getSeuraavatPalikat());
        
        if(peli.onkoVaihtoAika()){
            piirraEfektiPalikka(gc);
        }
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
   

    @Override
    public void paivita() {
        uudelleenPiirra();
    }    
}
