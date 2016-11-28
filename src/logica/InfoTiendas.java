package logica;

import logica.Armas.Armas;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 25-Nov-16 Tiempo: 12:57 PM
 */
public class InfoTiendas {
    public String nombre;
    public String costo;

    //Construcciones
    public int codigoImagenUnidad;
    public int codigoUnidadActual;
    public TiposConstrucciones tipoUnidadActual;
    public int izquierda;
    public int abajo;
    public int diagonal;
    public Construccion unidad;

    //Armas
    public Armas arma;
    public String nombreArma;
    public String costoArma;

    public InfoTiendas(String nombre, String costo, int codigoImagenUnidad, int codigoUnidadActual,
                       TiposConstrucciones tipoUnidadActual, int izquierda, int abajo, int diagonal) {
        this.nombre = nombre;
        this.costo = costo;
        this.codigoImagenUnidad = codigoImagenUnidad;
        this.codigoUnidadActual = codigoUnidadActual;
        this.tipoUnidadActual = tipoUnidadActual;
        this.izquierda = izquierda;
        this.abajo = abajo;
        this.diagonal = diagonal;
    }

    public InfoTiendas(Armas _arma) {
        arma = _arma;
        nombreArma = arma.getNombre();
        costoArma = "" + arma.getCosto();
    }

    //GETTERS Y SETTERS SON NECESARIOS PARA POPULAR EL TABLEVIEW
    public int obtenerCostoInt() {
        return Integer.parseInt(costo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getNombreArma() {
        return nombreArma;
    }

    public void setNombreArma(String nombreArma) {
        this.nombreArma = nombreArma;
    }

    public String getCostoArma() {
        return costoArma;
    }

    public void setCostoArma(String costoArma) {
        this.costoArma = costoArma;
    }
}
