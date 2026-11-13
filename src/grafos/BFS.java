package grafos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Recorrido en anchura (Breadth-First Search) con cola.
 *
 * Complejidad temporal: O(V + E) con listas de adyacencia y O(V^2) con
 * matriz de adyacencia. Espacio adicional: O(V).
 */
public class BFS implements Recorrido {

    @Override
    public List<Integer> recorrer(Grafo g, int inicio) {
        g.validarVertice(inicio);
        boolean[] visitado = new boolean[g.cantidadVertices()];
        List<Integer> orden = new ArrayList<>();
        Queue<Integer> cola = new ArrayDeque<>();

        visitado[inicio] = true;
        cola.add(inicio);
        while (!cola.isEmpty()) {
            int u = cola.remove();
            orden.add(u);
            for (Arista a : g.vecinos(u)) {
                if (!visitado[a.destino()]) {
                    visitado[a.destino()] = true;
                    cola.add(a.destino());
                }
            }
        }
        return orden;
    }
}
