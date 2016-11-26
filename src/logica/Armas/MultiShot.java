package logica.Armas;

import logica.Matriz;

/**
 * Created by Bryan on 11/18/2016.
 */
public class MultiShot extends Armas {

    public MultiShot(){
        super("MultiShot", 1000);
    }

    public void disparar(Matriz tableroJuego, int fila, int columna) {
        if(tableroJuego.tablero[fila][columna]==4){
            //tableroJuego.hoyoNegro.onHit();
        }
        else {
            if (tableroJuego.tablero[fila][columna] != -1) {
                tableroJuego.tablero[fila][columna] = 5;
                Misil misil=new Misil();
                for(int i=0; i<4; i++){
                    int filaD=(int) (Math.random()) * 24;
                    int columnaD=(int) (Math.random()) * 24;
                    misil.disparar(tableroJuego, filaD, columnaD);
                }
            }
        }
    }
}
