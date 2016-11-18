package Utilitarias;

import java.util.Random;

/**
 * Created by Bryan on 11/18/2016.
 */
public class Bomba{
    private int costo;

    public Bomba(){
        costo=2000;
    }

    public String explotar(Matriz tableroJuego, int fila1, int fila2, int fila3,
                           int columna1, int columna2, int columna3){
        Random random=new Random();
        String tmp="";
        if(tableroJuego.tablero[fila1][columna1]==4){

        }
        else {
            if (tableroJuego.tablero[fila1][columna1] != -1) {
                if(random.nextBoolean()){
                    tableroJuego.tablero[fila1][columna1]=5;
                    tableroJuego.tablero[fila1+1][columna1]=5;
                }
                else{
                    tableroJuego.tablero[fila1][columna1]=5;
                    tableroJuego.tablero[fila1][columna1+1]=5;
                }
                tmp += "Bomba1 ha estallado un elemento enemigo";
            }
            if (tableroJuego.tablero[fila2][columna2] != -1) {
                tmp += "Bomba1 ha estallado un elemento enemigo";
            }
            if (tableroJuego.tablero[fila3][columna3] != -1) {
                tmp += "Bomba1 ha estallado un elemento enemigo";
            }
        }
        return tmp;
    }

}
