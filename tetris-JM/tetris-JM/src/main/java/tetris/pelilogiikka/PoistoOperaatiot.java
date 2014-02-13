package tetris.pelilogiikka;

import java.util.ArrayList;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Vari;

/**
 * Luokka joka huolehtii pelin objektien poistoista, kuten rivin poistoista ja
 * palikoiden osiin jakamisesta.
 *
 * @author zaarock
 */
public class PoistoOperaatiot {

    private Pelilauta pelilauta;
    private ArrayList<Palikka> palikat;
    private ArrayList<Palikka> poistettavatPalikat;
    private ArrayList<Palikka> tarkistettavatPalikat;

    /**
     * Alustaa luokan toimimaan annetulla pelilaudalla.
     *
     * @param pelilauta liitettava pelilauta
     */
    public PoistoOperaatiot(Pelilauta pelilauta) {
        this.pelilauta = pelilauta;
        this.palikat = pelilauta.getPalikat();
        this.poistettavatPalikat = new ArrayList<Palikka>();
        this.tarkistettavatPalikat = new ArrayList<Palikka>();
    }

    /**
     * Tarkistaa onko pelilaudassa poistettavia riveja
     *
     * @return onko poistettaviariveja
     */
    public boolean onkoPoistettavia() {
        if (!this.pelilauta.taydetRivit().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Poistaa kaikki taydet rivit pelilaudalta.
     *
     * @return poistettujen rivien maara
     */
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

    /**
     * Poistaa pelilaudalta ja jokaisesta palikasta pisteet joilla on rivin y-koordinaatti.
     * (algoritmia voisi optimoida viela)
     *
     * @param poistettavatRivit lista poistettavien rivien y-koordinaatteja
     */
    private void poistaRivit(ArrayList<Integer> poistettavatRivit) {
        for (int rivi : poistettavatRivit) {
            this.pelilauta.nollaaRivi(rivi);
        }
        for (Palikka palikka : palikat) {
            ArrayList<Integer> poistettavatPisteet = new ArrayList<Integer>();
            ArrayList<int[]> muoto = palikka.getMuoto();
            boolean poistettavia = false;
            for (int i = muoto.size() - 1; i >= 0; i--) {
                int pisteenKorkeus = palikka.getYpos() + muoto.get(i)[1];
                if (poistettavatRivit.contains(pisteenKorkeus)) {
                    poistettavatPisteet.add(i);
                    poistettavia = true;
                }
            }
            if (poistettavia) {
                poistaPisteet(palikka, poistettavatPisteet);
            }
        }
    }

    /**
     * Poistaa pelilaudalta ja jokaisesta palikasta pisteet joilla on annettu y-koordinaatti
     * @param poistettava poistettavan rivin y-koordinaatti
     */
    private void poistaRivi(int poistettava) {
        pelilauta.nollaaRivi(poistettava);
        for (Palikka palikka : palikat) {
            int yPos = palikka.getYpos();
            if (yPos <= poistettava) {
                ArrayList<Integer> poistettavatPisteet = new ArrayList<Integer>();
                ArrayList<int[]> muoto = palikka.getMuoto();
                boolean poistettavia = false;
                for (int i = muoto.size() - 1; i >= 0; i--) {
                    int pisteenKorkeus = yPos + muoto.get(i)[1];
                    if (pisteenKorkeus == poistettava) {
                        poistettavatPisteet.add(i);
                        poistettavia = true;
                    }
                }
                if (poistettavia) {
                    poistaPisteet(palikka, poistettavatPisteet);
                }
            }
        }
    }

    /**
     * Poistaa palikasta pisteet joilla on listassa olevat indeksit. Asettaa
     * palikat poistettaviksi tai yhtenaisyyden tarkistettaviksi jaljelle jaavan
     * pistemaaran perusteella.
     *
     * @param palikka kasiteltava palikka
     * @param poistettavatIndeksit lista poistettavia indekseja
     */
    private void poistaPisteet(Palikka palikka, ArrayList<Integer> poistettavatIndeksit) {
        ArrayList<int[]> muoto = palikka.getMuoto();
        for (int poistettava : poistettavatIndeksit) {
            muoto.remove(poistettava);
        }
        if (muoto.isEmpty()) {
            palikka.setYpos(30);
            poistettavatPalikat.add(palikka);
        } else if (muoto.size() > 1) {
            tarkistettavatPalikat.add(palikka);
        }
    }

    /**
     * Poistaa palikasta pisteen jolla on annettu indeksi palikan piste-listassa
     * / muodossa.
     *
     * @param palikka kasiteltava palikka
     * @param poistettavaIndeksi poistettavan pisteen indeksi
     */
    public void poistaPiste(Palikka palikka, int poistettavaIndeksi) {
        palikka.getMuoto().remove(poistettavaIndeksi);
    }

    /**
     * Poistaa kaikki luokan poistolistassa olevat palikat pelilaudan listasta.
     */
    private void poistaPoistettavatPalikat() {
        this.palikat.removeAll(this.poistettavatPalikat);
        this.tarkistettavatPalikat.removeAll(this.poistettavatPalikat);
        this.poistettavatPalikat.clear();
    }

    /**
     * Tarkistaa tarkistettavien palikoiden yhtenaisyyden.
     */
    private void tarkistaPalikat() {
        for (Palikka palikka : tarkistettavatPalikat) {
            tarkistaYhtenaisyys(palikka);
        }
        this.tarkistettavatPalikat.clear();
    }

    /**
     * Tarkistaa palikan pisteiden viereisyyden, jos yksi piste on erillinen se
     * siirretaan uuteen palikkaan.
     *
     * @param palikka kasiteltava palikka
     * @return onko palikka yhtenainen
     */
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
                if (j != i) {
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

    /**
     * Tarkistaa onko piste2 pisteen 1 vieruspiste.
     *
     * @param piste1
     * @param piste2
     * @return onko vieruspiste
     */
    private boolean onkoVierusPiste(int[] piste1, int[] piste2) {
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

    /**
     * Luo annetusta palikan pisteesta uuden palikan ja irrottaa sen aiemmasta
     *
     * @param palikka alkuperainen palikka
     * @param irtonaisenIndeksi irtonaisen indeksi palikan pistelistassa
     */
    private void jaaOsiin(Palikka palikka, int irtonaisenIndeksi) {
        int[] piste = palikka.getMuoto().get(irtonaisenIndeksi);

        int x = palikka.getXpos() + piste[0];
        int y = palikka.getYpos() + piste[1];
        Vari vari = palikka.getVari();

        luoPistePalikka(x, y, vari);
        poistaPiste(palikka, irtonaisenIndeksi);
    }

    /**
     * Luo palikan joka koostuu annetusta pisteesta
     *
     * @param x pisteen x-koordinaatti
     * @param y pisteen y-koordinaatti
     * @param vari palikan vari
     */
    public void luoPistePalikka(int x, int y, Vari vari) {
        ArrayList<int[]> muoto = new ArrayList<int[]>();
        muoto.add(new int[]{0, 0});
        Palikka pistePalikka = new Palikka(muoto, x, y, vari);
        palikat.add(pistePalikka);
    }

    /**
     * Tyhjentaa koko pelilaudan.
     */
    public void poistaKaikki() {
        this.poistettavatPalikat.clear();
        this.pelilauta.clear();
    }
}
