package logica.Armas;

import logica.Matriz;

import java.io.Serializable;

/**
 * Created by Bryan on 11/18/2016.
 */
public class Armas implements Serializable {
    public String nombre;
    public int costo;
    public int fabricasDisponibles;

    public Armas(String p, int c){
        nombre=p;
        costo=c;
        fabricasDisponibles=1;
    }

    /*public void disparar(Matriz tablero){

    }*/

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}

