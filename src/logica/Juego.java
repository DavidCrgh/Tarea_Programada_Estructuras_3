package logica;

import aplicacion.cliente.interfaz.Utilitario;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 25-Nov-16 Tiempo: 12:44 PM
 */
public class Juego {
    public boolean modoInicial;
    public boolean modoConstruccion;
    public boolean modoAtaque;

    public int codigoImagenUnidad;
    public int codigoUnidadActual;
    public TiposConstrucciones tipoUnidadActual;
    public int izquierda;
    public int abajo;
    public int diagonal;

    public Matriz matrizPropia;
    public Grafo grafoPropio;

    public Juego() {
        modoInicial = true;
        modoConstruccion = true;
        modoAtaque = false;

        matrizPropia = new Matriz();
        grafoPropio = new Grafo();

        codigoImagenUnidad = 1;
        codigoUnidadActual = 1;
        tipoUnidadActual = TiposConstrucciones.MUNDO;
        izquierda = 1;
        abajo = 15;
        diagonal = 16;
    }

    public void desactivarModoInicial(int j) {
        int[] coordenadas = Utilitario.determinarCoordenadas(j);
        matrizPropia.posicionarObjeto(tipoUnidadActual, coordenadas[0], coordenadas[1], 0);
        modoInicial = false;
        izquierda = 1;
        abajo = 0;
        diagonal = 0;
        codigoImagenUnidad = 3;
        codigoUnidadActual = 3;
        tipoUnidadActual = TiposConstrucciones.FABRICA1x2;
    }

    public void activarModoConstruccion(InfoTiendas unidad) {
        modoConstruccion = true;
        codigoImagenUnidad = unidad.codigoImagenUnidad;
        codigoUnidadActual = unidad.codigoUnidadActual;
        tipoUnidadActual = unidad.tipoUnidadActual;
        izquierda = unidad.izquierda;
        abajo = unidad.abajo;
        diagonal = unidad.diagonal;
    }

    public void desactivarModoConstruccion() {
        modoConstruccion = false;
    }

    public void construirUnidad(int j) {
        int[] coordenadas = Utilitario.determinarCoordenadas(j);
        matrizPropia.posicionarObjeto(tipoUnidadActual, coordenadas[0], coordenadas[1], codigoUnidadActual);
    }
}
