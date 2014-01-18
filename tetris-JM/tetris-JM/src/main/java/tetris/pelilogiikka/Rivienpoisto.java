package tetris.pelilogiikka;

import java.util.ArrayList;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;

public class Rivienpoisto {

    private Pelilauta pelilauta;
    private int[] poistettavatRivit;
    private ArrayList<Palikka> palikat;
    private ArrayList<Palikka> poistettavatPalikat;
    private ArrayList<Palikka> tarkistettavatPalikat;

    public Rivienpoisto(Pelilauta pelilauta) {
        this.pelilauta = pelilauta;
        this.poistettavatRivit = new int[]{};
        this.poistettavatPalikat = new ArrayList<>();
        this.tarkistettavatPalikat = new ArrayList<>();
    }

    public boolean onkoPoistettavia() {
        this.poistettavatRivit = this.pelilauta.taydetRivit();
        if (this.poistettavatRivit.length != 0) {
            return true;
        }
        return false;
    }

    public void poistaRivit() {
        for (int i = 0; i < poistettavatRivit.length; i++) {
            poistaRivi(poistettavatRivit[i]);
        }
        poistaPoistettavatPalikat();
        tarkistaPalikat();
    }

    public void poistaRivi(int y) {
        this.pelilauta.nollaaRivi(y);
        for (Palikka palikka : palikat) {
            int[][] muoto = palikka.getMuoto();
            int i = muoto.length - 1;
            int[] poistettavatPisteet = new int[4];
            int k = 0;
            while (palikka.getMuoto()[i] != null) {
                if (palikka.getMuoto()[i][1] == y) {
                    poistettavatPisteet[k] = i;
                }
                i--;
            }
            poistaPisteet(palikka, poistettavatPisteet);
        }
        poistaPoistettavatPalikat();
    }

    public void poistaPiste(Palikka palikka, int indeksi) {
        int[][] muoto = palikka.getMuoto();

        if (muoto[1] == null) {
            muoto = null;
            poistettavatPalikat.add(palikka);
        } else {
            int i = indeksi;
            while (muoto[i + 1] != null) {
                muoto[i] = muoto[i + 1];
                i++;
            }
        }
        if(muoto.length>=2) {
            tarkistettavatPalikat.add(palikka);
        } else {
            tarkistettavatPalikat.remove(palikka);
        }
    }

    public void poistaPisteet(Palikka palikka, int[] poistettavatPisteet) {
        for (int i = 0; i < poistettavatPisteet.length; i++) {
            poistaPiste(palikka, poistettavatPisteet[i]);
        }
    }

    public void poistaPoistettavatPalikat() {
        this.palikat.removeAll(this.poistettavatPalikat);
        this.tarkistettavatPalikat.removeAll(this.poistettavatPalikat);
        this.poistettavatPalikat = new ArrayList<>();
    }

    public void tarkistaPalikat() {
        for (Palikka palikka : tarkistettavatPalikat) {
            tarkistaYhtenaisyys(palikka);
        }
        this.tarkistettavatPalikat = new ArrayList<>();
    }

    public boolean tarkistaYhtenaisyys(Palikka palikka) {
        int[][] muoto = palikka.getMuoto();
        if (muoto == null) {
            return true;
        }

        int i = 0;
        while (muoto[i] != null) {
            int[] piste1 = muoto[i];
            boolean vieruksia = false;
            int j = 0;
            while (muoto[j] != null) {
                if (i != j) {
                    int[] piste2 = muoto[j];
                    if (onkoVierusPiste(piste1, piste2)) {
                        vieruksia = true;
                    }
                }
                if (vieruksia) {
                    break;
                }
                j++;
            }

            if (!vieruksia) {
                jaaOsiin(palikka, i);
                return false;
            }
            i++;
        }
        return true;
    }

    public boolean onkoVierusPiste(int[] piste1, int[] piste2) {
        if (piste2[0] > piste1[0] - 2 && piste2[0] < piste1[0] + -2
                && piste2[1] > piste1[1] - 2 && piste2[1] < piste1[1] + 2) {
            return true;
        } else {
            return false;
        }
    }

    public void jaaOsiin(Palikka palikka, int irtonaisenIndeksi) {
        int[] piste = palikka.getMuoto()[irtonaisenIndeksi];

        int x = palikka.getXpos() + piste[0];
        int y = palikka.getYpos() + piste[1];
        int vari = palikka.getVari();

        luoPistePalikka(x, y, vari);
        poistaPiste(palikka, irtonaisenIndeksi);
    }

    public void luoPistePalikka(int x, int y, int vari) {
        int[][] muoto = new int[][]{{0, 0}};
        Palikka pistePalikka = new Palikka(muoto, x, y, vari);
        palikat.add(pistePalikka);
    }
}
