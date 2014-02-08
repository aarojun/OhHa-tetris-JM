package tetris.pelilogiikka;

import java.awt.event.ActionEvent;

public interface SyotteenLukija {
    
    public void vasenPush(ActionEvent ae);
    public void vasenRelease(ActionEvent ae);
    
    public void oikeaPush(ActionEvent ae);
    public void oikeaRelease(ActionEvent ae);
    
    public void ylosPush(ActionEvent ae);
    public void ylosRelease(ActionEvent ae);
    
    public void alasPush(ActionEvent ae);
    public void alasRelease(ActionEvent ae);
    
    public void vasenKaannosPush(ActionEvent ae);
    public void vasenKaannosRelease(ActionEvent ae);
    
    public void oikeaKaannosPush(ActionEvent ae);
    public void oikeaKaannosRelease(ActionEvent ae);
    
    public void pausePush(ActionEvent ae);
    public void yesPush(ActionEvent ae);
    public void noPush(ActionEvent ae);
}
