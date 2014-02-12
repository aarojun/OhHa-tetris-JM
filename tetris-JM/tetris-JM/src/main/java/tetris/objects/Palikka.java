package tetris.objects;

import java.util.ArrayList;

/**
 * 2-ulotteinen objekti jolle on maaritetty koordinaatit seka
 * lista 2-ulotteisista pisteista jotka maarittavat palikan muodon. Muoto kuvaa
 * palikan pisteiden sijaintia suhteessa palikan omiin koordinaatteihin
 *
 * tuntee myos oman varinsa Vari -objektina.
 * 
 * 
 * @author zaarock 
 */
public class Palikka {

    private ArrayList<int[]> muoto;
    private int xPos;
    private int yPos;
    private Vari vari;

    /**
     *
     * @param pisteet Palikalle asetettavat pisteet taulukossa, jossa
     * koordinaatit ovat lukupareja. pisteet[0] on ensimmaisen pisteen
     * x-koordinaatti ja pisteet[1] on y-koordinaatti, jne.
     * @param x Palikalle maaritettava x-koordinaatti
     * @param y Palikalle maariteltava y-koordinaatti
     * @param vari Vari jolla palikka piirretaan kayttoliittymassa.
     */
    public Palikka(int[] pisteet, int x, int y, Vari vari) {
        this.xPos = x;
        this.yPos = y;
        this.vari = vari;

        this.muoto = new ArrayList<>();
        setMuoto(pisteet);
    }

    /**
     *
     * @param muoto Palikalle asetettavat pisteet 2-ulotteisena matriiseina
     * ArrayListissa
     * @param x Palikalle maaritettava x-koordinaatti
     * @param y Palikalle maaritettava y-koordinaatti
     * @param vari Vari jolla palikka piirretaan kayttoliittymassa
     */
    public Palikka(ArrayList<int[]> muoto, int x, int y, Vari vari) {
        this.xPos = x;
        this.yPos = y;
        this.vari = vari;

        this.muoto = new ArrayList<>(muoto);
    }

    /**
     * Kaantaa ja asettaa 2-ulotteiset Tetromino - enumin antamasta muodosta
     * palikan omaan arraylistiin
     *
     * @param pisteet int[] taulukko jossa jokainen parillinen luku on
     * x-koordinaatti ja seuraava pariton sita vastaava y-koordinaatti
     */
    public void setMuoto(int[] pisteet) {
        this.muoto.clear();
        for (int i = 0; i <= 6; i += 2) {
            int xKoord = pisteet[i];
            int yKoord = pisteet[i + 1];
            this.muoto.add(new int[]{xKoord, yKoord});
        }
    }

    /**
     * Muuttaa palikan koordinaatteja annetun maaran
     *
     * @param x x-koordinaatin muutos
     * @param y y-koordinaatin muutos
     */
    public void liiku(int x, int y) {
        this.xPos += x;
        this.yPos += y;
    }

    public void setMuoto(ArrayList<int[]> muoto) {
        this.muoto = new ArrayList<>(muoto);
    }

    public void setVari(Vari vari) {
        this.vari = vari;
    }

    public void setXpos(int x) {
        this.xPos = x;
    }

    public void setYpos(int y) {
        this.yPos = y;
    }

    public int getXpos() {
        return this.xPos;
    }

    public int getYpos() {
        return this.yPos;
    }

    public ArrayList<int[]> getMuoto() {
        return this.muoto;
    }

    public Vari getVari() {
        return this.vari;
    }
}
