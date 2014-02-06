package tetris.objects;

public enum Tetromino {

    I(new int[][]{{0, 0, 1, 0, 2, 0, 3, 0}, {0, 1, 1, 1, 2, 1, 3, 1}, {2, 0, 2, 1, 2, 2, 2, 3}}, Vari.RED),
    O(new int[][]{{1, 0, 2, 0, 1, 1, 2, 1}}, Vari.YELLOW),
    T(new int[][]{{0, 0, 1, 0, 2, 0, 1, 1}, {0, 1, 1, 1, 2, 1, 1, 2}, {1, 0, 0, 1, 1, 1, 1, 2},
{1, 1, 0, 2, 1, 2, 2, 2}, {1, 0, 1, 1, 2, 1, 1, 2}}, Vari.CYAN),
    L(new int[][]{{0, 0, 1, 0, 2, 0, 0, 1}, {0, 1, 1, 1, 2, 1, 0, 2}, {0, 0, 1, 0, 1, 1, 1, 2},
{2, 1, 0, 2, 1, 2, 2, 2}, {1, 0, 1, 1, 1, 2, 2, 2}}, Vari.ORANGE),
    J(new int[][]{{0, 0, 1, 0, 2, 0, 2, 1}, {0, 1, 1, 1, 2, 1, 2, 2}, {1, 0, 1, 1, 0, 2, 1, 2},
{0, 1, 0, 2, 1, 2, 2, 2}, {1, 0, 1, 1, 0, 2, 1, 2}}, Vari.BLUE),
    S(new int[][]{{1, 0, 2, 0, 0, 1, 1, 1}, {1, 1, 2, 1, 0, 2, 1, 2}, {0, 0, 0, 1, 1, 1, 1, 2}}, Vari.MAGENTA),
    Z(new int[][]{{0, 0, 1, 0, 1, 1, 2, 1}, {0, 1, 1, 1, 1, 2, 2, 2}, {2, 0, 1, 1, 2, 1, 1, 2}}, Vari.LIME);
    private int[][] rotaatioJaMuoto; // palikan asento ja sita vastaava tetris-palikan esitys numerosarjana joissa jokainen lukupari on yhden pisteen x ja y -koordinaatti
    private Vari vari; // tetris-palikan vari

    private Tetromino(int[][] rotaatioJaMuoto, Vari vari) {
        this.rotaatioJaMuoto = rotaatioJaMuoto;
        this.vari = vari;
    }

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
