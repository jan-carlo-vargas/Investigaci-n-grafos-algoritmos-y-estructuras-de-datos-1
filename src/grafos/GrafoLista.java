package grafos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Grafo no dirigido y ponderado representado con listas de adyacencia.
 * Espacio: O(V + E). Enumerar los vecinos de u cuesta O(grado(u)) y
 * consultar una arista cuesta O(grado(u)).
 *
 * Cada lista se mantiene ordenada por destino para que los recorridos
 * sean deterministas.
 */
public class GrafoLista implements Grafo {

    private final int n;
    private final List<List<Arista>> adyacencia;

    /** Crea un grafo con n vértices y sin aristas. */
    public GrafoLista(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Se requiere n >= 1");
        }
        this.n = n;
        this.adyacencia = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adyacencia.add(new ArrayList<>());
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
        insertarOrdenado(u, new Arista(u, v, peso));
        insertarOrdenado(v, new Arista(v, u, peso));
    }

    /** Inserta (o reemplaza) la arista manteniendo el orden por destino. */
    private void insertarOrdenado(int u, Arista nueva) {
        List<Arista> lista = adyacencia.get(u);
        int i = 0;
        while (i < lista.size() && lista.get(i).destino() < nueva.destino()) {
            i++;
        }
        if (i < lista.size() && lista.get(i).destino() == nueva.destino()) {
            lista.set(i, nueva);
        } else {
            lista.add(i, nueva);
        }
    }

    @Override
    public boolean existeArista(int u, int v) {
        validarVertice(u);
        validarVertice(v);
        for (Arista a : adyacencia.get(u)) {
            if (a.destino() == v) {
                return true;
            }
        }
        return false;
    }

    /** Devuelve la lista de u sin copiarla: O(1). */
    @Override
    public List<Arista> vecinos(int u) {
        validarVertice(u);
        return Collections.unmodifiableList(adyacencia.get(u));
    }

    /** Recorre todas las listas: O(V + E). */
    @Override
    public List<Arista> aristas() {
        List<Arista> resultado = new ArrayList<>();
        for (int u = 0; u < n; u++) {
            for (Arista a : adyacencia.get(u)) {
                if (u < a.destino()) {
                    resultado.add(a);
                }
            }
        }
        return resultado;
    }

    /** Devuelve las listas de adyacencia como texto. */
    public String mostrar(String[] nombres) {
        StringBuilder sb = new StringBuilder();
        for (int u = 0; u < n; u++) {
            sb.append(nombres[u]).append(" ->");
            for (Arista a : adyacencia.get(u)) {
                sb.append(' ').append(nombres[a.destino()])
                  .append('(').append(a.peso()).append(')');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
