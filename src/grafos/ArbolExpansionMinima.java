package grafos;

/** Estrategia para calcular un árbol de expansión mínima (MST). */
public interface ArbolExpansionMinima {

    /**
     * Calcula el MST de {@code g}. Si el grafo no es conexo, cada
     * algoritmo documenta qué devuelve (ver implementaciones).
     */
    ResultadoMST calcular(Grafo g);
}
