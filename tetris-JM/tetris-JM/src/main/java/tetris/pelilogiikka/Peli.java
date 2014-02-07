package tetris.pelilogiikka;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import tetris.gui.Kayttoliittyma;
import tetris.gui.Paivitettava;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Tetromino;

public class Peli {

    private Kayttoliittyma kayttoliittyma;
    private PeliLoop peliloop;
    private Timer ajastin;
    private Kello kello;
    private Random random;
    private Pelilauta pelilauta;
    private ArrayList<KaantyvaPalikka> seuraavatPalikat;
    private ArrayList<Tetromino> palikkaHistoria;
    private KaantyvaPalikka nykyinenPalikka;
    private KaantyvaPalikka varjoPalikka;
    private Suunta suunta;
    private Painike painike;
    private int aikayksikko;
    private int vaikeustaso;
    private int pisteet;
    private TormaysLogiikka tormayslogiikka;
    private PoistoOperaatiot poistot;
    private Paivitettava gui;
    private boolean paalla;
    private boolean paused;
    private boolean gameover;
    private boolean tarkistaRivit;
    private boolean paivitaOhjaus;
    private boolean liukuAikaPaalla;
    private boolean palikallaVasenSeina;
    private boolean palikallaOikeaSeina;
    private final Tetromino[] tetrominoSet = new Tetromino[]{Tetromino.I, Tetromino.J,
        Tetromino.L, Tetromino.O, Tetromino.S, Tetromino.T, Tetromino.Z};

    public Peli() {
        this.ajastin = new Timer();
        this.pelilauta = new Pelilauta(10, 20);
        this.seuraavatPalikat = new ArrayList<>();
        this.palikkaHistoria = new ArrayList<>();

        this.aikayksikko = 20;
        this.vaikeustaso = 0;
        this.pisteet = 0;

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
        this.paivitaOhjaus = false;
        this.paused = false;
        this.gameover = false;
        this.liukuAikaPaalla = false;
        this.palikallaVasenSeina = false;
        this.palikallaOikeaSeina = false;
    }

    public void setPaivitettava(Paivitettava paivitettava) {
        this.gui = paivitettava;
    }
    
    public void setKayttoliittyma(Kayttoliittyma kayttoliittyma) {
        this.kayttoliittyma = kayttoliittyma;
    }

    public void start() {
        aloitaPeliLoop();
    }

    private void aloitaPeliLoop() {
        this.peliloop = new PeliLoop(this, gui);
        ajastin.schedule(peliloop,
                0,
                (long) (1000 / 60));
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
            if (paivitaOhjaus) {
                toteutaOhjausGameover();
            }
        } else {
            if (!paused) {
                paivitaFysiikka();
                kasvataKelloa();
            }

            if (paivitaOhjaus) {
                toteutaOhjaus();
                nollaaOhjaimet();
                paivitaOhjaus = false;
            }

            if (tarkistaRivit) {
                poistaTaydetRivitKetjureaktioilla();
                tarkistaRivit = false;
            }

            paivitaAikayksikko();

        }

