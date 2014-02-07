package tetris.pelilogiikka;

import java.util.Timer;
import java.util.ArrayList;
import java.util.Random;
import tetris.gui.Paivitettava;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Tetromino;

public class Peli {
    
    private PeliLoop peliloop;
    private Timer ajastin;
    private Random random;
    private Pelilauta pelilauta;
    private ArrayList<KaantyvaPalikka> seuraavatPalikat;
    private ArrayList<Palikka> palikat;
    private ArrayList<Tetromino> palikkaHistoria;
    private KaantyvaPalikka nykyinenPalikka;
    private int aikayksikko;
    private int vaikeustaso;
    private int pisteet;
    private TormaysLogiikka tormayslogiikka;
    private PoistoOperaatiot poistot;
    private Paivitettava paivitettava;
    private boolean paalla;
    private boolean gameover;
    private boolean tarkistaRivit;
    private boolean painovoimaPaalla;
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
        this.palikat = pelilauta.getPalikat();

        this.aikayksikko = 20;
        this.vaikeustaso = 0;
        this.pisteet = 0;

        this.poistot = new PoistoOperaatiot(pelilauta);
        this.tormayslogiikka = new TormaysLogiikka(pelilauta, poistot);
        
        this.random = new Random();

        alustaPalikkaHistoria();
        luoPalikat();
        asetaSeuraavat();
        this.nykyinenPalikka = seuraavatPalikat.get(0);

        this.paalla = true;
        this.tarkistaRivit = false;
        this.gameover = false;
        this.painovoimaPaalla = true;
        this.liukuAikaPaalla = false;
        this.palikallaVasenSeina = false;
        this.palikallaOikeaSeina = false;
    }

    public void setPaivitettava(Paivitettava paivitettava) {
        this.paivitettava = paivitettava;
    }

    public void start() {
        aloitaPeliLoop();
    }

    private void aloitaPeliLoop() {
        this.peliloop = new PeliLoop(this, paivitettava);
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

        if (tarkistaRivit) {
            poistaTaydetRivitKetjureaktioilla();
            tarkistaRivit = false;
        }

        paivitaAikayksikko();
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
            vaikeustaso += 1 + taysiaRiveja * taysiaRiveja;
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

    public boolean pudotaAlas() {
        return tormayslogiikka.yritaPudottaaLiikutettavaaPalikkaa(nykyinenPalikka);
    }

    public void kaannaVasen() {
        tormayslogiikka.kaannaPalikkaVasemmalle(nykyinenPalikka);
    }

    public void kaannaOikea() {
        tormayslogiikka.kaannaPalikkaOikealle(nykyinenPalikka);
    }

    public void pudotaJaLukitsePalikka() {
        while (pudotaAlas()) {
            pudotaAlas();
        }
        lukitsePalikka();

    }

    public void lukitsePalikkaJosAlusta() {
        if (liukuAikaPaalla) {
            lukitsePalikka();
        }
    }

    public void lukitsePalikka() {
        pelilauta.lisaaLiitettyPalikka(nykyinenPalikka);
        tarkistaRivit = true;
        seuraavaPalikka();
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
            } else if (i == 1) {
                pl.setXpos(3);
                pl.setYpos(-3);
            } else {
                pl.setXpos(12);
                pl.setYpos((i - 2) * 5);
            }
        }
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

    public boolean painovoimaPaalla() {
        return this.painovoimaPaalla;
    }

    public void gameover() {
        paalla = false;
        resetoiAjastin();
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
    
    public void nollaaAjastimet() {
        peliloop.nollaaAjastimet();
    }

    public void resetoiAjastin() {
        ajastin.cancel();
    }
}
