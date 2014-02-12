package tetris.objects;

/**
 * Palikka -objekti johon on liitetty kaantymis -ominaisuus. 
 * Kaannoksiin liittyvat muodot haetaan olioon liitetysta Tetromino -enumista.
 * 
 * @author zaarock
 */
public class KaantyvaPalikka extends Palikka {

    private Tetromino tetromino;
    private int rotaatio;
    private int maxRotaatio;
    
    
    // palikka johon on liitetty kaantymis -ominaisuus. 
//     kaantymismuodot haetaan liitetysta Tetrominosta
    
    /**
     * Luo uuden palikan ja asettaa pisteet annetun Tetrominon avulla. 
     * Konstruktori tarkistaa tetrominosta myos kaannos-asentojen maaran.
     * 
     * @param tetromino Tetromino - objekti jonka perusteella pisteet asetetaan.
     */
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
    
    /**
     * Kopioi annetun KaantyvaPalikka -olion.
     * 
     * @param kopioitava Kopioitava olio
     */
    public KaantyvaPalikka(KaantyvaPalikka kopioitava) {
        super(kopioitava.tetromino.getPisteet(kopioitava.getRotaatio()), kopioitava.getXpos(),kopioitava.getYpos(),kopioitava.tetromino.getVari());
        this.tetromino = kopioitava.tetromino;
        this.rotaatio = kopioitava.rotaatio;
        this.maxRotaatio = kopioitava.maxRotaatio;
    }

    /**
     * Palauttaa koordinaateista koostuvan muodon jossa palikka on kaannetty 90 astetta vasemmalle.
     * 
     * @return Palikan pisteiden koordinaatit jos toteutetaan vasen kaannos.
     */
    public int[] vasenKaannos() {
        if (this.rotaatio <= 1) {
            this.rotaatio = maxRotaatio;
        } else {
            this.rotaatio = this.rotaatio -1;
        }
        return this.tetromino.getPisteet(rotaatio);
    }

    /**
     * Palauttaa koordinaateista koostuvan muodon jossa palikka on kaannetty 90 astetta oikealle.
     * 
     * @return Palikan pisteiden koordinaatit jos toteutetaan oikea kaannos.
     */
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

    /**
     * Kaantaa palikkaa 90-astetta vasemmalle.
     */
    public void kaannaVasemmalle() {
        super.setMuoto(vasenKaannos());
     }

    /**
     * Kaantaa palikkaa 90-astetta oikealle.
     */
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
