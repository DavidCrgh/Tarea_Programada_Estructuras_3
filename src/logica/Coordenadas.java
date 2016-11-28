package logica;

import java.io.Serializable;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 25-Nov-16 Tiempo: 6:16 PM
 */
public class Coordenadas implements Serializable {
    public int x;
    public int y;

    public Coordenadas(int _x, int _y) {
        x = _x;
        y = _y;
    }
}
