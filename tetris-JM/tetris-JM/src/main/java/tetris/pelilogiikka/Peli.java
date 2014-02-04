package tetris.pelilogiikka;

import java.util.ArrayList;
import tetris.gui.Paivitettava;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Tetromino;

public class Peli {

    private Pelilauta pelilauta;
    private ArrayList<KaantyvaPalikka> seuraavatPalikat;
    private ArrayList<Palikka> palikat;
    private double aikayksikko;
    private int vaikeustaso;
    private int pisteet;
    private TormaysLogiikka tormayslogiikka;
    private PoistoOperaatiot poistot;
    private Paivitettava paivitettava;
    
    private boolean painovoimaPaalla;

    public Peli(Paivitettava paivitettava) {
        this.pelilauta = new Pelilauta(10, 20);
        this.seuraavatPalikat = new ArrayList<>();
        this.palikat = pelilauta.getPalikat();

        this.aikayksikko = 1;
        this.vaikeustaso = 0;
        this.pisteet = 0;

        this.poistot = new PoistoOperaatiot(pelilauta);
        this.tormayslogiikka = new TormaysLogiikka(pelilauta, poistot);

        this.paivitettava = paivitettava;

        luoPalikat();
    }

    private void luoPalikka() {
        KaantyvaPalikka uusiPalikka = new KaantyvaPalikka(Tetromino.O);
        seuraavatPalikat.add(uusiPalikka);
        palikat.add(uusiPalikka);
    }

    private void luoPalikat() {
        while (seuraavatPalikat.size() < 5) {
            luoPalikka();
        }
    }

    private void liikutaPalikkaa() {
        KaantyvaPalikka liikutettava = seuraavatPalikat.get(0);

        Ajastin liutusAika = new Ajastin(10 * aikayksikko);
        Ajastin painovoimaAjastin = new Ajastin(1 * aikayksikko);

        while (!liutusAika.loppunut()) {
            boolean onkoAlusta = tormayslogiikka.onkoPalikallaAlusta(liikutettava);
            if (!liutusAika.onkoPaalla() && onkoAlusta) {
                liutusAika.kaynnista();
            } else if (liutusAika.onkoPaalla() && !onkoAlusta) {
                liutusAika.resetoiAjastin();
            }
            // implementoi että jos kayttaja painaa ylos tai alas nuolta --> break;
        }

    }

    private void paivitaVaikeustaso() {
    }
    
    public double getAikayksikko() {
        return aikayksikko;
    }
    
    public KaantyvaPalikka getNykyinenPalikka() {
        return this.seuraavatPalikat.get(0);
    }
    
    public TormaysLogiikka getTormaysLogiikka() {
        return this.tormayslogiikka;
    }
    
    public boolean painovoimaPaalla() {
        return this.painovoimaPaalla;
    }
}
