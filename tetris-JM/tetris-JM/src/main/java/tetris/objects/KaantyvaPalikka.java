package tetris.objects;

public class KaantyvaPalikka extends Palikka {

    private Tetromino tetromino;
    private int rotaatio;
    private int maxRotaatio;
    
    public KaantyvaPalikka(Tetromino tetromino, int x, int y, int rotaatio, int maxRotaatio) {
        super(tetromino.getPisteet(0), tetromino.getVari(), x, y);
        this.rotaatio = rotaatio;
        this.maxRotaatio = maxRotaatio;
    }

    public KaantyvaPalikka(Tetromino tetromino) {
        super(tetromino.getPisteet(0), tetromino.getVari(), 0, 0);
        this.rotaatio = 0;
        int i = 0;
        while (tetromino.getPisteet(i) != null) {
            i++;
        }
        this.maxRotaatio = i;
    }

    public int[] vasenKaannos() {
        if (this.rotaatio < 2) {
            this.rotaatio = maxRotaatio;
        } else {
            this.rotaatio--;
        }
        return this.tetromino.getPisteet(rotaatio);
    }

    public int[] oikeaKaannos() {
        if (this.rotaatio == maxRotaatio) {
            this.rotaatio = 1;
        } else {
            this.rotaatio++;
        }
        return this.tetromino.getPisteet(rotaatio);
    }

    public void kaannaVasemmalle(int x) {
        super.setMuoto(vasenKaannos());
        if (x != 0) {
            super.liiku(x, 0);
        }
    }

    public void kaannaOikealle(int x) {
        super.setMuoto(oikeaKaannos());
        if (x != 0) {
            super.liiku(x, 0);
        }
    }
    
    public void kaannaVasemmalle() {
        kaannaVasemmalle(0);
    }
    
    public void kaannaOikealle() {
        kaannaOikealle(0);
    }
    
    public Tetromino getTetromino() {
        return this.tetromino;
    }
    
    public int getRotaatio() {
        return this.rotaatio;
    }
    
    public int getMaxRotaatio() {
        return this.maxRotaatio;
    }
}
