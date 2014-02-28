package tetris.pelilogiikka;

import java.util.ArrayList;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Tetromino;

/**
 * Luokka joka kasittelee pelin tormaysoperaatioita seka liiketta. Luokka vertaa pelilaudan ja
 * palikkojen pisteiden sijaintia, seka liikuttaa ja kaantaapalikoita.
 *
 * @author zaarock
 */
public class TormaysLogiikka {

    private Pelilauta pelilauta;

    public TormaysLogiikka(Pelilauta pelilauta) {
        this.pelilauta = pelilauta;
    }

    /**
     * tarkistaa onko palikassa pistetta joka tormaa seinaan jos siirtyma toteutetaan
     * @param palikka tarkasteltava palikka
     * @param xSiirtyma siirtyma x-koordinaatissa
     * @param ySiirtyma siirtyma y-koordinaatissa
     * @return osuuko palikka seinaan jos siirtyma toteutetaan
     */
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

    public boolean yritaPudottaaPalikkaa(Palikka palikka) {
        if (!onkoPalikallaAlusta(palikka)) {
            palikka.liiku(0, 1);
            return true;
        }
        return false;
    }

    /**
     * Pudottaa kaikkia palikoita joilla ei ole alustaa yhdella y-koordinaatilla.
     *
     * @return pudotettiinko yhtaan palikkaa
     */
    public boolean pudotaPalikoitaYhdella() {
        boolean pudonneita = false;
        ArrayList<Palikka> putoavatPalikat = new ArrayList<Palikka>();
        for (Palikka palikka : this.pelilauta.getPalikat()) {
            pelilauta.irroitaPalikka(palikka);
            if (!onkoPalikallaAlusta(palikka)) {
                putoavatPalikat.add(palikka);
                pudonneita = true;
            }
            pelilauta.liitaPalikka(palikka);
        }
        for (Palikka palikka : putoavatPalikat) {
            pelilauta.irroitaPalikka(palikka);
            palikka.liiku(0, 1);
            pelilauta.liitaPalikka(palikka);
        }
        return pudonneita;
    }

    /**
     * Yrittaa kaantaa annettua palikkaa 90 astetta annettuun suuntaan.
     * Kaantamisen testaus toteutetaan kloonilla palikasta.
     * O-tetrominon omaavaa palikkaa ei kaanneta koska sen muoto on nelio.
     * @param palikka Kaannettava palikka
     * @param suunta kaannoksen suunta. 0 = vasen, 1 = oikea.
     * @return onnistuiko kaannos
     */
    public boolean kaannaPalikka(KaantyvaPalikka palikka, int suunta) { 
        if (palikka.getTetromino() == Tetromino.O) {
            return false;
        }

        KaantyvaPalikka uusiPalikka = new KaantyvaPalikka(palikka);
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

    /**
     * Tarkistaa onnistuuko palikan kaannos, eli tormaako se seinaan.
     * Lisatty lisaominaisuus jossa palikkaa 'potkaistaan' sivulle jos se kaantyy seinaa vasten.
     * T-palikka pystyy myos potkaista maata ja I-palikka potkaista 2 ruutua oikealle.
     * @param palikka kaannetty palikka
     * @return onnistuuko kaannos
     */
    private boolean kaannosOnnistuu(KaantyvaPalikka palikka) {
        if (onkoPalikkaSeinanSisalla(palikka)) {
            palikka.liiku(1, 0);
            if (onkoPalikkaSeinanSisalla(palikka)) {
                palikka.liiku(-2, 0);
                if (onkoPalikkaSeinanSisalla(palikka)) {
                    Tetromino tet = palikka.getTetromino();
                    if (tet == Tetromino.T) {
                        palikka.liiku(1, -1);
                    } else if (tet == Tetromino.I) {
                        palikka.liiku(3, 1);
                    } else {
                        return false;
                    }
                    if (onkoPalikkaSeinanSisalla(palikka)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Yrittaa kaantaa annettua palikkaa vasemmalle
     * @param palikka kaannettava palikka
     * @return onnistuiko palikan kaannos
     */
    public boolean kaannaPalikkaVasemmalle(KaantyvaPalikka palikka) {
        return kaannaPalikka(palikka, 0);
    }

    /**
     * Yrittaa kaantaa annettua palikka oikealle
     * @param palikka kaannettava palikka
     * @return onnistuiko palikan kaannos
     */
    public boolean kaannaPalikkaOikealle(KaantyvaPalikka palikka) {
        return kaannaPalikka(palikka, 1);
    }
}
