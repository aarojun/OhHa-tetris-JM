/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 * Valiaikainen testiluokka aktiivisen renderoinnin testaamiseen.
 * @author zaarock
 */
public class RenderCanvas extends Canvas {
    private BufferStrategy strat = null;
    private boolean done = true;
    private BufferoituPanel b1;
    private BufferoituPanel b2;
    private BufferoituPanel b3;
    private BufferoituPanel b4;
    
    public RenderCanvas(BufferoituPanel b1,BufferoituPanel b2, BufferoituPanel b3, BufferoituPanel b4) {
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.b4 = b4;
    }
    
    public void initialize() {
        this.createBufferStrategy(2);
        strat = this.getBufferStrategy();
    }
    
    public void paivita() {
    if (done) {
        this.createBufferStrategy(2);
            Graphics2D g1 = (Graphics2D)strat.getDrawGraphics();
            g1.clearRect(0,0,getWidth(),getHeight());
            done = false;
            b1.render(g1);
            b2.render(g1);
            b3.render(g1);
            b4.render(g1);
            g1.dispose();
            strat.show();
            done = true;
        }
    }
}
