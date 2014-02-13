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
    
    public void lautaPaivitetty();
    
    public LiikeToiminnat getLukija();
    
    public Pelilauta getPelilauta();
    public ArrayList<KaantyvaPalikka> getSeuraavatPalikat();
    public KaantyvaPalikka getNykyinenPalikka();
    public KaantyvaPalikka getVarjopalikka();
    public Palikka getEfektiPalikka();
    
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
    public boolean onkoVaihtoAika();
}
