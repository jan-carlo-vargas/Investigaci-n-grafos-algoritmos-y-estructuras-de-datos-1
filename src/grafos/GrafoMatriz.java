package grafos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Grafo no dirigido y ponderado representado con una matriz de
 * adyacencia. Espacio: O(V^2). Consultar una arista cuesta O(1) y
 * enumerar los vecinos de un vértice cuesta O(V).
 */
public class GrafoMatriz implements Grafo {

    /** Marca de "no hay arista" (permite pesos 0 o negativos). */
    private static final int SIN_ARISTA = Integer.MAX_VALUE;

    private final int n;
    private final int[][] pesos;

    /** Crea un grafo con n vértices y sin aristas. */
    public GrafoMatriz(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Se requiere n >= 1");
        }
        this.n = n;
        this.pesos = new int[n][n];
        for (int[] fila : pesos) {
            Arrays.fill(fila, SIN_ARISTA);
        }
    }

    @Override
    public int cantidadVertices() {
        return n;
    }

    @Override
    public void agregarArista(int u, int v, int peso) {
        validarVertice(u);
        validarVertice(v);
        if (u == v) {
            throw new IllegalArgumentException("No se permiten lazos");
        }
        pesos[u][v] = peso;
        pesos[v][u] = peso;
    }

    @Override
    public boolean existeArista(int u, int v) {
        validarVertice(u);
        validarVertice(v);
        return pesos[u][v] != SIN_ARISTA;
    }

    /** Recorre la fila u completa: O(V). */
    @Override
    public List<Arista> vecinos(int u) {
        validarVertice(u);
        List<Arista> resultado = new ArrayList<>();
        for (int v = 0; v < n; v++) {
            if (pesos[u][v] != SIN_ARISTA) {
                resultado.add(new Arista(u, v, pesos[u][v]));
            }
        }
        return resultado;
    }

    /** Recorre el triángulo superior de la matriz: O(V^2). */
    @Override
    public List<Arista> aristas() {
        List<Arista> resultado = new ArrayList<>();
        for (int u = 0; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                if (pesos[u][v] != SIN_ARISTA) {
                    resultado.add(new Arista(u, v, pesos[u][v]));
                }
            }
        }
        return resultado;
    }

    /** Devuelve la matriz como texto; '-' indica ausencia de arista. */
    public String mostrar(String[] nombres) {
        StringBuilder sb = new StringBuilder("    ");
        for (int j = 0; j < n; j++) {
            sb.append(String.format("%4s", nombres[j]));
        }
        sb.append('\n');
        for (int i = 0; i < n; i++) {
            sb.append(String.format("%-4s", nombres[i]));
            for (int j = 0; j < n; j++) {
                String celda = pesos[i][j] == SIN_ARISTA
                        ? "-" : String.valueOf(pesos[i][j]);
                sb.append(String.format("%4s", celda));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
