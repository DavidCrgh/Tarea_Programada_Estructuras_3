package logica;

import java.util.ArrayList;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 24-Nov-16 Tiempo: 1:28 PM
 */
public class Grafo {
    public int max;
    public int matriz[][];
    public NodoGrafo vertices[];
    public boolean visitados[];
    public int cantidadVertices;

    public Grafo() {
        matriz = new int[225][225];
        vertices = new NodoGrafo[225];
        visitados = new boolean[225];
        max = 225;
        cantidadVertices = 0;

        for (int i = 0; i < 225; i++) {
            vertices[i] = null;
            visitados[i] = false;
            for (int j = 0; j < 225; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    public void agregarVertice(NodoGrafo vertice, int pos) {
        if (cantidadVertices < max && indexOf(vertice.x, vertice.y) == -1) {
            vertices[pos] = vertice;
            cantidadVertices++;
        }
    }

    public int indexOf(int x, int y) {
        for (int i = 0; i < 225; i++) {
            if (vertices[i] != null && vertices[i].x == x && vertices[i].y == y) {
                return i;
            }
        }
        return -1;
    }

    public void agregarArista(int xOrigen, int yOrigen, int xDestino, int yDestino, int peso) {
        int orig = indexOf(xOrigen, yOrigen);
        int dest = indexOf(xDestino, yDestino);

        if (orig != -1 && dest != -1) {
            matriz[dest][orig] = peso;
        }
    }

    public void removerVertice(int x, int y) {
        vertices[indexOf(x, y)] = null;
        for (int i = 0; i < 225; i++) {
            matriz[indexOf(x, y)][i] = 0;
            matriz[i][indexOf(x, y)] = 0;
        }
    }

    public int obtenerPrimerCampoDisponible() {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<NodoGrafo> encontrarMundo() {
        int i = 0;
        while (i < 225) {
            try {
                Mundo temporal = (Mundo) vertices[i].unidad;
                break;
            } catch (Exception e) {
            }
            i++;
        }
        if (i == 225) {
            return new ArrayList<>();
        }
        return verticesDeUnidad(vertices[i].x, vertices[i].y);
    }

    public ArrayList<NodoGrafo> verticesDeUnidad(int x, int y) {
        ArrayList<NodoGrafo> verticesUnidad = new ArrayList<>();
        int index = indexOf(x, y);
        Construccion unidad = vertices[index].unidad;
        for (int i = 0; i < 225; i++) {
            if (vertices[i] != null && vertices[i].unidad.equals(unidad)) {
                verticesUnidad.add(vertices[i]);
            }
        }
        return verticesUnidad;
    }

    public boolean hayCamino(NodoGrafo a, NodoGrafo b) {
        for (int i = 0; i < 225; i++) {
            visitados[i] = false;
        }
        ArrayList<NodoGrafo> cola = new ArrayList<>();
        visitados[indexOf(a.x, a.y)] = true;
        cola.add(a);

        while (cola.size() != 0) {
            a = cola.get(0);
            cola.remove(0);

            int i = indexOf(a.x, a.y);

            for (int j = 0; j < 225; j++) {
                if (matriz[i][j] != 0) {
                    if (vertices[j] == b) {
                        return true;
                    }

                    if (!visitados[j]) {
                        visitados[j] = true;
                        cola.add(vertices[j]);
                    }
                }
            }
        }
        return false;
    }

    public boolean unidadDisconexa(int x, int y) {
        ArrayList<NodoGrafo> mundo = encontrarMundo();
        ArrayList<NodoGrafo> unidad = verticesDeUnidad(x, y);

        for (int i = 0; i < mundo.size(); i++) {
            for (int j = 0; j < unidad.size(); j++) {
                if (hayCamino(mundo.get(i), unidad.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
