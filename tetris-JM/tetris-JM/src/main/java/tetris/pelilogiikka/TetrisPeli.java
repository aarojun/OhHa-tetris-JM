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

/**
 * Tetris-pelin logiikan ydin. Toteuttaa pelikohtaisen logiikan ja fysiikan paivityksen. 
 * Pelille ja kayttoliittymalle lahetetaan paivityksia pelin luomassa peliloopissa.
 * Luokkaa pitaa viela jakaa osiin.
 * @author zaarock
 */
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

    /**
     * Alustaa pelin tilan ennen kaynnistamista.
     */
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
        this.peliloop = new PeliLoop(this);
    }

    /**
     * Palauttaa kaikki peliin liittyvat arvot alkutilaansa.
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
     * Palauttaa kaikki peliin liittyvat booleanit alkutilaansa.
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
        paalla = true;
    }

    /**
     * Asettaa pelille graafisen kayttoliittyman jota peliloop paivittaa.
     * @param paivitettava graafinen kayttoliittyma
     */
    public void setPaivitettava(Paivitettava paivitettava) {
        peliloop.setPaivitettava(paivitettava);
    }

    /**
     * Kaynnistaa pelin.
     */
    public void start() {
        peliloop.run();
    }

    /**
     * Aloittaa pelin perusmoodissa.
     */
    private void startNormalMode() {
        this.perusAikayksikko = 20;
        paused = false;
    }

    /**
     * Aloittaa pelin 'death' moodissa.
     */
    private void startDeathMode() {
        this.perusAikayksikko = -20;
        this.vaikeustaso = 1000;
        paused = false;
    }

    /**
     * Luo uuden palikan joka lisataan seuraaviin palikoihin.
     */
    private void luoPalikka() {
        KaantyvaPalikka uusiPalikka = new KaantyvaPalikka(annaSatunnainenTetromino());
        uusiPalikka.setXpos(3);
        uusiPalikka.setYpos(-10);
        seuraavatPalikat.add(uusiPalikka);
    }

    /**
     * Luo peliin seuraavia palikoita kunnes niita on nelja.
     */
    private void luoPalikat() {
        while (seuraavatPalikat.size() < 4) {
            luoPalikka();
        }
    }

    /**
     * Palauttaa semi-satunnaisen tetrominon. 
     * Tetromino - palikat joita ei ole valittu moneen vuoroon ovat todennakoisempia.
     * @return uusi satunnainen Tetromino
     */
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
    
    /**
     * Luo alustavan historian valituista palikoista.
     * Vahentaa palikoiden Z ja S todennakoisyytta pelin alussa.
     */
    private void alustaPalikkaHistoria() {
        palikkaHistoria.add(Tetromino.Z);
        palikkaHistoria.add(Tetromino.S);
        palikkaHistoria.add(Tetromino.Z);
        palikkaHistoria.add(Tetromino.S);
    }

    /**
     * Paivittaa pelin tilan.
     */
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

    /**
     * Paivittaa pelin fysiikan tilan.
     */
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
        if (!vaihtoAika) {
            luoVarjopalikka();
        }
        toteutaFysiikka();
    }

    /**
     * Toteuttaa pelin fysiikan aiheuttamat palikkojen liikkeet.
     */
    private void toteutaFysiikka() {
        if (lukitse && palikallaOnAlusta) {
            lukitsePalikka();
        } else if (aikayksikko < 0 && !palikallaOnAlusta) {
            pudotaPalikkaaNegAikayksikko();
            pudota = false;
            alas = false;
        } else if (pudota) {
            if (!palikallaOnAlusta) {
                nykyinenPalikka.liiku(0, 1);
                nollaaAjastimet();
            }
            pudota = false;
            alas = false;
        }
    }

    /**
     * Toteuttaa pelin ohjauksen normaalitilassa.
     * Pudotuskomennot nostavat frekvenssi-mittaria eli antavat pistebonuksia.
     */
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

    /**
     * Toteuttaa pelin ohjauksen vuorojen valisena aikana.
     */
    private void toteutaOhjausVaihtoaika() {
        if (kaannaVasen) {
            kaannaVasen = true;
            kaannaOikea = false;
        } else if (kaannaOikea) {
            kaannaOikea = true;
            kaannaVasen = false;
        }
        if (vasen) {
            vasen = true;
            oikea = false;
        } else if (oikea) {
            oikea = true;
            vasen = false;
        }
    }

    /**
     * Toteuttaa pelin ohjauksen gameover -tilassa.
     */
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
    
    /**
     * Lukitsee nykyisen palikkaan pelilautaan, ja tarkistaa sen aiheuttamat ketjureaktiot.
     * Aloittaa vuorojen valisen vaihto-ajan.
     */
    private void lukitsePalikka() {
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

    /**
     * Poistaa pelilaudalle muodostuneet taydet rivit. Jatkaa tata jatkuvasti kunnes painovoiman aiheuttamat ketjureaktiot paattyvat.
     */
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

    /**
     * Laskee palikan asetuksesta seuraavat pisteet ja vaikeustaseen noston
     * @param taysiaRiveja maara riveja jota vuorolla poistettiin
     */
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

    /**
     * Pudottaa kaikkia pelilaudan palikoita kunnes niilla kaikilla on alusta.
     */
    private void painovoima() {
        while (tormayslogiikka.pudotaPalikoitaYhdella()) {
            tormayslogiikka.pudotaPalikoitaYhdella();
        }
    }

    /**
     * Paivittaa pelin aikayksikon perustuen pelin vaikeustasoon.
     */
    private void paivitaAikayksikko() {
        int muutos = vaikeustaso / 50;
        aikayksikko = perusAikayksikko - muutos;
    }

    /**
     * Palauttaa pelin nykyisen aikayksikon jota kaytetaan painovoiman paivittamiseen
     * @return aikayksikko paivityksia per painovoiman toteutus.
     */
    public int getAikayksikko() {
        return aikayksikko;
    }

    /**
     * Liikuttaa nykyista palikkaa vasemmalle jos se ei tormaa seinaan.
     */
    public void liikutaVasen() {
        if (!tormayslogiikka.onkoPalikkaVasSeinaaVasten(nykyinenPalikka)) {
            nykyinenPalikka.liiku(-1, 0);
        }
    }

    /**
     * Liikuttaa nykyista palikkaa oikealle jos se ei tormaa seinaan.
     */
    public void liikutaOikea() {
        if (!tormayslogiikka.onkoPalikkaOikSeinaaVasten(nykyinenPalikka)) {
            nykyinenPalikka.liiku(1, 0);
        }
    }

    /**
     * Pudottaa annettua palikkaa kunnes se tormaa alustaan.
     * @param palikka
     */
    public void pudotaPalikkaKokonaan(Palikka palikka) {
        while (tormayslogiikka.yritaPudottaaPalikkaa(palikka)) {
            tormayslogiikka.yritaPudottaaPalikkaa(palikka);
        }
    }

    /**
     * Toteuttaa painovoiman kun pelin aikayksikko on negatiivinen. 
     * Talloin palikkaa pudotetaan yhden ruudun jokaista negatiivista lukua kohden.
     */
    private void pudotaPalikkaaNegAikayksikko() {
        int i = -aikayksikko;
        while (pudotaPalikkaa() && i > 0) {
            pudotaPalikkaa();
            i--;
        }
        nollaaAjastimet();
    }

    /**
     * Pudottaa nykyista palikkaa kunnes se tormaa alustaan.
     */
    public void pudotaPalikkaKokonaan() {
        pudotaPalikkaKokonaan(nykyinenPalikka);
    }

    /**
     * Pudottaa annettua palikkaa yhdella ruudulla jos silla ei ole alustaa.
     * @param palikka pudotettava palikka
     * @return palikkaa pudotettiin eika silla ollut alustaa.
     */
    public boolean pudotaPalikkaa(Palikka palikka) {
        return tormayslogiikka.yritaPudottaaPalikkaa(palikka);
    }

    /**
     * Pudottaa nykyista palikkaa yhdella ruudulla jos silla on alusta.
     * @return palikkaa pudotettiin eika silla ollut alustaa.
     */
    public boolean pudotaPalikkaa() {
        return pudotaPalikkaa(nykyinenPalikka);
    }

    /**
     * Paivittaa nykyisen palikan varjon, joka on kopio nykyisesta palikasta joka on pudotettu alas.
     */
    private void luoVarjopalikka() {
        varjoPalikka = new KaantyvaPalikka(nykyinenPalikka);
        pudotaPalikkaKokonaan(varjoPalikka);
    }

    /**
     * Pudottaa nykyisen palikan mahdollisimman alas ja lukitsee sen paikalleen.
     */
    private void pudotaJaLukitsePalikka() {
        nykyinenPalikka = varjoPalikka;
        seuraavatPalikat.set(0, varjoPalikka);
        lukitsePalikka();
    }

    /**
     * Tarkistaa onko pelin nykyisella palikalla alusta.
     * @return onko nykyisella palikalla alusta
     */
    private boolean palikallaAlusta() {
        return tormayslogiikka.onkoPalikallaAlusta(nykyinenPalikka);
    }

    /**
     * Lukitsee pelin nykyisen palikan jos silla on alusta pelilaudassa.
     */
    public void lukitsePalikkaJosAlusta() {
        if (tormayslogiikka.onkoPalikallaAlusta(varjoPalikka)) {
            lukitsePalikka();
        }
    }

    /**
     * Aloittaa seuraavan pelivuoron (seuraava palikka tulee ohjattavaksi).
     */
    public void seuraavaVuoro() {
        this.vaihtoAika = false;
        vuoro++;
        seuraavaPalikkaOhjaus();
        lautaPaivita = true;
    }

    /**
     * Aloittaa pelin vuorojen valisen vaihtoajan.
     */
    private void aloitaVaihtoAika() {
        this.vaihtoAika = true;
        this.fxPalikka = nykyinenPalikka;
        this.nykyinenPalikka = seuraavatPalikat.get(1);
        luoVarjopalikka();
        seuraavatPalikat.remove(0);
        luoPalikka();
        asetaSeuraavat();
    }

    /**
     * Lopettaa pelin vuorojen valisen vaihtoajan.
     */
    public void lopetaVaihtoAika() {
        this.vaihtoAika = false;
    }

    /**
     * Toteuttaa bufferoidun ohjauksen seuraavalle palikalle ennen kuin se siirretaan peliin.
     */
    private void seuraavaPalikkaOhjaus() {
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

    /**
     * Paivittaa seuraavien palikoiden sijainnin.
     */
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

    /**
     * Nollaa pelin ohjaus-tiedot.
     */
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

    /**
     * Lopettaa pelin ja toteuttaa siihen liittyvat operaatiot.
     */
    public void gameover() {
        lautaPaivita = false;
        gameover = true;
        nollaaOhjaimet();
        paivitaHighScore();
        muutaPalikatHarmaiksi();
        lautaPaivita = true;
    }
    
    /**
     * Lopettaa pelin koska aika on loppunut.
     */
    public void timeover() {
        timeover = true;
        gameover();
    }

    /**
     * Muuttaa kaikki palikat harmaiksi visuaalisena efektina.
     */
    private void muutaPalikatHarmaiksi() {
        for (Palikka pl : pelilauta.getPalikat()) {
            pl.setVari(Vari.GRAY);
        }
    }

    /**
     * Restarttaa pelin alkutilaansa ilman luokkien uudelleenluomista.
     */
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

    /**
     * Sammuttaa pelin ja ohjelman.
     */
    private void quit() {
        System.out.println("quitting game");
        System.exit(0);
    }

    /**
     * Laittaa pelin pause -tilaan tai pois siita (pause mahdollisesti poistetaan myohemmin)
     */
    public void pause() {
        if (!paused && !gameover) {
            paused = true;
        } else {
            if (!startMenu) {
                paused = false;
            }
        }
    }

    /**
     * Toteuttaa myonteisen valinnan pelin valikoissa.
     */
    public void yes() {
        if (gameover) {
            yes = true;
        } else if (startMenu) {
            startMenu = false;
            startNormalMode();
        }
    }

    /**
     * Toteuttaa kielteisen valinnan pelin valikoissa.
     */
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
    
    /**
     * Nykyista palikkaa yritetaan liikuttaa vasemalle seuraavalla paivityksella.
     */
    public void vasemmalle() {
        this.vasen = true;
    }

    /**
     * Nykyista palikkaa yritetaan liikuttaa oikealle seuraavalla paivityksella.
     */
    public void oikealle() {
        this.oikea = true;
    }

    /**
     * Nykyista palikkaa yritetaan kaantaa vasemmalle seuraavalla paivityksella.
     */
    public void kaannaVasen() {
        this.kaannaVasen = true;
    }

    /**
     * Nykyista palikkaa yritetaan kaantaa oikealle seuraavalla paivityksella.
     */
    public void kaannaOikea() {
        this.kaannaOikea = true;
    }

    /**
     * Nykyista palikkaa yritetaan pudottaa seuraavalla paivityksella.
     */
    public void alas() {
        this.alas = true;
    }

    /**
     * Nykyinen palikka pudotetaan ja lukitaan seuraavalla paivityksella.
     */
    public void pudotaKokonaan() {
        this.pudotaKokonaan = true;
    }

    /**
     * Nykyinen palikka yritetaan pudottaa seuraavalla paivityksella.
     */
    public void pudota() {
        this.pudota = true;
    }

    /**
     * Nykyinen palikka lukitaan seuraavalla paivityksella.
     */
    public void lukitse() {
        this.lukitse = true;
    }

    /**
     * Paivittaa parhaan pistemaaran tiedot jos pelaaja on saavuttanut uuden ennatyksen.
     */
    private void paivitaHighScore() {
        if (pisteet > highScore) {
            highScore = pisteet;
            scoreTallennus.tallennaHighscore(pisteet);
        }
    }
    
    /**
     * Nollaa peliloopin yllapitamat painovoima-ajastimet.
     */
    public void nollaaAjastimet() {
        peliloop.nollaaAjastimet();
    }
    
    @Override
    public KaantyvaPalikka getNykyinenPalikka() {
        return this.seuraavatPalikat.get(0);
    }
    
    @Override
    public String getAika() {
        return this.kello.toString();
    }

    @Override
    public boolean onkoPaalla() {
        return this.paalla;
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

    @Override
    public SyotteenLukija getLukija() {
        return this.liikkeidenLukija;
    }
    
    @Override
    public boolean getTimeOver() {
        return this.timeover;
    }
}
