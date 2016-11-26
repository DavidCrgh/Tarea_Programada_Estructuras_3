package logica;

import logica.Armas.Misil;

/**
 * Created by Bryan on 11/18/2016.
 */
public class HoyoNegro {
    public Matriz tableroJuego;

    public HoyoNegro(Matriz _tableroJuego){
        tableroJuego=_tableroJuego;
    }

    public void onHit(){
        for(int i=0; i<3; i++) {
            Misil misil = new Misil();
            int fila = (int) (Math.random()) * 24;
            int columna = (int) (Math.random()) * 24;
            misil.disparar(tableroJuego, fila, columna);
        }
    }
}
