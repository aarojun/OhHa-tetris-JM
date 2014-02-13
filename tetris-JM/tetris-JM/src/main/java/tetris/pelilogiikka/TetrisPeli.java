package tetris.pelilogiikka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import tetris.gui.Paivitettava;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Tetromino;
import tetris.objects.Vari;

public class TetrisPeli implements PeliRajapinta {

    private PeliLoop peliloop;
    private Kello kello;
    private Random random;
    private Pelilauta pelilauta;
    private LiikeToiminnat liikkeidenLukija;
    private ArrayList<KaantyvaPalikka> seuraavatPalikat;
    private ArrayList<Tetromino> palikkaHistoria;
    private KaantyvaPalikka nykyinenPalikka;
    private KaantyvaPalikka fxPalikka;
    private KaantyvaPalikka varjoPalikka;
    private TormaysLogiikka tormayslogiikka;
    private PoistoOperaatiot poistot;
    private Paivitettava gui;
    private HighscoreKasittelija scoreTallennus;
    private int perusAikayksikko;
    private int aikayksikko;
    private int vaikeustaso;
    private int vuoro;
    private int pisteet;
    private int highScore;
    private int combo;
    private double frekvenssi;
    private double frekMultiplier;
    private boolean startMenu;
    private boolean lautaPaivita;
    private boolean vaihtoAika;
    private boolean paalla;
    private boolean paused;
    private boolean gameover;
    private boolean timeover;
    private boolean palikallaOnAlusta;
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

    public TetrisPeli() {
        this.pelilauta = new Pelilauta(10, 23);
        this.seuraavatPalikat = new ArrayList<KaantyvaPalikka>();
        this.palikkaHistoria = new ArrayList<Tetromino>();

        this.scoreTallennus = new HighscoreKasittelija();

        initialisoiArvot();
        initialisoiBooleanit();

        this.poistot = new PoistoOperaatiot(pelilauta);
        this.tormayslogiikka = new TormaysLogiikka(pelilauta, poistot);

        this.random = new Random();
        this.kello = new Kello(8,this);

        this.liikkeidenLukija = new LiikeToiminnat(this);

        alustaPalikkaHistoria();
        luoPalikat();
        asetaSeuraavat();
        this.nykyinenPalikka = seuraavatPalikat.get(0);
        luoVarjopalikka();
        paalla = true;
    }

    /**
     * palauttaa kaikki peliin liittyvat arvot alkutilaansa
     */
    private void initialisoiArvot() {
        try {
            highScore = scoreTallennus.lataaHighscore();
        } catch (IOException ex) {
            highScore = 0;
        }
        perusAikayksikko = 20;
        aikayksikko = perusAikayksikko;
        frekvenssi = 0;
        vaikeustaso = 0;
        pisteet = 0;
        vuoro = 0;
        combo = 0;
        frekMultiplier = 1;
    }

    /**
     * palauttaa kaikki peliin liittyvat booleanit alkutilaansa
     */
    private void initialisoiBooleanit() {
        startMenu = true;
        lautaPaivita = true;
        vaihtoAika = false;
        paused = true;
        gameover = false;
        timeover = false;
        palikallaOnAlusta = false;
        lukitse = false;
        alas = false;
        pudota = false;
        vasen = false;
        oikea = false;
        pudotaKokonaan = false;
        kaannaVasen = false;
        kaannaOikea = false;
        yes = false;
        no = false;
    }

    public void setPaivitettava(Paivitettava paivitettava) {
        this.gui = paivitettava;
    }

    public void start() {
        this.peliloop = new PeliLoop(this, gui);
        peliloop.run();
    }

    public void startNormalMode() {
        this.perusAikayksikko = 20;
        paused = false;
    }

