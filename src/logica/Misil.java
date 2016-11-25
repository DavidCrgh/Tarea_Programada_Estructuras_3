package logica;

/**
 * Created by Bryan on 11/18/2016.
 */
public class Misil extends Armas{

    public Misil(){
        super(500);
    }
    @Override
    public String disparar(Matriz tableroJuego, int fila, int columna) {
        String tmp="";
        if(tableroJuego.tablero[fila][columna]==4){
            //tableroJuego.hoyoNegro.onHit();
            tmp+="Ha disparado a un hoyo Negro";
        }
        else {
            if (tableroJuego.tablero[fila][columna] != -1) {
                tableroJuego.tablero[fila][columna] = 5;
                tmp+="Ha disparado a un elemento enemigo";
            }
            else
                tmp+="Ha disparado a un lugar vacio";
        }
        return tmp;
    }
}
