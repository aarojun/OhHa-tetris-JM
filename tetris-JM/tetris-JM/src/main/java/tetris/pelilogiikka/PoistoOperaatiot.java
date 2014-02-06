package tetris.pelilogiikka;

import java.util.ArrayList;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Vari;

public class PoistoOperaatiot {

    private Pelilauta pelilauta;
    private ArrayList<Integer> poistettavatRivit;
    private ArrayList<Palikka> palikat;
    private ArrayList<Palikka> poistettavatPalikat;
    private ArrayList<Palikka> tarkistettavatPalikat;

    public PoistoOperaatiot(Pelilauta pelilauta) {
        this.pelilauta = pelilauta;
        this.palikat = pelilauta.getPalikat();
        this.poistettavatRivit = new ArrayList<>();
        this.poistettavatPalikat = new ArrayList<>();
        this.tarkistettavatPalikat = new ArrayList<>();
    }

    public boolean onkoPoistettavia() {
        this.poistettavatRivit = this.pelilauta.taydetRivit();
        if (!this.poistettavatRivit.isEmpty()) {
            return true;
        }
        return false;
    }

    public int poistaTaydetRivit() {
        this.poistettavatRivit = this.pelilauta.taydetRivit();
        int poistettavia = poistettavatRivit.size();
        if (poistettavia > 0) {
            for (int i = 0; i < poistettavia; i++) {
                poistaRivi(poistettavatRivit.get(i));
            }
            poistaPoistettavatPalikat();
            tarkistaPalikat();
        }
        return poistettavia;
    }

    private void poistaRivi(int y) {
        this.pelilauta.nollaaRivi(y);
        for (Palikka palikka : palikat) {
            if (palikka.getYpos() <= y && palikka.getYpos() >= y - 4) {

                ArrayList<int[]> muoto = palikka.getMuoto();
                int i = muoto.size() - 1;
                ArrayList<Integer> poistettavatPisteet = new ArrayList<>();
                int k = 0;
                while (palikka.getMuoto().get(i) != null) {
                    if (palikka.getMuoto().get(i)[1] == y) {
                        poistettavatPisteet.add(k, i);
                        k++;
                    }
                    i--;
                }
                if (k != 0) {
                    poistaPisteet(palikka, poistettavatPisteet);
                }
            }
        }
    }

    private void poistaPisteet(Palikka palikka, ArrayList<Integer> poistettavatPisteet) {
        ArrayList<int[]> muoto = palikka.getMuoto();
        for (int i = 0; i < poistettavatPisteet.size(); i++) {
            int poistettavaIndeksi = poistettavatPisteet.get(i);
            muoto.remove(poistettavaIndeksi);
        }
        if (muoto.isEmpty()) {
            palikka.setYpos(30); // palikka siirretaan ulos pelilaudalta jottei rivinpoistooperaatiot kasittele sita
            poistettavatPalikat.add(palikka);
        } else if (muoto.size() > 1) {
            tarkistettavatPalikat.add(palikka);
        }
    }
    
    public void poistaPiste(Palikka palikka, int poistettavaIndeksi) {
        palikka.getMuoto().remove(poistettavaIndeksi);
    }

    private void poistaPoistettavatPalikat() {
        this.palikat.removeAll(this.poistettavatPalikat);
        this.tarkistettavatPalikat.removeAll(this.poistettavatPalikat);
        this.poistettavatPalikat = new ArrayList<>();
    }

    public void poistaPalikka(Palikka palikka) {
        this.palikat.remove(palikka);
    }

    private void tarkistaPalikat() {
        for (Palikka palikka : tarkistettavatPalikat) {
            tarkistaYhtenaisyys(palikka);
        }
        this.tarkistettavatPalikat = new ArrayList<>();
    }

    public boolean tarkistaYhtenaisyys(Palikka palikka) {
        ArrayList<int[]> muoto = palikka.getMuoto();
        if (muoto.isEmpty()) {
            return true;
        }

        int i = 0;
        while (muoto.get(i) != null) {
            int[] piste1 = muoto.get(i);
            boolean vieruksia = false;
            int j = 0;
            while (muoto.get(j) != null) {
                if (i != j) {
                    int[] piste2 = muoto.get(j);
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
        if (piste1[0] == piste2[0]) {
            if (piste1[1] == piste2[1] + 1 || piste1[1] == piste2[1] - 1) {
                return true;
            }
        } else if (piste1[1] == piste2[1]) {
            if (piste1[0] == piste2[0] + 1 || piste1[0] == piste2[0] - 1) {
                return true;
            }
        }
        return false;
    }

    private void jaaOsiin(Palikka palikka, int irtonaisenIndeksi) {
        int[] piste = palikka.getMuoto().get(irtonaisenIndeksi);

        int x = palikka.getXpos() + piste[0];
        int y = palikka.getYpos() + piste[1];
        Vari vari = palikka.getVari();

        luoPistePalikka(x, y, vari);
        poistaPiste(palikka, irtonaisenIndeksi);
    }

    public void luoPistePalikka(int x, int y, Vari vari) {
        ArrayList<int[]> muoto = new ArrayList<>();
        muoto.add(new int[]{0, 0});
        Palikka pistePalikka = new Palikka(muoto, x, y, vari);
        palikat.add(pistePalikka);
    }
}
