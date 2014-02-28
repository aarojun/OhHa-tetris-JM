package tetris.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLayeredPane;
import tetris.pelilogiikka.PeliRajapinta;

/**
 * Kayttoliittyman JFramelle asetettava contentPane.
 * Tuntee grafiikan piirtavat tasot joita kayttaa layereina, eli piirtaa ne tasoittain.
 *
 * @author zaarock
 */
public class Piirtoalusta extends JLayeredPane implements Paivitettava {

    private int nelionKoko;
    private int laudanKorkeus;
    private int laudanLeveys;
    private PiirtoTyokalu piirtoTyokalu;
    private BufferoituPanel laudanPiirto;
    private BufferoituPanel taustanPiirto;
    private BufferoituPanel liikkuvienPiirto;
    private BufferoituPanel kehyksenPiirto;

    /**
     * 
     * @param peli
     * @param nelionSivunPituus 
     */
    public Piirtoalusta(PeliRajapinta peli, int nelionSivunPituus) {
        this.setIgnoreRepaint(true);
        this.setOpaque(true);
        this.nelionKoko = nelionSivunPituus;
        
        this.setBackground(Color.black);

        this.laudanLeveys = peli.getPelilauta().getLeveys();
        this.laudanKorkeus = peli.getPelilauta().getKorkeus();

        this.piirtoTyokalu = new PiirtoTyokalu(nelionKoko);

        this.taustanPiirto = new TaustaPiirto(peli.getPelilauta(), nelionKoko);
        taustanPiirto.setBounds(nelionKoko, nelionKoko, 10 * nelionKoko, 23 * nelionKoko);
        taustanPiirto.paivita();

        this.laudanPiirto = new LaudanPiirto(peli, nelionKoko, piirtoTyokalu);
        laudanPiirto.setBounds(nelionKoko, nelionKoko, 15 * nelionKoko, (laudanKorkeus) * nelionKoko);
        laudanPiirto.paivita();

        this.liikkuvienPiirto = new LiikkuvienPiirto(peli, nelionKoko, piirtoTyokalu);
        liikkuvienPiirto.setBounds(nelionKoko, 3 * nelionKoko, 30 * nelionKoko, (laudanKorkeus - 2) * nelionKoko);

        this.kehyksenPiirto = new KehysPiirto(peli, nelionKoko);
        kehyksenPiirto.setBounds(nelionKoko, nelionKoko, 30 * nelionKoko, 40 * nelionKoko);

        this.add(taustanPiirto, new Integer(1));
        this.add(laudanPiirto, new Integer(3));
        this.add(liikkuvienPiirto, new Integer(2));
        this.add(kehyksenPiirto, new Integer(4));
    }

    @Override
    public void paivita() {
        kehyksenPiirto.repaint();
        Toolkit.getDefaultToolkit().sync();
    }
}
