package logica;

/**
 * Created by Bryan on 11/18/2016.
 */
public class MultiShot extends Armas{

    public MultiShot(){
        super(1000);
    }

    @Override
    public String disparar(Matriz tableroJuego, int fila, int columna) {
        String tmp="";
        if(tableroJuego.tablero[fila][columna]==4){
            tableroJuego.hoyoNegro.onHit();
            tmp+="Ha disparado a un hoyo Negro";
        }
        else {
            if (tableroJuego.tablero[fila][columna] != -1) {
                tableroJuego.tablero[fila][columna] = 5;
                tmp+="Ha disparado a un elemento enemigo";
                Misil misil=new Misil();
                for(int i=0; i<4; i++){
                    int filaD=(int) (Math.random()) * 24;
                    int columnaD=(int) (Math.random()) * 24;
                    misil.disparar(tableroJuego, filaD, columnaD);
                }
            }
            else
                tmp+="Ha disparado a un lugar vacio";
        }
        return tmp;
    }
}
