package tetris.pelilogiikka;

import java.util.ArrayList;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Vari;

public class PoistoOperaatiot {

    private Pelilauta pelilauta;
    private ArrayList<Palikka> palikat;
    private ArrayList<Palikka> poistettavatPalikat;
    private ArrayList<Palikka> tarkistettavatPalikat;

    public PoistoOperaatiot(Pelilauta pelilauta) {
        this.pelilauta = pelilauta;
        this.palikat = pelilauta.getPalikat();
        this.poistettavatPalikat = new ArrayList<Palikka>();
        this.tarkistettavatPalikat = new ArrayList<Palikka>();
    }

    public boolean onkoPoistettavia() {
        if (!this.pelilauta.taydetRivit().isEmpty()) {
            return true;
        }
        return false;
    }

    public int poistaTaydetRivit() {
        ArrayList<Integer> poistettavatRivit = this.pelilauta.taydetRivit();
        int poistettavia = poistettavatRivit.size();
        if (!poistettavatRivit.isEmpty()) {
            poistaRivit(poistettavatRivit);
            poistaPoistettavatPalikat();
            tarkistaPalikat();
        }
        return poistettavia;
    }
    
    //poistaa jokaisesta palikasta pisteet joilla on rivin y-koordinaatti
    private void poistaRivit(ArrayList<Integer> poistettavatRivit) {
        for (int rivi : poistettavatRivit) {
            this.pelilauta.nollaaRivi(rivi);
        }
        for (Palikka palikka : palikat) {
            ArrayList<Integer> poistettavatPisteet = new ArrayList<Integer>();
            ArrayList<int[]> muoto = palikka.getMuoto();
            int k = 0;
            for (int i = muoto.size() - 1; i >= 0; i--) {
                int pisteenKorkeus = palikka.getYpos() + palikka.getMuoto().get(i)[1];
                if (poistettavatRivit.contains(pisteenKorkeus)) {
                    poistettavatPisteet.add(i);
                }
            }
            if (!poistettavatPisteet.isEmpty()) {
                poistaPisteet(palikka, poistettavatPisteet);
            }
        }
    }

    private void poistaPisteet(Palikka palikka, ArrayList<Integer> poistettavatPisteet) {
        ArrayList<int[]> muoto = palikka.getMuoto();
        muoto.removeAll(poistettavatPisteet);
        for (int poistettava : poistettavatPisteet) {
            muoto.remove(poistettava);
        }
        if (muoto.isEmpty()) {
            palikka.setYpos(30); // palikka siirretaan ulos pelilaudalta jottei rivinpoistooperaatiot kasittele sita
            poistettavatPalikat.add(palikka);
        } else if (muoto.size() > 1) {
            tarkistettavatPalikat.add(palikka);  // palikan yhtenaisyys tarkistetaan
        }
    }

    public void poistaPiste(Palikka palikka, int poistettavaIndeksi) {
        palikka.getMuoto().remove(poistettavaIndeksi);
    }

    private void poistaPoistettavatPalikat() {
        this.palikat.removeAll(this.poistettavatPalikat);
        this.tarkistettavatPalikat.removeAll(this.poistettavatPalikat);
        this.poistettavatPalikat = new ArrayList<Palikka>();
    }

    public void poistaPalikka(Palikka palikka) {
        this.palikat.remove(palikka);
    }

    private void tarkistaPalikat() {
        for (Palikka palikka : tarkistettavatPalikat) {
            tarkistaYhtenaisyys(palikka);
        }
        this.tarkistettavatPalikat = new ArrayList<Palikka>();
    }

    // tarkistaa palikan pisteiden viereisyyden, jos yksi piste on erillinen se siirretaan uuteen palikkaan
    public boolean tarkistaYhtenaisyys(Palikka palikka) {
        ArrayList<int[]> muoto = palikka.getMuoto();
        if (muoto.isEmpty()) {
            return true;
        }

        int i = 0;
        while (i < muoto.size()) {
            int[] piste1 = muoto.get(i);
            boolean vieruksia = false;
            int j = 0;
            while (j < muoto.size()) {
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
        ArrayList<int[]> muoto = new ArrayList<int[]>();
        muoto.add(new int[]{0, 0});
        Palikka pistePalikka = new Palikka(muoto, x, y, vari);
        palikat.add(pistePalikka);
    }
    
    public void poistaKaikki() {
        this.poistettavatPalikat.clear();
        this.pelilauta.clear();
    }
}
