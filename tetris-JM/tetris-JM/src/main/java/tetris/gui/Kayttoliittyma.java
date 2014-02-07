package tetris.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;
import tetris.pelilogiikka.Nappaimistonkuuntelija;
import tetris.pelilogiikka.Peli;

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private Peli peli;
    private int sivunPituus;
    private Piirtoalusta piirto;
    private TaustaPiirto tausta;

    public Kayttoliittyma(Peli peli, int sivunPituus) {
        this.peli = peli;
        this.sivunPituus = sivunPituus;
    }

    @Override
    public void run() {
        frame = new JFrame("TetrisJM");
        int leveys = 15 * sivunPituus + 10;
        int korkeus = 20 * sivunPituus + 30;

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ruutu = new Dimension(leveys, korkeus);
        int xPos = screen.width / 2 - 250;
        int yPos = screen.height / 2 - 350;

        frame.setLocation(xPos, yPos);
        frame.setPreferredSize(new Dimension(leveys, korkeus));
//        frame.setMaximumSize(ruutu);
//        frame.setMinimumSize(ruutu);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(frame.getContentPane());
        
        Nappaimistonkuuntelija ohjaus = new Nappaimistonkuuntelija(peli);
        frame.addKeyListener(ohjaus);

        frame.pack();
        frame.setVisible(true);
    }

    private void luoKomponentit(Container container) {
        piirto = new Piirtoalusta(peli, sivunPituus);
//        tausta = new TaustaPiirto(sivunPituus);
//        tausta.add(piirto);
        
        container.add(piirto);
    }

    public JFrame getFrame() {
        return frame;
    }
    
    public void quit() {
        frame.dispose();
    }

    public Paivitettava getPaivitettava() {
        return this.piirto;
    }
}
