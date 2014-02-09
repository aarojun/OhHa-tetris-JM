package tetris.pelilogiikka;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import tetris.gui.Paivitettava;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Tetromino;

public class Peli {

    private PeliLoop2 peliloop;
    private Kello kello;
    private Random random;
    private Pelilauta pelilauta;
    private ArrayList<KaantyvaPalikka> seuraavatPalikat;
    private ArrayList<Tetromino> palikkaHistoria;
    private KaantyvaPalikka nykyinenPalikka;
    private KaantyvaPalikka varjoPalikka;
    private int aikayksikko;
    private int vaikeustaso;
    private int frame;
    private int pisteet;
    private TormaysLogiikka tormayslogiikka;
    private PoistoOperaatiot poistot;
    private Paivitettava gui;
    private boolean paalla;
    private boolean paused;
    private boolean gameover;
    private boolean tarkistaRivit;
    private boolean liukuAikaPaalla;
    private boolean lukitse;
    private boolean alas;
    private boolean pudota;
    private boolean vasen;
    private boolean oikea;
    private boolean pudotaKokonaan;
    private boolean kaannaVasen;
    private boolean kaannaOikea;
    private boolean yes;
    private boolean no;
    private final Tetromino[] tetrominoSet = new Tetromino[]{Tetromino.I, Tetromino.J,
        Tetromino.L, Tetromino.O, Tetromino.S, Tetromino.T, Tetromino.Z};

    public Peli() {
        this.pelilauta = new Pelilauta(10, 20);
        this.seuraavatPalikat = new ArrayList<>();
        this.palikkaHistoria = new ArrayList<>();

        this.aikayksikko = 30;
        this.vaikeustaso = 0;
        this.pisteet = 0;
        this.frame = 0;

        this.poistot = new PoistoOperaatiot(pelilauta);
        this.tormayslogiikka = new TormaysLogiikka(pelilauta, poistot);

        this.random = new Random();
        this.kello = new Kello();

        alustaPalikkaHistoria();
        luoPalikat();
        asetaSeuraavat();
        this.nykyinenPalikka = seuraavatPalikat.get(0);
        luoVarjopalikka();

        this.paalla = true;
        this.tarkistaRivit = false;
        this.paused = false;
        this.gameover = false;
        this.liukuAikaPaalla = false;
        this.vasen = false;
        this.oikea = false;
        this.alas = false;
        this.pudota = false;
        this.pudotaKokonaan = false;
        this.lukitse = false;
        this.kaannaVasen = false;
        this.kaannaOikea = false;
        this.no = false;
        this.yes = false;
    }

    public void setPaivitettava(Paivitettava paivitettava) {
        this.gui = paivitettava;
    }

    public void start() {
        this.peliloop = new PeliLoop2(this, gui);
        aloitaPeliLoop();
    }

    private void aloitaPeliLoop() {
        peliloop.run();

    }

    private void alustaPalikkaHistoria() {
        palikkaHistoria.add(Tetromino.Z);
        palikkaHistoria.add(Tetromino.S);
        palikkaHistoria.add(Tetromino.Z);
        palikkaHistoria.add(Tetromino.S);
    }

    private void luoPalikka() {
        KaantyvaPalikka uusiPalikka = new KaantyvaPalikka(annaSatunnainenTetromino());
        uusiPalikka.setXpos(3);
        uusiPalikka.setYpos(-10);
        seuraavatPalikat.add(uusiPalikka);
    }

    private void luoPalikat() {
        while (seuraavatPalikat.size() < 4) {
            luoPalikka();
        }
    }

    private Tetromino annaSatunnainenTetromino() {
        Tetromino kandidaatti = null;
        for (int i = 0; i < 4; i++) {
            int satunnaisluku = random.nextInt(7);
            kandidaatti = tetrominoSet[satunnaisluku];
            if (!palikkaHistoria.contains(kandidaatti)) {
                break;
            }
        }
        palikkaHistoria.remove(0);
        palikkaHistoria.add(kandidaatti);
        return kandidaatti;
    }

    public void paivita() {
        if (gameover) {
            toteutaOhjausGameover();
        } else {
            if (!paused) {
                frame++;
                paivitaFysiikka();
                kasvataKelloa();
                nostaVaikeustaso();
            }
            toteutaOhjaus();

            if (tarkistaRivit) {
                poistaTaydetRivitKetjureaktioilla();
                tarkistaRivit = false;
            }
            paivitaAikayksikko();
        }
    }
    
    public void nostaVaikeustaso() {
        if (frame >= 300) {
            frame = 0;
            vaikeustaso++;
            paivitaAikayksikko();
        }
    }