    public void startDeathMode() {
        this.perusAikayksikko = -20;
        this.vaikeustaso = 1000;
        paused = false;
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
        } else if (!paused) {
            kello.pienenna();
            if (vaihtoAika) {
                toteutaOhjausVaihtoaika();
            } else {
                paivitaFysiikka();
                toteutaOhjaus();
            }
            paivitaAikayksikko();
        }

    }

    private void paivitaFysiikka() {
        if (frekvenssi <= 1) {
            frekvenssi = 0;
        } else {
            frekvenssi -= 0.5;
        }
        frekMultiplier = 1+(frekvenssi / 30);
        if (tormayslogiikka.onkoPalikallaAlusta(nykyinenPalikka)) {
            palikallaOnAlusta = true;
        } else {
            palikallaOnAlusta = false;
        }
        if (aikayksikko < 0 && !palikallaOnAlusta) {
            pudotaPalikkaaNegAikayksikko();
            pudota = false;
        }
        if (!vaihtoAika) {
            luoVarjopalikka();
        }
        toteutaFysiikka();
    }

    private void toteutaFysiikka() {
        if (lukitse && palikallaOnAlusta) {
            lukitsePalikka();
        } else if (pudota) {
            if (!palikallaOnAlusta) {
                nykyinenPalikka.liiku(0, 1);
                nollaaAjastimet();
            }
            pudota = false;
            alas = false;
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
                frekvenssi += 20;
            } else {
                if (alas) {
                    this.alas = false;
                    if (palikallaAlusta()) {
                        lukitsePalikka();
                        frekvenssi += 20;
                    } else {
                        nykyinenPalikka.liiku(0, 1);
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

    private void toteutaOhjausVaihtoaika() {
        if (kaannaVasen) {
            kaannaVasen = true;
            kaannaOikea = false;
        } else if (kaannaOikea) {
            kaannaOikea = true;
            kaannaVasen = false;
        }
        if (vasen) {
            oikea = false;
        } else if (oikea) {
            vasen = false;
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

    private void poistaTaydetRivitKetjureaktioilla() {
        int taysiaRiveja = 1;
        int rivejaPoistettu = 0;
        while (taysiaRiveja > 0) {
            rivejaPoistettu += taysiaRiveja;
            painovoima();
            taysiaRiveja = poistot.poistaTaydetRivit();
        }
        if (rivejaPoistettu > 6) {
            rivejaPoistettu = 6;
        }
        laskePisteetJaNostaVaikeustaso(rivejaPoistettu);
    }

    private void laskePisteetJaNostaVaikeustaso(int taysiaRiveja) {
        vaikeustaso += taysiaRiveja * (1 + taysiaRiveja / 2) + (1+ frekMultiplier / 3);
        if (taysiaRiveja > 1) {
            combo++;
            frekvenssi += 40;
            pisteet += 10 * taysiaRiveja * (1 + taysiaRiveja / 2) * combo * (1 + (int) (vaikeustaso / 100)) * frekMultiplier;
        } else {
            combo = 0;
        }
        if (frekvenssi < 80) {
            frekvenssi += 40;
        } else {
            frekvenssi = 120;
        }
    }

    private void painovoima() {
        while (tormayslogiikka.pudotaPalikoitaYhdella()) {
            tormayslogiikka.pudotaPalikoitaYhdella();
        }
    }

    private void paivitaAikayksikko() {
        int muutos = vaikeustaso / 50;
        aikayksikko = perusAikayksikko - muutos;
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
            if (!startMenu) {
                paused = false;
            }
        }
    }

    public void yes() {
        if (gameover) {
            yes = true;
        } else if (startMenu) {
            startMenu = false;
            startNormalMode();
        }
    }

    public void no() {
        if (gameover) {
            no = true;
        } else if (startMenu) {
            startMenu = false;
            startDeathMode();
        }
    }

    @Override
    public void lautaPaivitetty() {
        this.lautaPaivita = false;
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

    public void pudotaPalikkaKokonaan(Palikka palikka) {
        while (pudotaPalikkaa(palikka)) {
            pudotaPalikkaa(palikka);
        }
    }

    private void pudotaPalikkaaNegAikayksikko() {
        int i = -aikayksikko;
        while (pudotaPalikkaa() && i > 0) {
            pudotaPalikkaa();
            i--;
        }
        nollaaAjastimet();
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

    public void pudotaJaLukitsePalikka() {
        nykyinenPalikka = varjoPalikka;
        seuraavatPalikat.set(0, varjoPalikka);
        lukitsePalikka();
    }

    private boolean palikallaAlusta() {
        return tormayslogiikka.onkoPalikallaAlusta(nykyinenPalikka);
    }

    public void lukitsePalikkaJosAlusta() {
        if (tormayslogiikka.onkoPalikallaAlusta(varjoPalikka)) {
            lukitsePalikka();
        }
    }

    public void lukitsePalikka() {
        lautaPaivita = false;
        nollaaOhjaimet();
        lukitse = false;
        if (nykyinenPalikka.getYpos() < 2) {
            gameover();
        }
        pelilauta.lisaaLiitettyPalikka(nykyinenPalikka);
        aloitaVaihtoAika();
        nollaaAjastimet();
        palikallaOnAlusta = false;
        poistaTaydetRivitKetjureaktioilla();
        lautaPaivita = true;
    }

    public void seuraavaVuoro() {
        this.vaihtoAika = false;
        vuoro++;
        seuraavaPalikka();
        lautaPaivita = true;
    }

    private void aloitaVaihtoAika() {
        this.vaihtoAika = true;
        this.fxPalikka = nykyinenPalikka;
        this.nykyinenPalikka = seuraavatPalikat.get(1);
        luoVarjopalikka();
        seuraavatPalikat.remove(0);
        luoPalikka();
        asetaSeuraavat();
    }

    public void lopetaVaihtoAika() {
        this.vaihtoAika = false;
    }

    private void seuraavaPalikka() {
        if (nykyinenPalikka.getTetromino() != Tetromino.O) {
            if (kaannaVasen) {
                kaannaVasen = false;
                nykyinenPalikka.kaannaVasemmalle();
            } else if (kaannaOikea) {
                kaannaOikea = false;
                nykyinenPalikka.kaannaOikealle();
            }
        }
        if (vasen) {
            liikutaVasen();
            vasen = false;
        } else if (oikea) {
            liikutaOikea();
            oikea = false;
        }

    }

    private void asetaSeuraavat() {
        for (int i = 0; i < 4; i++) {
            KaantyvaPalikka pl = seuraavatPalikat.get(i);
            if (i == 0) {

                pl.setXpos(3);
                pl.setYpos(0);
            } else if (i == 1) {
                pl.setXpos(3);
                pl.setYpos(0);
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

    @Override
    public String getAika() {
        return this.kello.toString();
    }

    public boolean onkoPaalla() {
        return this.paalla;
    }

    public boolean liukuAikaPaalla() {
        return this.palikallaOnAlusta;
    }

    public void gameover() {
        lautaPaivita = false;
        gameover = true;
        nollaaOhjaimet();
        paivitaHighScore();
        muutaPalikatHarmaiksi();
        lautaPaivita = true;
    }
    
    public void timeover() {
        timeover = true;
        gameover();
    }

    private void muutaPalikatHarmaiksi() {
        for (Palikka pl : pelilauta.getPalikat()) {
            pl.setVari(Vari.GRAY);
        }
    }

    private void restart() {
        paalla = false;
        nollaaAjastimet();
        kello.reset();
        poistot.poistaKaikki();
        this.seuraavatPalikat.clear();
        this.palikkaHistoria.clear();
        alustaPalikkaHistoria();
        luoPalikat();
        asetaSeuraavat();
        this.nykyinenPalikka = seuraavatPalikat.get(0);
        luoVarjopalikka();
        initialisoiBooleanit();
        initialisoiArvot();
        paalla = true;
        Thread.yield();
        peliloop.run();
        System.out.println("game restarted");
    }

    private void quit() {
        System.out.println("quitting game");
        System.exit(0);
    }

    private void paivitaHighScore() {
        if (pisteet > highScore) {
            highScore = pisteet;
            scoreTallennus.tallennaHighscore(pisteet);
        }
    }

    @Override
    public ArrayList<KaantyvaPalikka> getSeuraavatPalikat() {
        return this.seuraavatPalikat;
    }

    @Override
    public Pelilauta getPelilauta() {
        return this.pelilauta;
    }

    @Override
    public int getPisteet() {
        return this.pisteet;
    }

    @Override
    public int getHighScore() {
        return this.highScore;
    }

    @Override
    public int getVaikeustaso() {
        return this.vaikeustaso;
    }

    @Override
    public int getVuoro() {
        return this.vuoro;
    }

    @Override
    public KaantyvaPalikka getVarjopalikka() {
        return this.varjoPalikka;
    }

    @Override
    public Palikka getEfektiPalikka() {
        return this.fxPalikka;
    }

    @Override
    public boolean onkoVaihtoAika() {
        return this.vaihtoAika;
    }

    @Override
    public boolean getPaused() {
        return this.paused;
    }

    public PeliLoop getPeliloop() {
        return this.peliloop;
    }

    @Override
    public boolean getStartMenu() {
        return this.startMenu;
    }

    @Override
    public boolean getGameover() {
        return this.gameover;
    }

    @Override
    public boolean getLautaPaivita() {
        return this.lautaPaivita;
    }

    @Override
    public int getCombo() {
        return this.combo;
    }

    @Override
    public double getFrekMultiplier() {
        return this.frekMultiplier;
    }

    public void nollaaAjastimet() {
        peliloop.nollaaAjastimet();
    }

    @Override
    public LiikeToiminnat getLukija() {
        return this.liikkeidenLukija;
    }
    
    @Override
    public boolean getTimeOver() {
        return this.timeover;
    }
}
