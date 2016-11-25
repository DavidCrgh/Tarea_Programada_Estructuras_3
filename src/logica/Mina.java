package logica;

/**
 * Created by Bryan on 11/19/2016.
 */
public class Mina extends Construccion {
    public long tiempo;
    public int cantidadXCiclo;

    public Mina() {
        super(2, 1000);
        //cantidadGenerada=0;
        cantidadXCiclo = 50;
        tiempo = 50;
    }

    public void setTiempo(long _tiempo) {
        this.tiempo = _tiempo;
    }
}
