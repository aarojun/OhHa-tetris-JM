package tetris.pelilogiikka;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tetris.objects.KaantyvaPalikka;
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
        this.tormayslogiikka = new TormaysLogiikka(p);
    }
    
    @After
    public void tearDown() {
    }

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
    
    @Test
    public void palikkaOnVasJaYlaSeinaaVastenJosVasemmassaYlanurkassa() {
        KaantyvaPalikka sPalikka = new KaantyvaPalikka(Tetromino.S);
        sPalikka.setXpos(0);
        sPalikka.setYpos(0);
        
        assertEquals(true, tormayslogiikka.onkoPalikkaVasSeinaaVasten(sPalikka));
        assertEquals(true, tormayslogiikka.onkoPalikkaVasten(sPalikka, 0, -1));                
    }
    
    @Test
    public void palikkaOnOikJaAlaSeinaavastenJosOikeassaAlanurkassa() {
        KaantyvaPalikka oPalikka = new KaantyvaPalikka(Tetromino.O);
        oPalikka.setXpos(9);
        oPalikka.setYpos(19);
        
        assertEquals(true, tormayslogiikka.onkoPalikkaOikSeinaaVasten(oPalikka));
        assertEquals(true, tormayslogiikka.onkoPalikallaAlusta(oPalikka));        
    }
    
    @Test
    public void pudotaPalikoitaYhdellaPudottaaKaikkiaPalikoita() {
        Pelilauta p = new Pelilauta(10,20);
        KaantyvaPalikka iPalikka = new KaantyvaPalikka(Tetromino.I);
        KaantyvaPalikka tPalikka = new KaantyvaPalikka(Tetromino.T);
        iPalikka.setYpos(11);
        iPalikka.setXpos(5);
        p.lisaaLiitettyPalikka(tPalikka);
        p.lisaaLiitettyPalikka(iPalikka);
        tormayslogiikka = new TormaysLogiikka(p);
        
        tormayslogiikka.pudotaPalikoitaYhdella();
        assertEquals(12, iPalikka.getYpos());
        assertEquals(1, tPalikka.getYpos());
    }
}
