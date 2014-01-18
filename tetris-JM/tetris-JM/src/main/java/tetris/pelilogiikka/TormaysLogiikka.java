package tetris.pelilogiikka;

import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;

public class TormaysLogiikka {

    private Pelilauta pelilauta;

    public TormaysLogiikka(Pelilauta pelilauta) {
        this.pelilauta = pelilauta;
    }

    public boolean onkoPalikkaVasten(Palikka palikka, int xSiirtyma, int ySiirtyma) {
        int x = palikka.getXpos();
        int y = palikka.getYpos();
        int[][] muoto = palikka.getMuoto();
        int i = 0;
        while (muoto[i] != null) {
            if (pelilauta.tarkistaPiste(x + muoto[i][0] + xSiirtyma, y + muoto[i][1] + ySiirtyma)) {
                return true;
            }
            i++;
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

    public void poistaPalikka(Palikka poistettava) {
        // ei implementoitu
    }

    public void asetaPalikka(Palikka asetettava) {
        int[][] pisteet = asetettava.getMuoto();
        int x = asetettava.getXpos();
        int y = asetettava.getYpos();
        
        int i = 0;
        while (pisteet[i] != null) {
            pelilauta.asetaPiste(x+pisteet[i][0], y+pisteet[i][1]);
        }
    }

    public boolean kaannaPalikka(KaantyvaPalikka palikka, int suunta) { // 0 = vasen, 1 = oikea
        if (palikka.getVari() == 2) {                                   // palikka on O-palikka eli ei voi kaantya
            return false;
        }

        KaantyvaPalikka uusiPalikka = kopioiPalikka(palikka);
        if (suunta == 0) {
            uusiPalikka.kaannaVasemmalle();
        } else {
            uusiPalikka.kaannaOikealle();
        }
        if (kaannosOnnistuu(uusiPalikka)) {
            poistaPalikka(palikka);
            return true;
        } else {
            poistaPalikka(uusiPalikka);
            return false;
        }
    }

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
        KaantyvaPalikka kopio = new KaantyvaPalikka(kop.getTetromino(), kop.getXpos(), kop.getYpos(), kop.getRotaatio(), kop.getMaxRotaatio());
        return kopio;
    }

    public Palikka kopioiPalikka(Palikka kop) {
        Palikka kopio = new Palikka(kop.getMuoto(), kop.getXpos(), kop.getYpos(), kop.getVari());  // muoto ei mahdollisesti kopioidu erilliseksi viela!
        return kopio;
    }
}
