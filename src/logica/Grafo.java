package logica;

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
            if (vertices[i].x == x && vertices[i].y == y) {
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
}
