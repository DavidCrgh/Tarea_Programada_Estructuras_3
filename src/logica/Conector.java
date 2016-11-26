package logica;

import java.util.ArrayList;

/**
 * Created by Bryan on 11/18/2016.
 */
public class Conector extends Construccion {

    public ArrayList<Coordenadas> conexiones;

    public Conector(){
        super(1,100);
        conexiones = new ArrayList<>();
    }

    public String obtenerConexiones() {
        String salida = "";
        for (int i = 0; i < conexiones.size(); i++) {
            salida += i + " - ";
            salida += "X: " + conexiones.get(i).x;
            salida += " Y: " + conexiones.get(i).y + "\n";
        }
        return salida;
    }

    public void agregarCoordenadas(int x, int y) {
        for (Coordenadas punto : conexiones) {
            if (punto.x == x & punto.y == y) {
                return;
            }
        }
        conexiones.add(new Coordenadas(x, y));
    }
}
