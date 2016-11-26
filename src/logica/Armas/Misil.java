package logica.Armas;

import logica.Matriz;

/**
 * Created by Bryan on 11/18/2016.
 */
public class Misil extends Armas {

    public Misil(){
        super("Misil", 500);
    }

    public void disparar(Matriz tableroJuego, int fila, int columna) {
        if(tableroJuego.tablero[fila][columna]==4){
            //tableroJuego.hoyoNegro.onHit();
        }
        else {
            if (tableroJuego.tablero[fila][columna] != -1) {
                tableroJuego.tablero[fila][columna] = 5;
            }
        }
    }
}
