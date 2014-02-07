package tetris.pelilogiikka;

import java.util.ArrayList;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Vari;

public class TormaysLogiikka {

    private Pelilauta pelilauta;
    private PoistoOperaatiot poistot;

    public TormaysLogiikka(Pelilauta pelilauta, PoistoOperaatiot poistot) {
        this.pelilauta = pelilauta;
        this.poistot = poistot;
    }

    //tarkistaa onko palikassa pistetta joka tormaa seinaan jos siirtyma toteutetaan
    public boolean onkoPalikkaVasten(Palikka palikka, int xSiirtyma, int ySiirtyma) {
        int x = palikka.getXpos();
        int y = palikka.getYpos();
        ArrayList<int[]> muoto = palikka.getMuoto();
        for (int i = 0; i < muoto.size(); i++) {
            if (pelilauta.tarkistaPiste(x + muoto.get(i)[0] + xSiirtyma, y + muoto.get(i)[1] + ySiirtyma)) {
                return true;
            }
        }
        return false;
    }

    public boolean onkoPalikallaAlusta(Palikka palikka) {
        return onkoPalikkaVasten(palikka, 0, 1);
    }

    public boolean onkoPalikkaVasSeinaaVasten(Palikka palikka) {
        return onkoPalikkaVasten(palikka, -1, 0);
    }

    public boolean onkoPalikkaOikSeinaaVasten(Palikka palikka) {
        return onkoPalikkaVasten(palikka, 1, 0);
    }

    public boolean onkoPalikkaSeinanSisalla(Palikka palikka) {
        return onkoPalikkaVasten(palikka, 0, 0);
    }
    
    public boolean yritaPudottaaLiikutettavaaPalikkaa(Palikka palikka) {
        if(!onkoPalikallaAlusta(palikka)) {
            palikka.liiku(0, 1);
            return true;
        }
        return false;
    }

    // pudottaa kaikkia palikoita joita voi pudottaa yhdella koordinaatilla
    public boolean pudotaPalikoitaYhdella() {
        boolean pudonneita = false;
        ArrayList<Palikka> putoavatPalikat = new ArrayList<>();
        for (Palikka palikka : this.pelilauta.getPalikat()) {
            pelilauta.irrotaPalikka(palikka);
            if (!onkoPalikallaAlusta(palikka)) {
                putoavatPalikat.add(palikka);
                pudonneita = true;
            }
            pelilauta.liitaPalikka(palikka);
        }
        for (Palikka palikka : putoavatPalikat) {
            pelilauta.irrotaPalikka(palikka);
            palikka.liiku(0, 1);
            pelilauta.liitaPalikka(palikka);
        }
        return pudonneita;
    }
    
    public void pudotaLiikutettavaPalikkaKokonaan(Palikka palikka) {
        while(!onkoPalikallaAlusta(palikka)) {
            palikka.liiku(0, 1);
        }
        pelilauta.liitaPalikka(palikka);
    } 

    public void poistaPalikka(Palikka poistettava) {
        this.poistot.poistaPalikka(poistettava);
    }

    public boolean kaannaPalikka(KaantyvaPalikka palikka, int suunta) { // 0 = vasen, 1 = oikea
        if (palikka.getVari() == Vari.YELLOW) {                         // palikka on O-palikka eli ei voi kaantya
            return false;
        }

        KaantyvaPalikka uusiPalikka = kopioiPalikka(palikka);
        if (suunta == 0) {
            uusiPalikka.kaannaVasemmalle();
        } else {
            uusiPalikka.kaannaOikealle();
        }
        if (kaannosOnnistuu(uusiPalikka)) {
            palikka.setXpos(uusiPalikka.getXpos());
            palikka.setYpos(uusiPalikka.getYpos());
            palikka.setMuoto(uusiPalikka.getMuoto());
            palikka.setRotaatio(uusiPalikka.getRotaatio());
            return true;
        } else {
            return false;
        }

    }

    // tarkistaa onnistuuko palikan kaannos.
//    lisatty ominaisuus jossa palikkaa 'potkaistaan' sivulle jos on seinan vieressa
    private boolean kaannosOnnistuu(KaantyvaPalikka palikka) {
        if (onkoPalikkaSeinanSisalla(palikka)) {
            palikka.liiku(1, 0);                                  // kokeillaan toimiiko kaannos jos siirretaan yhden oikealle
            if (onkoPalikkaSeinanSisalla(palikka)) {
                palikka.liiku(-2, 0);                             // kokeillaan toimiiko kaannos jos siirretaan yhden vasemmalle
                if (onkoPalikkaSeinanSisalla(palikka)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean kaannaPalikkaVasemmalle(KaantyvaPalikka palikka) {
        return kaannaPalikka(palikka, 0);
    }

    public boolean kaannaPalikkaOikealle(KaantyvaPalikka palikka) {
        return kaannaPalikka(palikka, 1);
    }

    public KaantyvaPalikka kopioiPalikka(KaantyvaPalikka kop) {
        KaantyvaPalikka kopio = new KaantyvaPalikka(kop);
        return kopio;
    }

    public Palikka kopioiPalikka(Palikka kop) {
        Palikka kopio = new Palikka(kop.getMuoto(), kop.getXpos(), kop.getYpos(), kop.getVari());
        return kopio;
    }
}
