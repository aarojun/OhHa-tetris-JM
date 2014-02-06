package tetris.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import tetris.pelilogiikka.Peli;

public class Kayttoliittyma implements Runnable {
    
    private JFrame frame;
    private Peli peli;
    private int sivunPituus;
    private Piirtoalusta piirto;
    
    public Kayttoliittyma(Peli peli, int sivunPituus) {
        this.peli = peli;
        this.sivunPituus = sivunPituus;
    }

    @Override
    public void run() {
        frame = new JFrame("TetrisJM");
        int leveys = 10*sivunPituus+10;
        int korkeus = 20*sivunPituus+10;
        
        frame.setPreferredSize(new Dimension(leveys,korkeus));
        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        luoKomponentit(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }
    
    private void luoKomponentit(Container container) {
        piirto = new Piirtoalusta(peli,sivunPituus);
        container.add(piirto);
        
        frame.addKeyListener(new Nappaimistonkuuntelija(peli));
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    public Paivitettava getPaivitettava() {
        return this.piirto;
    }
    
}
