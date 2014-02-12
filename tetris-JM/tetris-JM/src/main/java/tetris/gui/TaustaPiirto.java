package tetris.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import tetris.objects.Pelilauta;

public class TaustaPiirto extends BufferoituPanel {

    private BufferedImage tausta;
    private BufferedImage background = null;
    private int korkeus;
    private int leveys;
    private int nelionKoko;
    private final Color taustaVari = new Color(50, 70, 90);
    private final Color gridVari = new Color(255, 255, 255, 40);

    public TaustaPiirto(Pelilauta lauta, int nelionKoko) {
//        initComponents();
        this.setOpaque(false);
        this.nelionKoko = nelionKoko;
        this.korkeus = lauta.getKorkeus();
        this.leveys = lauta.getLeveys();

        try { background = ImageIO.read(getClass().getResource("/background0.jpg"));
        } catch (Exception IOException) {
            System.out.println("failed to load background image");
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.translate(nelionKoko, nelionKoko);

        Graphics2D g2 = (Graphics2D) g;

        if (tausta == null) {
            uudelleenPiirra();
        } 
        g2.drawImage(tausta, null, 0, 0);
        
    }

    public void piirraGrid(Graphics g) {
        g.setColor(gridVari);
        for (int j = 0; j < korkeus; j++) {
            g.drawLine(0, j * nelionKoko, leveys * nelionKoko, j * nelionKoko);
        }
        for (int i = 1; i < leveys; i++) {
            g.drawLine(i * nelionKoko, 0, i * nelionKoko, korkeus * nelionKoko);
        }
    }

    @Override
    public void paivita() {
        super.repaint();
    }

    public void uudelleenPiirra() {
        int leveys = this.getWidth();
        int korkeus = this.getHeight();
        tausta = (BufferedImage) (this.createImage(leveys, korkeus));
        Graphics2D gc = tausta.createGraphics();

        gc.drawImage(background,-20,-50, null);

        piirraGrid(gc);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 300, Short.MAX_VALUE));
    }
}
