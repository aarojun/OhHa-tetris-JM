package tetris.objects;

public enum Tetromino {

    I(new int[][]{{0, 0, 1, 0, 2, 0, 3, 0}, {0, 1, 1, 1, 2, 1, 3, 1}, {2, 0, 2, 1, 2, 2, 2, 3}}, 1),
    O(new int[][]{{1, 0, 2, 0, 1, 1, 2, 1}}, 2),
    T(new int[][]{{0, 0, 1, 0, 2, 0, 1, 1}, {0, 1, 1, 1, 2, 1, 1, 2}, {1, 0, 0, 1, 1, 1, 1, 2},
{1, 1, 0, 2, 1, 2, 2, 2}, {1, 0, 1, 1, 2, 1, 1, 2}}, 3),
    L(new int[][]{{0, 0, 1, 0, 2, 0, 0, 1}, {0, 1, 1, 1, 2, 1, 0, 2}, {0, 0, 1, 0, 1, 1, 1, 2},
{2, 1, 0, 2, 1, 2, 2, 2}, {1, 0, 1, 1, 1, 2, 2, 2}}, 4),
    J(new int[][]{{0, 0, 1, 0, 2, 0, 2, 1}, {0, 1, 1, 1, 2, 1, 2, 2}, {1, 0, 1, 1, 0, 2, 1, 2},
{0, 1, 0, 2, 1, 2, 2, 2}, {1, 0, 1, 1, 0, 2, 1, 2}}, 5),
    S(new int[][]{{1, 0, 2, 0, 0, 1, 1, 1}, {1, 1, 2, 1, 0, 2, 1, 2}, {0, 0, 0, 1, 1, 1, 1, 2}}, 6),
    Z(new int[][]{{0, 0, 1, 0, 1, 1, 2, 1}, {0, 1, 1, 1, 1, 2, 2, 2}, {2, 0, 1, 1, 2, 1, 1, 2}}, 7);
    private int[][] rotaatioJaMuoto; // rotaatio ja sita vastaava tetris-palikan esitys numerosarjana joissa jokainen lukupari on yhden pisteen x ja y -koordinaatti
    private int vari; // tetris-palikan vari

    private Tetromino(int[][] rotaatioJaMuoto, int vari) {
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

    public int getVari() {
        return this.vari;
    }
}
