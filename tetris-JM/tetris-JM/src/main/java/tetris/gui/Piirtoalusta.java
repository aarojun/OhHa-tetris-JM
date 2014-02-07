package tetris.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
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

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 10 * nelionKoko, 20 * nelionKoko);

        for (Palikka pl : kaikkiPalikat) {
            Color vari = pl.getVari().getColor();
            int xPos = pl.getXpos();
            int yPos = pl.getYpos();
            ArrayList<int[]> muoto = pl.getMuoto();
            for (int i = 0; i < muoto.size(); i++) {
                int[] piste = muoto.get(i);
                g.setColor(vari);
                g.fillRect((xPos + piste[0]) * nelionKoko, (yPos + piste[1]) * nelionKoko, nelionKoko, nelionKoko);
                g.setColor(Color.BLACK);
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

            piirraMittarit(g);
        }
    }

    private void piirraMittarit(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("SCORE " + Integer.toString(peli.getPisteet()), 7 * nelionKoko, 1 * nelionKoko);
        g.drawString("LEVEL " + Integer.toString(peli.getVaikeustaso()), 7 * nelionKoko, 2 * nelionKoko);

        g.setColor(Color.WHITE);

        if (!peli.onkoPaalla()) {
            g.drawString("GAME OVER", 4 * nelionKoko, 8 * nelionKoko);
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
}