    public void paivitaFysiikka() {
        if (tormayslogiikka.onkoPalikallaAlusta(nykyinenPalikka)) {
            liukuAikaPaalla = true;
        } else {
            liukuAikaPaalla = false;
        }

        if (tormayslogiikka.onkoPalikkaSeinanSisalla(nykyinenPalikka)) {
            gameover = true;
            gameover();
        }

        toteutaFysiikka();

        luoVarjopalikka();
    }

    private void toteutaFysiikka() {
        if (pudota) {
            if (!liukuAikaPaalla) {
                nykyinenPalikka.liiku(0, 1);
            }
            pudota = false;
        }
        if (lukitse) {
            lukitsePalikka();
        }
    }

    private void toteutaOhjaus() {
        if (!paused) {
            if (kaannaVasen) {
                kaannaVasen = false;
                tormayslogiikka.kaannaPalikkaVasemmalle(nykyinenPalikka);
            } else if (kaannaOikea) {
                kaannaOikea = false;
                tormayslogiikka.kaannaPalikkaOikealle(nykyinenPalikka);
            }
            if (pudotaKokonaan) {
                pudotaKokonaan = false;
                pudotaJaLukitsePalikka();
            } else {
                if (alas) {
                    this.alas = false;
                    if (liukuAikaPaalla) {
                        lukitsePalikka();
                    } else {
                        pudota = true;
                        nollaaAjastimet();
                    }
                }
                if (vasen) {
                    vasen = false;
                    liikutaVasen();
                } else if (oikea) {
                    oikea = false;
                    liikutaOikea();
                }
            }
        }
    }

    private void toteutaOhjausGameover() {
        if (yes) {
            yes = false;
            restart();
        }
        if (no) {
            no = false;
            quit();
        }
    }

    public void poistaTaydetRivitKetjureaktioilla() {
        int taysiaRiveja = poistot.poistaTaydetRivit();
        int rivejaPoistettu = 0;
        while (taysiaRiveja > 0) {
            rivejaPoistettu += taysiaRiveja;
            painovoima();
            taysiaRiveja = poistot.poistaTaydetRivit();
        }
        laskePisteetJaNostaVaikeustaso(rivejaPoistettu);
    }

    private void laskePisteetJaNostaVaikeustaso(int taysiaRiveja) {
        vaikeustaso++;
        if (taysiaRiveja > 0) {
            pisteet += taysiaRiveja * taysiaRiveja * 100;
            vaikeustaso += 1.5 * taysiaRiveja * taysiaRiveja;
        }
    }

    private void painovoima() {
        while (tormayslogiikka.pudotaPalikoitaYhdella()) {
            tormayslogiikka.pudotaPalikoitaYhdella();
        }
    }

    private void paivitaAikayksikko() {
        int muutos = vaikeustaso / 60;
        aikayksikko = 30 - muutos;
    }

    public int getAikayksikko() {
        return aikayksikko;
    }

    public KaantyvaPalikka getNykyinenPalikka() {
        return this.seuraavatPalikat.get(0);
    }

    public TormaysLogiikka getTormaysLogiikka() {
        return this.tormayslogiikka;
    }

    public void liikutaVasen() {
        if (!tormayslogiikka.onkoPalikkaVasSeinaaVasten(nykyinenPalikka)) {
            nykyinenPalikka.liiku(-1, 0);
        }
    }

    public void liikutaOikea() {
        if (!tormayslogiikka.onkoPalikkaOikSeinaaVasten(nykyinenPalikka)) {
            nykyinenPalikka.liiku(1, 0);
        }
    }

    public void vasemmalle() {
        this.vasen = true;
    }

    public void oikealle() {
        this.oikea = true;
    }

    public void pause() {
        if (!paused) {
            paused = true;
        } else {
            paused = false;
        }
    }

    public void yes() {
        if (gameover) {
            yes = true;
        }
    }

    public void no() {
        if (gameover) {
            no = true;
        }
    }

    public void pudotaPalikkaKokonaan(Palikka palikka) {
        while (pudotaPalikkaa(palikka)) {
            pudotaPalikkaa(palikka);
        }
    }

    public void pudotaPalikkaKokonaan() {
        pudotaPalikkaKokonaan(nykyinenPalikka);
    }

    public boolean pudotaPalikkaa(Palikka palikka) {
        return tormayslogiikka.yritaPudottaaLiikutettavaaPalikkaa(palikka);
    }

    public boolean pudotaPalikkaa() {
        return pudotaPalikkaa(nykyinenPalikka);
    }

