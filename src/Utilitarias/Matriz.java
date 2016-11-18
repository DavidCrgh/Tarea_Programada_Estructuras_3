package Utilitarias;

/**
 * Created by Bryan on 11/18/2016.
 */
public class Matriz {
    public  int [] [] tablero;
    public HoyoNegro hoyoNegro;

    public Matriz(){
        int contador=24;
        while(contador>=0){
            tablero[contador][contador]=-1;
            contador--;
        }
        for(int i = 0; i<2; i++) {
            int fila = (int) (Math.random()) * 24;
            int columna = (int) (Math.random()) * 24;
            tablero[fila][columna]=4;
        }
        hoyoNegro=new HoyoNegro(this);
    }

    public boolean posicionarObjeto(tiposConstrucciones tipo, int fila, int columna){
        if(tipo==tiposConstrucciones.MUNDO){
            if(tablero[fila][columna]==-1 && tablero[fila+1][columna]==-1
                    && tablero[fila][columna+1]==-1 && tablero[fila+1][columna+1]==-1){
                tablero[fila][columna]=0;
                tablero[fila+1][columna]=0;
                tablero[fila][columna+1]=0;
                tablero[fila+1][columna+1]=0;
                return true;
            }
            else
                return false;
        }
        if(tipo==tiposConstrucciones.CONECTOR){
            if(tablero[fila][columna]==-1){
                tablero[fila][columna]=1;
                return true;
            }
            else
                return false;
        }
        if(tipo==tiposConstrucciones.FABRICA2x1){
            if(tablero[fila][columna]==-1 && tablero[fila+1][columna]==-1){
                tablero[fila][columna]=2;
                tablero[fila+1][columna]=2;
                return true;
            }
            else
                return false;
        }
        if(tipo==tiposConstrucciones.FABRICA1x2){
            if(tablero[fila][columna]==-1 && tablero[fila+1][columna]==-1){
                tablero[fila][columna]=3;
                tablero[fila][columna+1]=3;
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

}
