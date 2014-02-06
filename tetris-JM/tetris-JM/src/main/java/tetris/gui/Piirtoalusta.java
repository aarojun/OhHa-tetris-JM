package tetris.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import tetris.objects.Palikka;
import tetris.objects.Vari;
import tetris.pelilogiikka.Peli;

public class Piirtoalusta extends JPanel implements Paivitettava {
    private Peli peli;
    private int nelionKoko;
    
    public Piirtoalusta(Peli peli, int nelionSivunPituus) {
        this.peli = peli;
        this.nelionKoko = nelionSivunPituus;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<Palikka> kaikkiPalikat = new ArrayList<>();
        kaikkiPalikat.addAll(peli.getSeuraavatPalikat());
        kaikkiPalikat.addAll(peli.getPelilauta().getPalikat());
        
        for (Palikka pl : kaikkiPalikat) {
            Color vari = pl.getVari().getColor();
            int xPos = pl.getXpos();
            int yPos = pl.getYpos();
            g.setColor(vari);
            for (int[] piste : pl.getMuoto()) {
                g.fill3DRect((xPos+piste[0])*nelionKoko, (yPos+piste[1])*nelionKoko, nelionKoko, nelionKoko, true);
            }
        }
    }

    @Override
    public void paivita() {
        super.repaint();
    }
    
}
