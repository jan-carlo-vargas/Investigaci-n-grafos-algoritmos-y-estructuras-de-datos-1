package grafos;

import java.util.List;

/**
 * Contrato de un grafo no dirigido y ponderado cuyos vértices se numeran
 * de 0 a n-1. Las implementaciones devuelven los vecinos ordenados por
 * vértice destino ascendente, de modo que los recorridos son
 * deterministas y reproducibles.
 */
public interface Grafo {

    /** @return cantidad de vértices V del grafo */
    int cantidadVertices();

    /**
     * Agrega (o reemplaza) la arista no dirigida {u, v}.
     *
     * @throws IllegalArgumentException si un vértice no existe o u == v
     */
    void agregarArista(int u, int v, int peso);

    /** @return true si existe la arista {u, v} */
    boolean existeArista(int u, int v);

    /** @return aristas que salen de u, ordenadas por destino */
    List<Arista> vecinos(int u);

    /** @return cada arista una sola vez, con origen menor que destino */
    List<Arista> aristas();

    /** Verifica que v sea un vértice válido. */
    default void validarVertice(int v) {
        if (v < 0 || v >= cantidadVertices()) {
            throw new IllegalArgumentException(
                    "Vertice fuera de rango: " + v);
        }
    }
}
