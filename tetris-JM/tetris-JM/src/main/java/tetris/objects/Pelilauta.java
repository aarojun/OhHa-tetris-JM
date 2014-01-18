package tetris.objects;

import java.util.ArrayList;

public class Pelilauta {

    private int[][] alueMatriisi;
    private int leveys;
    private int korkeus;

    public Pelilauta(int leveys, int korkeus) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.alueMatriisi = null;
        generoiAlueMatriisi();
    }

    private void generoiAlueMatriisi() {
        for (int i = 0; i < leveys; i++) {
            for (int j = 0; i < korkeus; i++) {
                this.alueMatriisi[i][j] = 0;
            }
        }
    }

    public int[][] getAlueMatriisi() {
        return this.alueMatriisi;
    }

    public void asetaPiste(int x, int y) {
        this.alueMatriisi[x][y] = 1;
    }

    public boolean tarkistaPiste(int x, int y) {
        if (x < 0 || y < 0 || y >= this.korkeus || x >= this.leveys) {
            return true;
        } else if (this.alueMatriisi[x][y] == 1) {
            return true;
        }
        return false;
    }

    public int[] taydetRivit() {
        int[] taydetRivit = new int[]{};
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
                taydetRivit[k] = j;
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
