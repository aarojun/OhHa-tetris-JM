package tetris.gui;

import java.awt.Polygon;

public class Kolmio extends Polygon {
    
    public Kolmio(int sivunKoko, int xKoord, int yKoord) {
        super.addPoint(xKoord+0*sivunKoko, yKoord+1*sivunKoko);
        super.addPoint(xKoord+3*sivunKoko, yKoord+0*sivunKoko);
        super.addPoint(xKoord+3*sivunKoko, yKoord+2*sivunKoko);
    }
}
