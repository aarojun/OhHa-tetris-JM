package tetris.objects;

import java.util.ArrayList;

/**
 * Luokka joka pitaa kirjaa tetris-pelissa asetetuista palikoista seka niiden asettamista alueista.
 * pelilaudan alue sailytetaan 2-ulotteisena taulukkona.
 * @author zaarock
 */
public class Pelilauta {

    private int[][] alueMatriisi;
    private int leveys;
    private int korkeus;
    private ArrayList<Palikka> palikat;

    /**
     * Alustaa pelilaudalle tyhjan palikka -listan seka 2-ulotteisen taulukon.
     * 
     * @param leveys laudan alueen leveus
     * @param korkeus laudan alueen korkeus
     */
    public Pelilauta(int leveys, int korkeus) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.palikat = new ArrayList<>();
        this.alueMatriisi = new int[leveys][korkeus];
    }
    // 2-ulotteinen matriisi joka kuvaa pelilaudalle asetettuja alueita
    // luokka myos tuntee kaikki laudalle lukitut palikka -oliot

    /**
     * Asettaa laudan pisteelle sille annetun arvon
     * @param x pisteen x -koordinaatti
     * @param y pisteen y -koordinaatti
     * @param arvo pisteelle asetettava arvo
     */
    public void setPisteenArvo(int x, int y, int arvo) {
        try {
            this.alueMatriisi[x][y] = arvo;
        } catch (Exception ArrayIndexOutOfBoundsException) {
        }
    }

    /**
     * Asettaa annetun pisteen 'taydeksi' arvolla 1.
     * @param x pisteen x-koordinaatti
     * @param y pisteen y-koordinaatti
     */
    public void asetaPiste(int x, int y) {
        setPisteenArvo(x, y, 1);
    }

    /**
     * Asettaa annetun pisteen 'tyhjaksi' arvolla 0.
     * @param x pisteen x-koordinaatti
     * @param y pisteen y-koordinaatti
     */
    public void poistaPiste(int x, int y) {
        setPisteenArvo(x, y, 0);
    }

    /**
     * Asettaa palikkaa vastaaville pisteille annetun arvon.  
     * @param pl Asetettava Palikka.
     * @param arvo  Pisteille asetettava arvo.
     */
    private void setArvotPalikanMukaan(Palikka pl, int arvo) {
        int x = pl.getXpos();
        int y = pl.getYpos();
        for (int[] piste : pl.getMuoto()) {
            setPisteenArvo(x + piste[0], y + piste[1], arvo);
        }
    }

    /**
     * Asettaa palikkaa vastaavat pisteet 'taysiksi' arvolla 1.
     * @param palikka Palikka joka maarittaa asetettavat pisteet.
     */
    public void liitaPalikka(Palikka palikka) {
        setArvotPalikanMukaan(palikka,1);
    }
    
    /**
     * Asettaa palikkaa vastaavat pisteet 'tyhjiksi' arvolla 0.
     * @param palikka Palikka joka maarittaa asetettavat pisteet.
     */
    public void irroitaPalikka(Palikka palikka) {
        setArvotPalikanMukaan(palikka,0);
    }

    /**
     * Lisaa annetun palikan palikat -listaan seka asettaa palikkaa vastaavat pisteet 'taysiksi' arvolla 1.
     * @param palikka Pelilautaan liitettava palikka.
     */
    public void lisaaLiitettyPalikka(Palikka palikka) {
        palikat.add(palikka);
        liitaPalikka(palikka);
    }

    /**
     * Tarkistaa onko pelilaudalla oleva piste taysi vai tyhja. Palauttaa myos tosi jos koordinaatit ovat laudan alueen ulkopuolella. 
     * @param x tarkistettavan pisteen x-koordinaatti
     * @param y tarkistettava pisteen y-koordinaatti
     * @return palauttaa tosi jos piste on taysi
     */
    public boolean tarkistaPiste(int x, int y) {
        try { 
            if (this.alueMatriisi[x][y] != 0) {
            return true;
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            return true;
        }
        return false;
    }


    /**
     * Tarkistaa laudalla olevien taysien vaakatasossa olevien rivien y-koordinaatit ja palauttaa ne listana.
     * @return ArrayList taysien rivien y-koordinaateista
     */
    public ArrayList<Integer> taydetRivit() {
        ArrayList<Integer> taydetRivit = new ArrayList<>();
        int k = 0;
        for (int j = 0; j < korkeus; j++) {
            boolean taysi = true;
            for (int i = 0; i < leveys; i++) {
                if (alueMatriisi[i][j] == 0) {
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

    /**
     * Muuttaa kaikki annetulla y-koordinaatilla olevat pisteet 'tyhjiksi' arvolla 0.
     * @param y rivin korkeus y-koordinaattina.
     */
    public void nollaaRivi(int y) {
        for (int i = 0; i < leveys; i++) {
            this.alueMatriisi[i][y] = 0;
        }
    }

    /**
     * Tyhjentaa pelilaudan. Aluematriisi seka palikka-lista nollataan.
     * 
     */
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
