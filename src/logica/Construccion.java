package logica;

/**
 * Created by Bryan on 11/18/2016.
 */
public class Construccion {
    private boolean destruido;
    private int tamanno;
    private int costo;

    public Construccion(int _tamanno, int _costo) {
        destruido=false;
        tamanno=_tamanno;
        costo=_costo;
    }

    public void setDestruido(boolean estado){
        destruido=estado;
    }
}
