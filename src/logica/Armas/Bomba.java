package logica.Armas;

import logica.Matriz;

import java.util.Random;

/**
 * Created by Bryan on 11/18/2016.
 */
public class Bomba extends Armas {

    public Bomba(){
        super("Bomba", 2000);
    }

    public void disparar(Matriz tableroJuego, int fila, int columna){
        Random random=new Random();
        if(random.nextBoolean()){
            tableroJuego.tablero[fila][columna]=5;
            tableroJuego.tablero[fila+1][columna]=5;
        }
        else{
            tableroJuego.tablero[fila][columna]=5;
            tableroJuego.tablero[fila][columna+1]=5;
        }
    }

    public String explotar(Matriz tableroJuego, int fila1, int fila2, int fila3,
                           int columna1, int columna2, int columna3){
        String tmp="";
        if(tableroJuego.tablero[fila1][columna1]==4){
            //tableroJuego.hoyoNegro.onHit();
            tmp+="Ha disparado a un hoyo Negro";
        }
        else {
            if (tableroJuego.tablero[fila1][columna1] != -1) {
                disparar(tableroJuego, fila1, columna1);
                tmp += "Bomba1 ha estallado un elemento enemigo";
            }
            else{
                tmp += "Bomba1 ha fallado";
            }
            if (tableroJuego.tablero[fila2][columna2] != -1) {
                disparar(tableroJuego, fila2, columna2);
                tmp += "Bomba2 ha estallado un elemento enemigo";
            }
            else{
                tmp += "Bomba1 ha fallado";
            }
            if (tableroJuego.tablero[fila3][columna3] != -1) {
                disparar(tableroJuego, fila3, columna3);
                tmp += "Bomba3 ha estallado un elemento enemigo";
            }
            else{
                tmp += "Bomba1 ha fallado";
            }
        }
        return tmp;
    }

}
