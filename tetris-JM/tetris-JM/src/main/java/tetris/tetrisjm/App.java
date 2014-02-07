package tetris.tetrisjm;

import javax.swing.SwingUtilities;
import tetris.gui.Kayttoliittyma;
import tetris.pelilogiikka.Peli;

public class App {
    
    public static void main(String[] args) {
    Peli peli = new Peli();
    
    Kayttoliittyma kali = new Kayttoliittyma(peli,30);
    SwingUtilities.invokeLater(kali);
    
    while(kali.getPaivitettava() == null) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            System.out.println("Piirtoalustaa ei luotu");
        }
     }
    
    peli.setPaivitettava(kali.getPaivitettava());
    peli.start();
    }
}