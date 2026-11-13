package grafos;

import java.util.List;

/**
 * Resultado de un algoritmo de árbol de expansión mínima.
 *
 * @param aristas    aristas elegidas, en el orden en que se aceptaron
 * @param costoTotal suma de los pesos de las aristas elegidas
 * @param abarcaTodo true si el resultado conecta los V vértices
 *                   (V-1 aristas); false si el grafo no es conexo
 */
public record ResultadoMST(List<Arista> aristas, long costoTotal,
                           boolean abarcaTodo) {
}
