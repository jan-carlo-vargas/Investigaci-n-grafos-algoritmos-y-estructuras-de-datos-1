package grafos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 * Recorrido en profundidad (Depth-First Search). Se ofrecen dos
 * versiones que producen exactamente el mismo orden:
 * una ITERATIVA con pila explícita ({@link #recorrer}) y una RECURSIVA
 * ({@link #recorrerRecursivo}).
 *
 * Complejidad temporal: O(V + E) con listas de adyacencia y O(V^2) con
 * matriz de adyacencia. Espacio adicional: O(V).
 */
public class DFS implements Recorrido {

    /** Versión iterativa: la pila guarda un iterador por vértice activo. */
    @Override
    public List<Integer> recorrer(Grafo g, int inicio) {
        g.validarVertice(inicio);
        boolean[] visitado = new boolean[g.cantidadVertices()];
        List<Integer> orden = new ArrayList<>();
        Deque<Iterator<Arista>> pila = new ArrayDeque<>();

        visitado[inicio] = true;
        orden.add(inicio);
        pila.push(g.vecinos(inicio).iterator());
        while (!pila.isEmpty()) {
            Iterator<Arista> it = pila.peek();
            if (it.hasNext()) {
                int v = it.next().destino();
                if (!visitado[v]) {
                    visitado[v] = true;
                    orden.add(v);
                    pila.push(g.vecinos(v).iterator());
                }
            } else {
                pila.pop();
            }
        }
        return orden;
    }

    /** Versión recursiva (la profundidad de recursión puede llegar a V). */
    public List<Integer> recorrerRecursivo(Grafo g, int inicio) {
        g.validarVertice(inicio);
        boolean[] visitado = new boolean[g.cantidadVertices()];
        List<Integer> orden = new ArrayList<>();
        visitar(g, inicio, visitado, orden);
        return orden;
    }

    private void visitar(Grafo g, int u, boolean[] visitado,
                         List<Integer> orden) {
        visitado[u] = true;
        orden.add(u);
        for (Arista a : g.vecinos(u)) {
            if (!visitado[a.destino()]) {
                visitar(g, a.destino(), visitado, orden);
            }
        }
    }
}
