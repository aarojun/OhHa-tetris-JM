/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.objects;

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
public class KaantyvaPalikkaTest {
    
    public KaantyvaPalikkaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void vasenKaannosKiertaaMaksimirotaatioonJosRotaatioOnAlleKaksi() {
        KaantyvaPalikka palikka = new KaantyvaPalikka(Tetromino.L);
        int maxrotaatio = palikka.getMaxRotaatio();
        palikka.kaannaVasemmalle();
        assertEquals(maxrotaatio, palikka.getRotaatio());
    }
    
    @Test
    public void oikeaKaannosKiertaaRotaatioonYksiJosRotaatioOnMaksimirotaatio() {
        KaantyvaPalikka palikka = new KaantyvaPalikka(Tetromino.L);
        int maxrotaatio = palikka.getMaxRotaatio();
        palikka.kaannaVasemmalle();
        palikka.kaannaOikealle();
        assertEquals(1, palikka.getRotaatio());
    }
}
