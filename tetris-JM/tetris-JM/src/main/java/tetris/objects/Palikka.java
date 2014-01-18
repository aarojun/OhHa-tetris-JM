package tetris.objects;

public class Palikka {
    private int vari;
    private int[][] muoto;
    private int xPos;
    private int yPos;

    public Palikka(int[] pisteet, int x, int y, int vari) {
        this.xPos = x;
        this.yPos = y;
        this.vari = vari;
        
        this.muoto = null;
        setMuoto(pisteet);
        }
    
    public Palikka(int[][] muoto, int x, int y, int vari) {
        this.xPos = x;
        this.yPos = y;
        this.vari = vari;
        
        this.muoto = new int[4][4];
        System.arraycopy(muoto, 0, this.muoto, 0, muoto.length );
        }

    public void setMuoto(int[] pisteet) {
         this.muoto = null;
         for (int i = 0; i <= 6; i += 2) {
            int xKoord = pisteet[i];
            int yKoord = pisteet[i+1];
            this.muoto[i] = new int[]{xKoord, yKoord};
    }
    }
         
    public void setMuoto(int[][] muoto) {
         this.muoto = new int[4][4];
         System.arraycopy(muoto, 0, this.muoto, 0, muoto.length );
    }


    public void liiku(int x, int y) {
        this.xPos += x;
        this.yPos += y;
    }
    
    public int getXpos() {
        return this.xPos;
    }
    
    public int getYpos() {
        return this.yPos;
    }
    
    public int[][] getMuoto() {
        return this.muoto;
    }
    
    public int getVari() {
        return this.vari;
    }
}
