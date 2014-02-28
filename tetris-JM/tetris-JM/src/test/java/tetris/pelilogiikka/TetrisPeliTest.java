/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.pelilogiikka;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tetris.objects.KaantyvaPalikka;

/**
 *
 * @author zaarock
 */
public class TetrisPeliTest {
    private TetrisPeli peli;
    private KaantyvaPalikka liikutettava;
    
    public TetrisPeliTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        peli = new TetrisPeli();
        liikutettava = peli.getNykyinenPalikka();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void liikutaAlasToimii() {
        int yPos = liikutettava.getYpos();        
        
        peli.liikutaAlas();
        
        assertEquals(yPos+1, liikutettava.getYpos());
    }
    
    @Test
    public void liikutaVasenToimii() {
        int xPos = liikutettava.getXpos();        
        
        peli.liikutaVasen();
        
        assertEquals(xPos-1, liikutettava.getXpos());
    }
    
    @Test
    public void liikutaOikeaToimii() {
        int xPos = liikutettava.getXpos();        
        
        peli.liikutaOikea();
        
        assertEquals(xPos+1, liikutettava.getXpos());
    }
}
