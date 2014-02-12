package tetris.objects;

import java.awt.Color;

/**
 * Palikkaan liitettava vari. Kokoelma pelin kayttamia Color -objekteja.
 * @author zaarock
 */
public enum Vari {
    RED(220,20,60), MAGENTA(199,21,133), YELLOW(255,215,0), CYAN(127,255,212), 
    BLUE(0,0,205), ORANGE(255,140,0), LIME(154,205,50), GRAY(50,50,50);
    
    private Color awtVari;
    
    private Vari(int red, int green, int blue) {
        this.awtVari = new Color(red,green,blue);
    }
    
    public Color getColor() {
        return this.awtVari;
    }
}
