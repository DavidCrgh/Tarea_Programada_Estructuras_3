package Utilitarias;

/**
 * Created by Bryan on 11/18/2016.
 */
public class Construcciones {
    private boolean destruido;
    private int tamanno;
    private int costo;

    public Construcciones(int _tamanno, int _costo){
        destruido=false;
        tamanno=_tamanno;
        costo=_costo;
    }

    public void setDestruido(boolean estado){
        destruido=estado;
    }
}
