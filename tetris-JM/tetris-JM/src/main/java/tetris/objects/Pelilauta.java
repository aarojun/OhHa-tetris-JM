package tetris.objects;

import java.util.ArrayList;

public class Pelilauta {

    private int[][] alueMatriisi;
    private int leveys;
    private int korkeus;
    private ArrayList<Palikka> palikat;
    
    
    public Pelilauta(int leveys, int korkeus) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.palikat = new ArrayList<>();
        this.alueMatriisi = new int[leveys][korkeus];
    }
    // 2-ulotteinen matriisi joka kuvaa pelilaudalle asetettuja alueita
    // luokka tuntee kaikki laudalle lukitut palikat

    public void asetaPiste(int x, int y) {
        if (x < leveys && y < korkeus) {
            this.alueMatriisi[x][y] = 1;
        }
    }

    public void poistaPiste(int x, int y) {
        if (x < leveys && y < korkeus) {
            this.alueMatriisi[x][y] = 0;
        }
    }

    public void liitaPalikka(Palikka palikka) {
        int x = palikka.getXpos();
        int y = palikka.getYpos();

        for (int[] piste : palikka.getMuoto()) {
            asetaPiste(x + piste[0], y + piste[1]);
        }
    }

    public void lisaaLiitettyPalikka(Palikka palikka) {
        palikat.add(palikka);
        liitaPalikka(palikka);
    }

    public void irrotaPalikka(Palikka palikka) {
        int x = palikka.getXpos();
        int y = palikka.getYpos();

        for (int[] piste : palikka.getMuoto()) {
            poistaPiste(x + piste[0], y + piste[1]);
        }
    }

    // palauttaa tosi jos pisteella on palikka tai seina
    public boolean tarkistaPiste(int x, int y) {
        if (x < 0 || y < 0 || y >= this.korkeus || x >= this.leveys) {
            return true;
        } else if (this.alueMatriisi[x][y] == 1) {
            return true;
        }
        return false;
    }

    //antaa listan kaikkien taysien rivien y-koordinaateista
    public ArrayList<Integer> taydetRivit() {
        ArrayList<Integer> taydetRivit = new ArrayList<>();
        int k = 0;
        for (int j = 0; j < korkeus; j++) {
            boolean taysi = true;
            for (int i = 0; i < leveys; i++) {
                if (alueMatriisi[i][j] != 1) {
                    taysi = false;
                    break;
                }
            }
            if (taysi) {
                taydetRivit.add(k, j);
                k++;
            }
        }
        return taydetRivit;
    }

    public void nollaaRivi(int y) {
        for (int i = 0; i < leveys; i++) {
            this.alueMatriisi[i][y] = 0;
        }
    }
    
    // tyhjentaa pelilaudan
    public void clear() {
        this.alueMatriisi = new int[leveys][korkeus];
        this.palikat.clear();
    }
    
    public ArrayList<Palikka> getPalikat() {
        return this.palikat;
    }
    
    public int[][] getAlueMatriisi() {
        return this.alueMatriisi;
    }
    
    public int getKorkeus() {
        return this.korkeus;
    }
    
    public int getLeveys() {
        return this.leveys;
    }
}
