package tetris.pelilogiikka;

public class Kello {

    private int frame;
    private int sekuntti;
    private int minuutti;
    private TetrisPeli peli;

    public Kello() {
        this.frame = 00;
        this.sekuntti = 00;
        this.minuutti = 00;
    }
    
    public Kello(int minuutteja, TetrisPeli peli) {
        this.peli = peli;
        this.frame = 00;
        this.sekuntti = 00;
        this.minuutti = minuutteja;
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
    
    public void pienenna() {
        frame--;
        if(frame <= 0) {
            frame = 59;
            sekuntti--;
            if(sekuntti <= 0) {
                sekuntti = 59;
                if(minuutti == 0) {
                    peli.timeover();
                } else {
                    minuutti--;
                }
            }
        }
    }

    @Override
    public String toString() {
        String min = Integer.toString(minuutti);
        String sec = Integer.toString(sekuntti);
        String frm = Integer.toString(frame);
        if (minuutti < 10) {
            min = "0" + min;
        }
        if (sekuntti < 10) {
            sec = "0" + sec;
        }
        if (frame < 10) {
            frm = "0" + frm;
        }
        return min+"''"+sec+"'"+frm;
    }
    
    public void reset() {
        this.frame=0;
        this.minuutti=8;
        this.sekuntti=0;
    }
    
    public int getMinuutteja() {
        return this.minuutti;
    }
}
