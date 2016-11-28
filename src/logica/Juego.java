package logica;

import aplicacion.cliente.interfaz.ControladorPrincipalJuego;
import aplicacion.cliente.interfaz.Utilitario;
import logica.Armas.Armas;

import java.util.ArrayList;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 25-Nov-16 Tiempo: 12:44 PM
 */
public class Juego {
    public boolean modoInicial;
    public boolean modoConstruccion;
    public boolean modoAtaque;
    public boolean modoConexion;
    public int[] coordenadasConector;
    public boolean enTurno;
    public ControladorPrincipalJuego controlador;
    public int codigoImagenUnidad;
    public int codigoUnidadActual;
    public TiposConstrucciones tipoUnidadActual;
    public int izquierda;
    public int abajo;
    public int diagonal;
    public ArrayList<Matriz> matricesEnemigos;
    public Matriz matrizPropia;
    public Grafo grafoPropio;
    public ArrayList<Armas> armasDisponibles;
    public ArrayList<ThreadMina> minas;

    public Juego() {
        modoInicial = true;
        modoConstruccion = true;
        modoAtaque = false;
        modoConexion = false;
        coordenadasConector = new int[2];
        coordenadasConector[0] = coordenadasConector[1] = -1;
        enTurno = false;
        minas=new ArrayList<>();
        matrizPropia = new Matriz();
        grafoPropio = new Grafo();
        matricesEnemigos = new ArrayList<>();

        codigoImagenUnidad = 1;
        codigoUnidadActual = 1;
        tipoUnidadActual = TiposConstrucciones.MUNDO;
        izquierda = 1;
        abajo = 15;
        diagonal = 16;
        armasDisponibles=new ArrayList<>();
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

    public void activarModoAtaque(InfoTiendas arma) {
        modoAtaque = true;

    }

    public void construirUnidad(int j) {
        int[] coordenadas = Utilitario.determinarCoordenadas(j);
        int x = coordenadas[0];
        int y = coordenadas[1];
        matrizPropia.posicionarObjeto(tipoUnidadActual, x, y, codigoUnidadActual);
        Construccion unidad = null;
        switch (codigoUnidadActual) { //Ver tabla de valores en Matriz.java
            case 1:
                unidad = new Mundo();
                break;
            case 2:
                unidad = new Conector();
                break;
            case 3:
                unidad = new Mercado();
                break;
            case 4:
                unidad = new Mina();
                break;
            case 5:
                unidad = new Armeria();
                break;
        }
        matrizPropia.posicionarEnGrafo(grafoPropio, tipoUnidadActual, x, y, unidad);
    }

    public void conectarUnidades(int xDestino, int yDestino) {
        grafoPropio.agregarArista(coordenadasConector[0], coordenadasConector[1], xDestino, yDestino, 1000);
        grafoPropio.agregarArista(xDestino, yDestino, coordenadasConector[0], coordenadasConector[1], 1000);
        int index = grafoPropio.indexOf(coordenadasConector[0], coordenadasConector[1]);
        Conector conector = (Conector) grafoPropio.vertices[index].unidad;
        conector.conexiones.add(new Coordenadas(xDestino, yDestino));
        coordenadasConector[0] = coordenadasConector[1] = -1;
        modoConexion = false;
    }

    public ArrayList<Coordenadas> obtenerCasillasDisconexas() {
        ArrayList<Coordenadas> casillas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (matrizPropia.tablero[i][j] != 0 && matrizPropia.tablero[i][j] != 7 &&
                        matrizPropia.tablero[i][j] != 8 && matrizPropia.tablero[i][j] != 1) {
                    if (grafoPropio.unidadDisconexa(i, j)) {
                        casillas.add(new Coordenadas(i, j));
                    }
                }
            }
        }
        return casillas;
    }

    public void agregarArma(Armas newArma){
        Armas aux=null;
        for (Armas armasDisponible : armasDisponibles) {
            if(armasDisponible.getNombre().equals(newArma.getNombre())){
                armasDisponible.fabricasDisponibles+=1;
                aux=armasDisponible;
            }
        }
        if(aux==null)
            this.armasDisponibles.add(newArma);
    }

    public ArrayList getDisponibles(){
        ArrayList<Armas> aux = new ArrayList<>();
        for (Armas armasDisponible : armasDisponibles) {
            aux.add((Armas)armasDisponible);
        }
        return aux;
    }

    public void removerArma(Armas arma){
        for (Armas armasDisponible : armasDisponibles) {
            if(armasDisponible.getNombre().equals(arma.getNombre())){
                armasDisponible.fabricasDisponibles-=1;
                if(armasDisponible.fabricasDisponibles<=0){
                    this.armasDisponibles.remove(armasDisponible);
                }
            }
        }
    }

    public void agregarNuevaMina(){
        ThreadMina threadMina = new ThreadMina(controlador);
        this.minas.add(threadMina);
        threadMina.start();
    }
}