        gui.paivita();


    }

    public void paivitaFysiikka() {
        if (tormayslogiikka.onkoPalikallaAlusta(nykyinenPalikka)) {
            liukuAikaPaalla = true;
        } else {
            liukuAikaPaalla = false;
        }

        if (tormayslogiikka.onkoPalikkaOikSeinaaVasten(nykyinenPalikka)) {
            palikallaOikeaSeina = true;
        } else {
            palikallaOikeaSeina = false;
        }

        if (tormayslogiikka.onkoPalikkaVasSeinaaVasten(nykyinenPalikka)) {
            palikallaVasenSeina = true;
        } else {
            palikallaVasenSeina = false;
        }

        if (tormayslogiikka.onkoPalikkaSeinanSisalla(nykyinenPalikka)) {
            gameover = true;
            gameover();
        }

        luoVarjopalikka();
    }

    private void toteutaOhjaus() {
        if (painike == Painike.P) {
            pause();
            return;
        }
        if (!paused) {
            if (painike == Painike.Z) {
                kaannaVasen();
            } else if (painike == Painike.X) {
                kaannaOikea();
            }
            if (suunta == Suunta.ALAS) {
                if (liukuAikaPaalla) {
                    lukitsePalikkaJosAlusta();
                } else {
                    pudotaPalikkaa();
                }
                nollaaAjastimet();
            } else if (suunta == Suunta.YLOS) {
                pudotaJaLukitsePalikka();
                nollaaAjastimet();
            } else if (suunta == Suunta.VASEN) {
                liikutaVasen();
            } else if (suunta == Suunta.OIKEA) {
                liikutaOikea();
            }
        }
    }

    private void toteutaOhjausGameover() {
        if (painike == Painike.Y) {
            restart();
            gameover = false;
        }
        if (painike == Painike.N) {
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
            vaikeustaso += 2 * taysiaRiveja * taysiaRiveja;
        }
    }

    private void painovoima() {
        while (tormayslogiikka.pudotaPalikoitaYhdella()) {
            tormayslogiikka.pudotaPalikoitaYhdella();
        }
    }

    private void paivitaAikayksikko() {
        int muutos = vaikeustaso / 50;
        aikayksikko = 20 - muutos;
    }
    
    private void quit() {
        kayttoliittyma.quit();
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
        if (!palikallaVasenSeina) {
            nykyinenPalikka.liiku(-1, 0);
        }
    }

    public void liikutaOikea() {
        if (!palikallaOikeaSeina) {
            nykyinenPalikka.liiku(1, 0);
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
        tormayslogiikka.kaannaPalikkaVasemmalle(nykyinenPalikka);
    }

    public void kaannaOikea() {
        tormayslogiikka.kaannaPalikkaOikealle(nykyinenPalikka);
    }

    public void pudotaJaLukitsePalikka() {
        nykyinenPalikka = varjoPalikka;
        lukitsePalikka();

    }

    public void lukitsePalikkaJosAlusta() {
        if (liukuAikaPaalla) {
            lukitsePalikka();
        }
    }

    public void lukitsePalikka() {
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
            Palikka pl = seuraavatPalikat.get(i);
            if (i == 0) {
                pl.setXpos(3);
                pl.setYpos(0);
            } else {
                pl.setXpos(11);
                pl.setYpos(2 + (i - 1) * 3);
            }
        }
    }

    public void setSuunta(Suunta suunta) {
        this.suunta = suunta;
    }

    public Suunta getSuunta() {
        return this.suunta;
    }

    public void setPainike(Painike painike) {
        this.painike = painike;
    }

    private void nollaaOhjaimet() {
        this.suunta = Suunta.NEUTRAL;
        this.painike = Painike.NEUTRAL;
    }

    public void setPaivitaOhjaus(boolean bool) {
        this.paivitaOhjaus = bool;
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

    public boolean palikallaVasenSeina() {
        return this.palikallaVasenSeina;
    }

    public boolean palikallaOikeaSeina() {
        return this.palikallaOikeaSeina;
    }

    public void gameover() {
//        paalla = false;
        nollaaOhjaimet();
    }

    public void restart() {
        this.aikayksikko = 20;
        this.pisteet = 0;
        this.vaikeustaso = 0;
        nollaaAjastimet();
        nollaaOhjaimet();
        kello.reset();
        poistot.poistaKaikki();
        this.seuraavatPalikat.clear();
        this.palikkaHistoria.clear();
        alustaPalikkaHistoria();
        luoPalikat();
        asetaSeuraavat();
        this.nykyinenPalikka = seuraavatPalikat.get(0);
        luoVarjopalikka();
        paalla = true;

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

    public KaantyvaPalikka getVarjopalikka() {
        return this.varjoPalikka;
    }

    public int getVaikeustaso() {
        return this.vaikeustaso;
    }

    public void pause() {
        if (!paused) {
            paused = true;
        } else {
            paused = false;
        }
    }

    public boolean getPaused() {
        return this.paused;
    }

    public PeliLoop getPeliloop() {
        return this.peliloop;
    }

    public int getFrame() {
        return this.peliloop.getFrame();
    }

    public boolean getGameover() {
        return this.gameover;
    }

    public void nollaaAjastimet() {
        peliloop.nollaaAjastimet();
    }

    public void resetoiAjastin() {
        ajastin.cancel();
    }
}
