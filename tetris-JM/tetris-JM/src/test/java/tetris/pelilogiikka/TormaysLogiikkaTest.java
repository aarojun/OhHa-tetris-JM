package tetris.pelilogiikka;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tetris.objects.KaantyvaPalikka;
import tetris.objects.Palikka;
import tetris.objects.Pelilauta;
import tetris.objects.Tetromino;

/**
 *
 * @author zaarock
 */
public class TormaysLogiikkaTest {
    private TormaysLogiikka tormayslogiikka;
    
    public TormaysLogiikkaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Pelilauta p = new Pelilauta(10,20);
        PoistoOperaatiot pois = new PoistoOperaatiot(p);
        this.tormayslogiikka = new TormaysLogiikka(p, pois);
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void oPalikkaaEiKaanneta() {
        KaantyvaPalikka oPalikka = new KaantyvaPalikka(Tetromino.O);
        
        assertEquals(false, tormayslogiikka.kaannaPalikka(oPalikka, 0));
        assertEquals(false, tormayslogiikka.kaannaPalikka(oPalikka, 1));
    }
    
    @Test
    public void palikkaKaantyyVasemmalle() {
        KaantyvaPalikka sPalikka = new KaantyvaPalikka(Tetromino.S);
        
        assertEquals(true, tormayslogiikka.kaannaPalikka(sPalikka,0));
    }
    
    @Test
    public void palikkaKaantyyOikealle() {
        KaantyvaPalikka zPalikka = new KaantyvaPalikka(Tetromino.Z);
        
        assertEquals(true, tormayslogiikka.kaannaPalikka(zPalikka,0));
    }
}
