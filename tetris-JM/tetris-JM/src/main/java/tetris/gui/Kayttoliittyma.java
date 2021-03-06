package tetris.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import tetris.pelilogiikka.PeliRajapinta;
import tetris.pelilogiikka.SyotteenLukija;

/**
 * Pelin kayttoliittyman ylin taso. Sailyttaa itsessan keybindingit joilla pelin ohjaus toteutetaan.
 * Keybindingeista lahetetaan komentoja SyotteenLukija -rajapintaan pelilogiikassa.
 * Tuntee kayttoliittyman ikkunan JFramena. Grafiikka piirretaan luokan tuntemassa Piirtoalusta - oliossa.
 * @author zaarock
 */
public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private PeliRajapinta peli;
    private int sivunPituus;
    private Piirtoalusta piirto;
    private SyotteenLukija lukija;

    /**
     * Tallentaa muistiin kasiteltavan pelin seka piirtamiseen kaytettavan koon perusyksikon.
     * @param peli peli jota kayttoliittyma piirtaa
     * @param sivunPituus yhden pelilaudan nelion sivun pituus pikseleina
     */
    public Kayttoliittyma(PeliRajapinta peli, int sivunPituus) {
        this.peli = peli;
        this.sivunPituus = sivunPituus;
    }

    /**
     * Alustaa seka kaynnistaa kayttoliittyman.
     */
    @Override
    public void run() {
        frame = new JFrame("TetrisJM");
        frame.setIgnoreRepaint(true);
        int leveys = 18 * sivunPituus + 10;
        int korkeus = 25 * sivunPituus + 10;

        Dimension naytonResoluutio = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ruutu = new Dimension(leveys, korkeus);
        int xPos = naytonResoluutio.width / 2 - 250;
        int yPos = naytonResoluutio.height / 2 - 350;

        frame.setLocation(xPos, yPos);
        frame.setPreferredSize(new Dimension(leveys, korkeus+20));
        frame.setMaximumSize(ruutu);
        frame.setMinimumSize(ruutu);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit();

        frame.pack();
        frame.setVisible(true);
    }

    private void luoKomponentit() {
        this.lukija = peli.getLukija();
        
        piirto = new Piirtoalusta(peli, sivunPituus);
        luoKeyBindings(piirto);

        frame.setContentPane(piirto);
    }
    
    private void luoKeyBindings(JComponent jc) {
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,false), "vasen alhaalla");
        jc.getActionMap().put("vasen alhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.vasenPush(ae);
            }
        });
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,true), "vasen ylhaalla");
        jc.getActionMap().put("vasen ylhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.vasenRelease(ae);
            }
        });

        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,false), "oikea alhaalla");
        jc.getActionMap().put("oikea alhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.oikeaPush(ae);
            }
        });

        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,true), "oikea ylhaalla");
        jc.getActionMap().put("oikea ylhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.oikeaRelease(ae);
            }
        });
        
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,false), "alas alhaalla");
        jc.getActionMap().put("alas alhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.alasPush(ae);
            }
        });
        
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,true), "alas ylhaalla");
        jc.getActionMap().put("alas ylhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.alasRelease(ae);
            }
        });
        
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,false), "ylos alhaalla");
        jc.getActionMap().put("ylos alhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.ylosPush(ae);
            }
        });
        
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,true), "ylos ylhaalla");
        jc.getActionMap().put("ylos ylhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.ylosRelease(ae);
            }
        });

        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,0,false), "z alhaalla");
        jc.getActionMap().put("z alhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.vasenKaannosPush(ae);
            }
        });
        
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,0,true), "z ylhaalla");
        jc.getActionMap().put("z ylhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.vasenKaannosRelease(ae);
            }
        });

        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X,0,false), "x alhaalla");
        jc.getActionMap().put("x alhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.oikeaKaannosPush(ae);
            }
        });
        
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X,0,true), "x ylhaalla");
        jc.getActionMap().put("x ylhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.oikeaKaannosRelease(ae);
            }
        });
        
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P,0,false), "p alhaalla");
        jc.getActionMap().put("p alhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.pausePush(ae);
            }
        });
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y,0,false), "y alhaalla");
        jc.getActionMap().put("y alhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.yesPush(ae);
            }
        });
        jc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_N,0,false), "n alhaalla");
        jc.getActionMap().put("n alhaalla", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lukija.noPush(ae);
            }
        });
        }

    public Frame getFrame() {
        return frame;
    }

    public Paivitettava getPaivitettava() {
        return this.piirto;
    }
}
