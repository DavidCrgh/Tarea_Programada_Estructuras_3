package Utilitarias;

/**
 * Created by Bryan on 11/18/2016.
 */
public abstract class Armas {
    int costo;

    public Armas(int _costo){
        costo=_costo;
    }

    public abstract String disparar(Matriz tableroJuego, int fila, int columna);
}

