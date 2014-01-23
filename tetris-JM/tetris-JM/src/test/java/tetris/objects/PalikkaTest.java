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
public class PalikkaTest {
    private ArrayList<int[]> tyhjaList;
    
    public PalikkaTest() {
        this.tyhjaList = new ArrayList<>();
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void palikkaAsettuuOikein() {
        Palikka palikka = new Palikka(tyhjaList,3,6,1);
        assertEquals(3, palikka.getXpos());
        assertEquals(6, palikka.getYpos());
    }
    
    @Test
    public void palikanMuodonPisteetAsettuvatOikein() {
        ArrayList<int[]> muoto = new ArrayList<>();
        muoto.add(new int[]{1,0});
        muoto.add(new int[]{2,0});
        muoto.add(new int[]{2,1});
        Palikka palikka = new Palikka(muoto,1,2,1);
        assertEquals(2, palikka.getMuoto().get(1)[0]);
        assertEquals(1, palikka.getMuoto().get(2)[1]);
    }
    
    @Test
    public void palikkaLiikkuuPositiivisiinSuuntiin() {
        Palikka palikka = new Palikka(tyhjaList,1,2,1);
        palikka.liiku(3, 1);
        assertEquals(4, palikka.getXpos());
        assertEquals(3, palikka.getYpos());
    }
    
    @Test
    public void palikkaLiikkuuNegatiivisiinSuuntiin() {
        int[][] muoto = new int[][]{{}};
        Palikka palikka = new Palikka(tyhjaList,2,1,1);
        palikka.liiku(-3, -6);
        assertEquals(-1, palikka.getXpos());
        assertEquals(-5, palikka.getYpos());
    }
}
