package tetris.gui;

import tetris.pelilogiikka.Nappaimistonkuuntelija;
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
        int leveys = 15*sivunPituus+10;
        int korkeus = 20*sivunPituus+30;
        
        frame.setPreferredSize(new Dimension(leveys,korkeus));
        frame.setLocation(500, 200);
        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        luoKomponentit(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }
    
    private void luoKomponentit(Container container) {
        Nappaimistonkuuntelija ohjaus = new Nappaimistonkuuntelija(peli);
        piirto = new Piirtoalusta(peli,sivunPituus,ohjaus);
        container.add(piirto);
        
        frame.addKeyListener(ohjaus);
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    public Paivitettava getPaivitettava() {
        return this.piirto;
    }
    
}
