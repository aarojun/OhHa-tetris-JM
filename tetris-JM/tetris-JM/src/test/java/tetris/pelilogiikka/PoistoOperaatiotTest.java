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
import tetris.objects.Pelilauta;
import tetris.objects.Tetromino;

/**
 *
 * @author zaarock
 */
public class PoistoOperaatiotTest {

    private PoistoOperaatiot poistot;
    private Pelilauta p;

    public PoistoOperaatiotTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        p = new Pelilauta(10, 20);
        poistot = new PoistoOperaatiot(p);
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void poistaPisteToimii() {
        KaantyvaPalikka zPalikka = new KaantyvaPalikka(Tetromino.Z);
        
        poistot.poistaPiste(zPalikka, 0);
        assertEquals(3, zPalikka.getMuoto().size());
        
        poistot.poistaPiste(zPalikka, 2);
        assertEquals(2, zPalikka.getMuoto().size());
    }
    
    @Test
    public void poistaKaikkiPoistaaKaiken() {
        p.lisaaLiitettyPalikka(new KaantyvaPalikka(Tetromino.Z));
        
        poistot.poistaKaikki();
        
        assertEquals(true, p.getPalikat().isEmpty());
    }
    
    @Test
    public void taysienRivienPoistoToimii() {
        KaantyvaPalikka pal1 = new KaantyvaPalikka(Tetromino.I);
        KaantyvaPalikka pal2 = new KaantyvaPalikka(Tetromino.I);
        KaantyvaPalikka pal3 = new KaantyvaPalikka(Tetromino.Z);
        
        // asetetaan palikat riviin joka luo tayden rivin..
        
        pal1.setYpos(10);
        
        pal2.setXpos(4);
        pal2.setYpos(10);
        
        pal3.setXpos(7);
        pal3.setYpos(9);
        
        p.lisaaLiitettyPalikka(pal1);
        p.lisaaLiitettyPalikka(pal2);
        p.lisaaLiitettyPalikka(pal3);
        
        //tarkistetaan onko rivi taysi
        assertEquals(false, p.taydetRivit().isEmpty());
        
        // kokeillaan rivienpoistoa
        
        poistot.poistaTaydetRivit();
        
        //tarkistetaan katosiko taysi rivi
        assertEquals(true, p.taydetRivit().isEmpty());
        
        //tarkistetaan puuttuvatko palikat 1 ja 2 (joiden kaikki pisteet olivat rivilla) nyt pelilaudalta
        assertEquals(false, p.getPalikat().contains(pal1));
        assertEquals(false,p.getPalikat().contains(pal2));
    }
}
