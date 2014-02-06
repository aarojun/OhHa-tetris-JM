package tetris.objects;

import java.awt.Color;

public enum Vari {
    RED(Color.RED), MAGENTA(Color.MAGENTA), YELLOW(Color.YELLOW), CYAN(Color.CYAN), 
    BLUE(Color.BLUE), ORANGE(Color.ORANGE), LIME(Color.GREEN);
    
    private Color awtVari;
    
    private Vari(Color awtColor) {
        this.awtVari = awtColor;
    }
    
    public Color getColor() {
        return this.awtVari;
    }
}
