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
         g2.drawImage(tausta, null, 0, 0);       
    }
    
    public void uudelleenPiirra() {
        int lev = this.getWidth();
        int kor = this.getHeight();
        tausta = new BufferedImage(lev,kor,1);
        Graphics2D gc = tausta.createGraphics();

        gc.drawImage(background,-20,-50, null);

        piirraGrid(gc);
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
        uudelleenPiirra();
    }
}
