package tetris.objects;

public class KaantyvaPalikka extends Palikka {

    private Tetromino tetromino;
    private int rotaatio;
    private int maxRotaatio;
    
    
    // palikka johon on liitetty kaantymis -ominaisuus. 
//     kaantymismuodot haetaan liitetysta Tetrominosta
    
    public KaantyvaPalikka(Tetromino tetromino) {
        super(tetromino.getPisteet(0), 0, 0, tetromino.getVari());
        this.tetromino = tetromino;
        this.rotaatio = 0;
        int i = 0;
        // tarkistaa tetrominon maksimirotaation
        while (tetromino.getPisteet(i) != null) {
            i++;
        }
        this.maxRotaatio = i-1;
    }
    
    // kopioimiseen tarkoitettu konstruktori
    public KaantyvaPalikka(KaantyvaPalikka kopioitava) {
        super(kopioitava.tetromino.getPisteet(kopioitava.getRotaatio()), kopioitava.getXpos(),kopioitava.getYpos(),kopioitava.tetromino.getVari());
        this.tetromino = kopioitava.tetromino;
        this.rotaatio = kopioitava.rotaatio;
        this.maxRotaatio = kopioitava.maxRotaatio;
    }

    public int[] vasenKaannos() {
        if (this.rotaatio <= 1) {
            this.rotaatio = maxRotaatio;
        } else {
            this.rotaatio = this.rotaatio -1;
        }
        return this.tetromino.getPisteet(rotaatio);
    }

    public int[] oikeaKaannos() {
        if (this.rotaatio == maxRotaatio) {
            this.rotaatio = 1;
        } else if (this.rotaatio == 0) {
            this.rotaatio = 2;
        } else {
            this.rotaatio++;
        }
        return this.tetromino.getPisteet(rotaatio);
    }

    public void kaannaVasemmalle() {
        super.setMuoto(vasenKaannos());
     }

    public void kaannaOikealle() {
        super.setMuoto(oikeaKaannos());
    }
    
    public Tetromino getTetromino() {
        return this.tetromino;
    }
    
    public int getRotaatio() {
        return this.rotaatio;
    }
    
    public void setRotaatio(int rotaatio) {
        this.rotaatio = rotaatio;
    }
    
    public int getMaxRotaatio() {
        return this.maxRotaatio;
    }
}
