package tetris.objects;

import java.util.ArrayList;

public class Palikka {
    private Vari vari;
    private ArrayList<int[]> muoto;
    private int xPos;
    private int yPos;

    public Palikka(int[] pisteet, int x, int y, Vari vari) {
        this.xPos = x;
        this.yPos = y;
        this.vari = vari;
        
        this.muoto = new ArrayList<>();
        setMuoto(pisteet);
        }
    
    public Palikka(ArrayList<int[]> muoto, int x, int y, Vari vari) {
        this.xPos = x;
        this.yPos = y;
        this.vari = vari;
        
        this.muoto = new ArrayList<>(muoto);
        }

    public void setMuoto(int[] pisteet) {
         this.muoto.clear();
         for (int i = 0; i <= 6; i += 2) {
            int xKoord = pisteet[i];
            int yKoord = pisteet[i+1];
            this.muoto.add(new int[]{xKoord, yKoord});
    }
    }
         
    public void setMuoto(ArrayList<int[]> muoto) {
         this.muoto = new ArrayList<>(muoto);
    }


    public void liiku(int x, int y) {
        this.xPos += x;
        this.yPos += y;
    }
    
    public void setXpos(int x) {
        this.xPos = x;
    }
    
    public void setYpos(int y) {
        this.yPos = y;
    }
    
    public int getXpos() {
        return this.xPos;
    }
    
    public int getYpos() {
        return this.yPos;
    }
    
    public ArrayList<int[]> getMuoto() {
        return this.muoto;
    }
    
    public Vari getVari() {
        return this.vari;
    }
}
