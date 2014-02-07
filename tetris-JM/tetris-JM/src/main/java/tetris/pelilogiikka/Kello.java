package tetris.pelilogiikka;

public class Kello {

    private int frame;
    private int sekuntti;
    private int minuutti;

    public Kello() {
        this.frame = 00;
        this.sekuntti = 00;
        this.minuutti = 00;
    }

    public void kasvata() {
        frame++;
        if (frame == 60) {
            frame = 0;
            sekuntti++;
            if (sekuntti == 60) {
                sekuntti = 0;
                minuutti++;
            }
        }
    }

    @Override
    public String toString() {
        String min = Integer.toString(minuutti);
        String sec = Integer.toString(sekuntti);
        if (minuutti < 10) {
            min = "0" + min;
        }
        if (sekuntti < 10) {
            sec = "0" + sec;
        }
        return min+":"+sec;
    }
    
    public void reset() {
        this.frame=0;
        this.minuutti=0;
        this.sekuntti=0;
    }
}
