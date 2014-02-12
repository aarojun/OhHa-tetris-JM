package tetris.objects;

/**
 * Tetris -pelin peruspalikka. Tetrominot koostuvat neljasta pisteesta jotka ovat toisiinsa liittyneita.
 * Tuntee kaikkien tetrominoiden kaannosten muodot seka palikkaan liitetyn varin.
 * 
 * rotaatio 0 = alkurotaatio
 * rotaatio 1 = perusrotaatio (sama kuin alkurotaatio)
 * rotaatiot 2-n = rotaatiot 2:sta viimeiseen.
 * rotaatiota n seuraa perusrotaatio.
 * 
 * @author zaarock
 */
public enum Tetromino {

    I(new int[][]{{0, 0, 1, 0, 2, 0, 3, 0}, {0, 1, 1, 1, 2, 1, 3, 1}, {2, 0, 2, 1, 2, 2, 2, 3}}, Vari.RED),
    O(new int[][]{{1, 0, 2, 0, 1, 1, 2, 1}}, Vari.YELLOW),
    T(new int[][]{{0, 0, 1, 0, 2, 0, 1, 1}, {0, 1, 1, 1, 2, 1, 1, 2}, {1, 0, 0, 1, 1, 1, 1, 2},
{1, 1, 0, 2, 1, 2, 2, 2}, {1, 0, 1, 1, 2, 1, 1, 2}}, Vari.CYAN),
    L(new int[][]{{0, 0, 1, 0, 2, 0, 0, 1}, {0, 1, 1, 1, 2, 1, 0, 2}, {0, 0, 1, 0, 1, 1, 1, 2},
{2, 1, 0, 2, 1, 2, 2, 2}, {1, 0, 1, 1, 1, 2, 2, 2}}, Vari.ORANGE),
    J(new int[][]{{0, 0, 1, 0, 2, 0, 2, 1}, {0, 1, 1, 1, 2, 1, 2, 2}, {1, 0, 1, 1, 0, 2, 1, 2},
{0, 1, 0, 2, 1, 2, 2, 2}, {1, 0, 2, 0, 1, 1, 1, 2}}, Vari.BLUE),
    S(new int[][]{{1, 0, 2, 0, 0, 1, 1, 1}, {1, 1, 2, 1, 0, 2, 1, 2}, {0, 0, 0, 1, 1, 1, 1, 2}}, Vari.MAGENTA),
    Z(new int[][]{{0, 0, 1, 0, 1, 1, 2, 1}, {0, 1, 1, 1, 1, 2, 2, 2}, {2, 0, 1, 1, 2, 1, 1, 2}}, Vari.LIME);
    private int[][] rotaatioJaMuoto; // palikan asento ja sita vastaava tetris-palikan esitys numerosarjana joissa jokainen lukupari on yhden pisteen x ja y -koordinaatti
    private Vari vari; // tetris-palikan vari

    private Tetromino(int[][] rotaatioJaMuoto, Vari vari) {
        this.rotaatioJaMuoto = rotaatioJaMuoto;
        this.vari = vari;
    }

    /**
     * Palauttaa tetrominon muodon pisteet annetulle tetrominon rotaatiolle
     * @param rotaatio haluttava tetrominopalikan asento 
     * @return tetrominopalikkaa vatsaavat pisteet taulukkona jossa jokainen parillinen luku on x-koordinaatti ja sita seuraava pariton y-koordinaatti.
     */
    public int[] getPisteet(int rotaatio) {
        if (rotaatio < this.rotaatioJaMuoto.length) {
            return this.rotaatioJaMuoto[rotaatio];
        } else {
            return null;
        }
    }

    public Vari getVari() {
        return this.vari;
    }
}
