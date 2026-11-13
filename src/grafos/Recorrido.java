package grafos;

import java.util.List;

/** Estrategia de recorrido de un grafo a partir de un vértice. */
public interface Recorrido {

    /**
     * Visita los vértices alcanzables desde {@code inicio}.
     *
     * @return vértices en el orden en que fueron visitados
     */
    List<Integer> recorrer(Grafo g, int inicio);
}
