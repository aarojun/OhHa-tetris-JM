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

    public int[][] getAlueMatriisi() {
        return this.alueMatriisi;
    }
    
    public ArrayList<Palikka> getPalikat() {
        return this.palikat;
    }

    public void asetaPiste(int x, int y) {
        this.alueMatriisi[x][y] = 1;
    }
    
    public void poistaPiste(int x, int y) {
        this.alueMatriisi[x][y] = 0;
    }
    
    public void liitaPalikka(Palikka palikka) {
        int x = palikka.getXpos();
        int y = palikka.getYpos();
        
        for (int[] piste : palikka.getMuoto()) {
            asetaPiste(x+piste[0],y+piste[1]);
        }
    }
    
    public void irrotaPalikka(Palikka palikka) {
        int x = palikka.getXpos();
        int y = palikka.getYpos();
        
        for (int[] piste : palikka.getMuoto()) {
            poistaPiste(x+piste[0],y+piste[1]);
        }
    }

    public boolean tarkistaPiste(int x, int y) {
        if (x < 0 || y < 0 || y >= this.korkeus || x >= this.leveys) {
            return true;
        } else if (this.alueMatriisi[x][y] == 1) {
            return true;
        }
        return false;
    }

    public ArrayList<Integer> taydetRivit() {
        ArrayList<Integer> taydetRivit = new ArrayList<>();
        int k = 0;
        for (int j = 0; j < korkeus; j++) {
            boolean taysi = true;
            for (int i = 1; i < leveys; i++) {
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
        for (int i=0; i<leveys; i++) {
            this.alueMatriisi[y][i] = 0;
        }
    } 
}
