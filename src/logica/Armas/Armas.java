package logica.Armas;

/**
 * Created by Bryan on 11/18/2016.
 */
public class Armas {
    public String nombre;
    public int costo;
    public int fabricasDisponibles;

    public Armas(String p, int c){
        nombre=p;
        costo=c;
        fabricasDisponibles=1;
    }

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

