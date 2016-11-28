package logica;

import java.io.Serializable;

/**
 * Created by Bryan on 11/18/2016.
 */
/*
Codigos de casillas:
0 - Nada
1 - Mundo
2 - Conector
3 - Mercado
4 - Mina
5 - Armeria
6 - Templo
7 - Hoyo Negro
8 - Quemado
 */
public class Matriz implements Serializable {
    public  int [] [] tablero;

    public Matriz(){
        tablero = new int[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                tablero[i][j] = 0;
            }
        }
        /*int contador=24;
        while(contador>=0){
            tablero[contador][contador]=-1;
            contador--;
        }
        for(int i = 0; i<2; i++) {
            int fila = (int) (Math.random()) * 24;
            int columna = (int) (Math.random()) * 24;
            tablero[fila][columna]=4;
        }*/
    }

    /*public void colocarHoyosNegros(){
        int x = ThreadLocalRandom.current().nextInt(0, 15);
        int y = ThreadLocalRandom.current().nextInt(0, 15);
        tablero[x][y] = 7;

        while(true){
            x = ThreadLocalRandom.current().nextInt(0, 15);
            y = ThreadLocalRandom.current().nextInt(0, 15);
            if(tablero[x][y] == 0){
                tablero[x][y] = 7;
                break;
            }
        }
    }*/

    public boolean posicionarObjeto(TiposConstrucciones tipo, int fila, int columna, int codigo) {
        switch (tipo) {
            case MUNDO:
                if (tablero[fila][columna] == 0 && tablero[fila + 1][columna] == 0
                        && tablero[fila][columna + 1] == 0 && tablero[fila + 1][columna + 1] == 0) {
                    tablero[fila][columna] = 1;
                    tablero[fila + 1][columna] = 1;
                    tablero[fila][columna + 1] = 1;
                    tablero[fila + 1][columna + 1] = 1;
                    return true;
                } else {
                    return false;
                }
            case CONECTOR:
                if (tablero[fila][columna] == 0) {
                    tablero[fila][columna] = 2;
                    return true;
                } else {
                    return false;
                }
            case FABRICA2x1:
                if (tablero[fila][columna] == 0 && tablero[fila + 1][columna] == 0) {
                    tablero[fila][columna] = codigo;
                    tablero[fila + 1][columna] = codigo;
                    return true;
                } else {
                    return false;
                }
            case FABRICA1x2:
                if (tablero[fila][columna] == 0 && tablero[fila][columna + 1] == 0) {
                    tablero[fila][columna] = codigo;
                    tablero[fila][columna + 1] = codigo;
                    return true;
                } else {
                    return false;
                }
        }
        if (tipo.equals(TiposConstrucciones.ARMERIA1) || tipo.equals(TiposConstrucciones.ARMERIA2) ||
                tipo.equals(TiposConstrucciones.ARMERIA3) || tipo.equals(TiposConstrucciones.ARMERIA4)) {
            if (tablero[fila][columna] == 0 && tablero[fila][columna + 1] == 0) {
                tablero[fila][columna] = codigo;
                tablero[fila][columna + 1] = codigo;
                return true;
            } else {
                return false;
            }
        } else if (tipo.equals(TiposConstrucciones.ARMERIA5) || tipo.equals(TiposConstrucciones.ARMERIA6) ||
                tipo.equals(TiposConstrucciones.ARMERIA7) || tipo.equals(TiposConstrucciones.ARMERIA8)) {
            if (tablero[fila][columna] == 0 && tablero[fila + 1][columna] == 0) {
                tablero[fila][columna] = codigo;
                tablero[fila + 1][columna] = codigo;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void posicionarEnGrafo(Grafo grafo, TiposConstrucciones tipo, int fila, int columna, Construccion unidad) {
        switch (tipo) {
            case MUNDO:
                grafo.agregarVertice(new NodoGrafo(fila, columna, unidad), grafo.obtenerPrimerCampoDisponible());
                grafo.agregarVertice(new NodoGrafo(fila + 1, columna, unidad), grafo.obtenerPrimerCampoDisponible());
                grafo.agregarVertice(new NodoGrafo(fila, columna + 1, unidad), grafo.obtenerPrimerCampoDisponible());
                grafo.agregarVertice(new NodoGrafo(fila + 1, columna + 1, unidad), grafo.obtenerPrimerCampoDisponible());
                break;
            case CONECTOR:
                grafo.agregarVertice(new NodoGrafo(fila, columna, unidad), grafo.obtenerPrimerCampoDisponible());
                break;
            case FABRICA2x1:
                grafo.agregarVertice(new NodoGrafo(fila, columna, unidad), grafo.obtenerPrimerCampoDisponible());
                grafo.agregarVertice(new NodoGrafo(fila + 1, columna, unidad), grafo.obtenerPrimerCampoDisponible());
                break;
            case FABRICA1x2:
                grafo.agregarVertice(new NodoGrafo(fila, columna, unidad), grafo.obtenerPrimerCampoDisponible());
                grafo.agregarVertice(new NodoGrafo(fila, columna + 1, unidad), grafo.obtenerPrimerCampoDisponible());
                break;
        }
        if (tipo.equals(TiposConstrucciones.ARMERIA1) || tipo.equals(TiposConstrucciones.ARMERIA2) ||
                tipo.equals(TiposConstrucciones.ARMERIA3) || tipo.equals(TiposConstrucciones.ARMERIA4)) {
            grafo.agregarVertice(new NodoGrafo(fila, columna, unidad), grafo.obtenerPrimerCampoDisponible());
            grafo.agregarVertice(new NodoGrafo(fila + 1, columna, unidad), grafo.obtenerPrimerCampoDisponible());
        } else if (tipo.equals(TiposConstrucciones.ARMERIA5) || tipo.equals(TiposConstrucciones.ARMERIA6) ||
                tipo.equals(TiposConstrucciones.ARMERIA7) || tipo.equals(TiposConstrucciones.ARMERIA8)) {
            grafo.agregarVertice(new NodoGrafo(fila, columna, unidad), grafo.obtenerPrimerCampoDisponible());
            grafo.agregarVertice(new NodoGrafo(fila, columna + 1, unidad), grafo.obtenerPrimerCampoDisponible());

        }
    }

    public void imprimirMatriz() {
        System.out.println("Estado de matriz: ");
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }
}
