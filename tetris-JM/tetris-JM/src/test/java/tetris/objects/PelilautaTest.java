package tetris.objects;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zaarock
 */
public class PelilautaTest {
    private Pelilauta pelilauta;
    
    public PelilautaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.pelilauta = new Pelilauta(10,20);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void pisteenAsetusToimii() {
        pelilauta.asetaPiste(8, 19);
        int[][] matriisi = pelilauta.getAlueMatriisi();
        assertEquals(1,matriisi[8][19]);
    }
    
    @Test
    public void pisteenAsetusEiKaaduJosAlueenUlkopuolella() {
        pelilauta.asetaPiste(99,66);
    }
    
    @Test
    public void pisteenPoistoToimii() {
        int[][] matriisi = pelilauta.getAlueMatriisi();
        pelilauta.asetaPiste(9,10);
        assertEquals(1,matriisi[9][10]);
        pelilauta.poistaPiste(9,10);
        assertEquals(0,matriisi[9][10]);
    }
    
    @Test
    public void pisteenPoistoEiKaaduJosAlueenUlkopuolella() {
        pelilauta.poistaPiste(66,99);
    }
    
    @Test
    public void pisteenTarkistusToimii() {
        pelilauta.asetaPiste(3, 4);
        assertEquals(true,pelilauta.tarkistaPiste(3, 4));
    }
    
    @Test
    public void pisteenTarkistusPalauttaaTosiJosAlueenUlkopuolella() {
        assertEquals(true,pelilauta.tarkistaPiste(22, 44));
        assertEquals(true,pelilauta.tarkistaPiste(-31,-14));
    }
    
    @Test
    public void konstruktoriMaarittaaAlueenRajan() {
        this.pelilauta = new Pelilauta(23,11);
        assertEquals(true,pelilauta.tarkistaPiste(24, 12));
    }
    
    @Test
    public void palikanLiittaminenAsettaaPisteetOikein() {
        KaantyvaPalikka pl = new KaantyvaPalikka(Tetromino.Z);
        pelilauta.liitaPalikka(pl);
        ArrayList<int[]> palikanPisteet = pl.getMuoto();
        for(int[] piste : palikanPisteet) {
        assertEquals(true,pelilauta.tarkistaPiste(piste[0],piste[1]));
        }        
    }
    
    @Test
    public void palikanIrroittaminenPoistaaPalikanPisteet() {
        KaantyvaPalikka pl = new KaantyvaPalikka(Tetromino.T);
        pelilauta.asetaPiste(0, 0);
        pelilauta.asetaPiste(1,1);
        pelilauta.asetaPiste(2,0);
        
        assertEquals(true,pelilauta.tarkistaPiste(0,0));
        
        pelilauta.irroitaPalikka(pl);
        
        assertEquals(false,pelilauta.tarkistaPiste(0,0));
        assertEquals(false,pelilauta.tarkistaPiste(1,1));
        assertEquals(false,pelilauta.tarkistaPiste(2,0)); 
    }
    
    @Test
    public void lisaaLiitettyPalikkaLisaaPalikanListaan() {
        KaantyvaPalikka yksinainenPalikka = new KaantyvaPalikka(Tetromino.O);
        pelilauta.lisaaLiitettyPalikka(yksinainenPalikka);
        
        assertEquals(true,pelilauta.getPalikat().contains(yksinainenPalikka));        
    }
    
    @Test
    public void taydetRivitPalauttaaTaydetRivit() {
        for(int i = 0; i< pelilauta.getLeveys(); i++) {
            pelilauta.asetaPiste(i, 0);
            pelilauta.asetaPiste(i,13);
            pelilauta.asetaPiste(i,19);
        }
        
        ArrayList<Integer> taydetRivit = pelilauta.taydetRivit();
        
        assertEquals(true,taydetRivit.contains(13));
        assertEquals(true,taydetRivit.contains(0));
        assertEquals(true,taydetRivit.contains(19));
    }
    
    @Test
    public void nollaaRiviTyhjentaaRivin() {
        for(int i = 0; i< pelilauta.getLeveys(); i++) {
            pelilauta.asetaPiste(i,3);
        }
        
        assertEquals(true,pelilauta.tarkistaPiste(9, 3));
        
        pelilauta.nollaaRivi(3);
        
        for(int i = 0; i<pelilauta.getLeveys(); i++) {
            assertEquals(false,pelilauta.tarkistaPiste(i,3));
        }
    }
    
    @Test
    public void clearMetodiTyhjentaaKaikki() {
        KaantyvaPalikka pl1 = new KaantyvaPalikka(Tetromino.S);
        KaantyvaPalikka pl2 = new KaantyvaPalikka(Tetromino.L);
        pl2.liiku(6, 0);
        
        int[][] matriisi = pelilauta.getAlueMatriisi();
        
        pelilauta.lisaaLiitettyPalikka(pl2);
        pelilauta.lisaaLiitettyPalikka(pl1);
        
        assertEquals(1,matriisi[1][0]);
        
        pelilauta.clear();
        
        assertEquals(true,pelilauta.getPalikat().isEmpty());
        assertEquals(0,pelilauta.getAlueMatriisi()[1][0]);        
    }
}
