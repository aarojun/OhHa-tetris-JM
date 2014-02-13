package tetris.pelilogiikka;

import java.util.ArrayList;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;

/**
 * Rajapinta peli - ilmentymille. palauttaa pelilogiikkaan liittyvia arvoja.
 * 
 * @author zaarock
 */
public interface PeliRajapinta {
    
    /**
     * Kuittaa pelille laudan graafisen paivityksen.
     */
    public void lautaPaivitetty();
    
    /**
     * Palauttaa peliin liitetyn SyotteenLukijan
     * @return pelin kayttama SyotteenLukija
     */
    public SyotteenLukija getLukija();
    
    /**
     * Palauttaa peliin liitetyn pelilaudan.
     * @return pelilauta
     */
    public Pelilauta getPelilauta();
    /**
     * Palauttaa listan pelin seuraavista palikoista
     * @return lista seuraavista palikoista.
     */
    public ArrayList<KaantyvaPalikka> getSeuraavatPalikat();
    /**
     * Palauttaa pelissa talla hetkella liikutettavan palikan
     * @return ohjattava palikka.
     */
    public KaantyvaPalikka getNykyinenPalikka();
    /**
     * Palauttaa varjopalikan (liikuteltava palikka pudotettu alas)
     * @return ohjattavan palikan varjo.
     */
    public KaantyvaPalikka getVarjopalikka();
    /**
     * Palauttaa nykyisen pelilaudan palikan jossa on vilkkumisefekti paalla.
     * @return viimeksi asetettu palikka jolle piirretaan efekti.
     */
    public Palikka getEfektiPalikka();
    
    /**
     * Palauttaa pelin ajastimen aika String-muodossa
     * @return pelin ajastimen aika String-muodossa.
     */
    public String getAika();
    
    public int getVaikeustaso();
    public int getPisteet();
    public int getHighScore();
    public int getVuoro();
    public double getFrekMultiplier();
    public int getCombo();
    
    public boolean getLautaPaivita();
    public boolean getPaused();
    public boolean getGameover();
    public boolean getTimeOver();
    public boolean getStartMenu();
    /**
     * Palauttaa tosi jos pelissa on vuoronvaihto-aika paalla.
     * @return vaihtoaika tosi/ei tosi
     */
    public boolean onkoVaihtoAika();
    /**
     * Palauttaa tosi jos peli on paalla.
     * @return
     */
    public boolean onkoPaalla();
}
