package tetris.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLayeredPane;
import tetris.pelilogiikka.PeliRajapinta;

// Tetris-pelin JFramen contentPane seka layereiden kasittelija 
/**
 * Kayttoliittyman JFramen contentPane. Piirtaa sisaltonsa
 *
 * @author zaarock
 */
public class Piirtoalusta extends JLayeredPane implements Paivitettava {

    private PeliRajapinta peli;
    private int nelionKoko;
    private int laudanKorkeus;
    private int laudanLeveys;
    private RenderCanvas canvas;
    private PiirtoTyokalu piirtoTyokalu;
    private BufferoituPanel laudanPiirto;
    private BufferoituPanel taustanPiirto;
    private BufferoituPanel liikkuvienPiirto;
    private BufferoituPanel kehyksenPiirto;

    public Piirtoalusta(PeliRajapinta peli, int nelionSivunPituus) {
        this.setIgnoreRepaint(true);
        this.peli = peli;
        this.nelionKoko = nelionSivunPituus;

        this.laudanLeveys = peli.getPelilauta().getLeveys();
        this.laudanKorkeus = peli.getPelilauta().getKorkeus();

        this.setBackground(Color.BLACK);

        this.piirtoTyokalu = new PiirtoTyokalu(nelionKoko);

        this.taustanPiirto = new TaustaPiirto(peli.getPelilauta(), nelionKoko);
        taustanPiirto.setBounds(0, 0, 11 * nelionKoko, 24 * nelionKoko);
        taustanPiirto.paivita();

        this.laudanPiirto = new LaudanPiirto(peli, nelionKoko, piirtoTyokalu);
        laudanPiirto.setBounds(nelionKoko, nelionKoko, 15 * nelionKoko, (laudanKorkeus) * nelionKoko);
        laudanPiirto.paivita();

        this.liikkuvienPiirto = new LiikkuvienPiirto(peli, nelionKoko, piirtoTyokalu);
        liikkuvienPiirto.setBounds(nelionKoko, 3 * nelionKoko, 30 * nelionKoko, (laudanKorkeus - 2) * nelionKoko);

        this.kehyksenPiirto = new KehysPiirto(peli, nelionKoko);
        kehyksenPiirto.setBounds(0, 0, 30 * nelionKoko, 40 * nelionKoko);
        this.setOpaque(true);

        this.add(taustanPiirto, new Integer(1));
        this.add(laudanPiirto, new Integer(3));
        this.add(liikkuvienPiirto, new Integer(2));
        this.add(kehyksenPiirto, new Integer(4));
    }
    
    public Canvas getCanvas() {
        return this.canvas;
    }
    
    public void initialize() {
        canvas.initialize();     
    }

    @Override
    public void paivita() {
        repaint();
        Toolkit.getDefaultToolkit().sync();
    }
}
