package tetris.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLayeredPane;
import tetris.pelilogiikka.Peli;

public class Piirtoalusta extends JLayeredPane implements Paivitettava {

    private Peli peli;
    private int nelionKoko;
    private int varjotusEro;
    private int laudanKorkeus;
    private int laudanLeveys;
    private Color varjo;
    private Font pisteFont;
    private Font menuFont;
    private LaudanPiirto laudanPiirto;
    private TaustaPiirto taustanPiirto;
    private LiikkuvienPiirto liikkuvienPiirto;
    private KehysPiirto kehyksenPiirto;

    public Piirtoalusta(Peli peli, int nelionSivunPituus) {
        this.peli = peli;
        this.nelionKoko = nelionSivunPituus;
        this.varjotusEro = nelionKoko/5;
        this.laudanLeveys = peli.getPelilauta().getLeveys();
        this.laudanKorkeus = peli.getPelilauta().getKorkeus();

        this.pisteFont = new Font(Font.SANS_SERIF, 1, 16);
        this.menuFont = new Font(Font.SANS_SERIF, 0, 20);
        this.varjo = new Color(0, 0, 0, 130);
        this.setBackground(Color.BLACK);
 
        this.taustanPiirto = new TaustaPiirto(peli.getPelilauta(),nelionKoko);
        taustanPiirto.setBounds(0,0,11*nelionKoko,24*nelionKoko);
        
        this.laudanPiirto = new LaudanPiirto(peli,nelionKoko);
        laudanPiirto.setBounds(nelionKoko,nelionKoko,15*nelionKoko,23*nelionKoko);
        
        this.liikkuvienPiirto = new LiikkuvienPiirto(peli,nelionKoko);
        liikkuvienPiirto.setBounds(nelionKoko,3*nelionKoko,30*nelionKoko,40*nelionKoko);
        
        this.kehyksenPiirto = new KehysPiirto(peli, nelionKoko);
        kehyksenPiirto.setBounds(0,0,30*nelionKoko,40*nelionKoko);
        this.setOpaque(true);
        
        this.add(taustanPiirto, new Integer(1));
        this.add(laudanPiirto, new Integer(3));
        this.add(liikkuvienPiirto, new Integer(2));
        this.add(kehyksenPiirto, new Integer(4));
    }

    public void setPeli(Peli peli) {
        this.peli = peli;
    }

    @Override
    public void paivita() {
//        if(peli.getLautaPaivita()) {
//            this.laudanPiirto.uudelleenPiirra();
//        }
        repaint();
    }
}