    private void luoVarjopalikka() {
        varjoPalikka = new KaantyvaPalikka(nykyinenPalikka);
        pudotaPalikkaKokonaan(varjoPalikka);
    }

    public void kaannaVasen() {
        this.kaannaVasen = true;
    }

    public void kaannaOikea() {
        this.kaannaOikea = true;
    }

    public void alas() {
        this.alas = true;
    }

    public void pudotaKokonaan() {
        this.pudotaKokonaan = true;
    }

    public void pudota() {
        this.pudota = true;
    }

    public void lukitse() {
        this.lukitse = true;
    }

    private void pudotaNykPalikkaa() {
        if (!tormayslogiikka.onkoPalikallaAlusta(nykyinenPalikka)) {
            nykyinenPalikka.liiku(0, 1);
            nollaaAjastimet();
        } else {
            lukitsePalikka();
        }
    }

    public void pudotaJaLukitsePalikka() {
        nykyinenPalikka = varjoPalikka;
        lukitsePalikka();
    }

    public void lukitsePalikkaJosAlusta() {
        if (tormayslogiikka.onkoPalikallaAlusta(varjoPalikka)) {
            lukitsePalikka();
        }
    }

    public void lukitsePalikka() {
        lukitse = false;
        pelilauta.lisaaLiitettyPalikka(nykyinenPalikka);
        nollaaAjastimet();
        seuraavaPalikka();
        tarkistaRivit = true;
        liukuAikaPaalla = false;
        poistaTaydetRivitKetjureaktioilla();
    }

    private void seuraavaPalikka() {
        seuraavatPalikat.remove(0);
        luoPalikka();
        asetaSeuraavat();
        nykyinenPalikka = seuraavatPalikat.get(0);
    }

    private void asetaSeuraavat() {
        for (int i = 0; i < 4; i++) {
            KaantyvaPalikka pl = seuraavatPalikat.get(i);
            if (i == 0) {
                pl.setXpos(3);
                pl.setYpos(0);
                if (kaannaVasen) {
                    tormayslogiikka.kaannaPalikkaVasemmalle(pl);
                } else if (kaannaOikea) {
                    tormayslogiikka.kaannaPalikkaOikealle(pl);
                }
            } else if (i == 1) {
                pl.setXpos(3);
                pl.setYpos(-3);
            } else {
                pl.setXpos(11);
                pl.setYpos((i - 1) * 3);
            }
        }
    }

    private void nollaaOhjaimet() {
        this.vasen = false;
        this.oikea = false;
        this.alas = false;
        this.pudota = false;
        this.pudotaKokonaan = false;
        this.lukitse = false;
        this.kaannaVasen = false;
        this.kaannaOikea = false;
        this.no = false;
        this.yes = false;
    }

    private void kasvataKelloa() {
        kello.kasvata();
    }

    public String getAika() {
        return this.kello.toString();
    }

    public boolean onkoPaalla() {
        return this.paalla;
    }

    public boolean liukuAikaPaalla() {
        return this.liukuAikaPaalla;
    }

    public void gameover() {
//        paalla = false;
        nollaaOhjaimet();
    }

    public void restart() {
        paalla = false;
        nollaaAjastimet();
        poistot.poistaKaikki();
        nykyinenPalikka = null;
        this.aikayksikko = 20;
        this.pisteet = 0;
        this.vaikeustaso = 0;
        this.liukuAikaPaalla = false;
        nollaaOhjaimet();
        kello.reset();
        this.seuraavatPalikat.clear();
        this.palikkaHistoria.clear();
        alustaPalikkaHistoria();
        luoPalikat();
        asetaSeuraavat();
        this.nykyinenPalikka = seuraavatPalikat.get(0);
        luoVarjopalikka();
        pudota = false;
        lukitse = false;
        gameover = false;
        paalla = true;
        Thread.yield();
        aloitaPeliLoop();

    }

    private void quit() {
        System.exit(0);
    }

    public ArrayList<KaantyvaPalikka> getSeuraavatPalikat() {
        return this.seuraavatPalikat;
    }

    public Pelilauta getPelilauta() {
        return this.pelilauta;
    }

    public int getPisteet() {
        return this.pisteet;
    }

    public int getVaikeustaso() {
        return this.vaikeustaso;
    }

    public KaantyvaPalikka getVarjopalikka() {
        return this.varjoPalikka;
    }

    public boolean getPaused() {
        return this.paused;
    }

    public PeliLoop2 getPeliloop() {
        return this.peliloop;
    }

    public boolean getGameover() {
        return this.gameover;
    }

    public void nollaaAjastimet() {
        peliloop.nollaaAjastimet();
    }
}
