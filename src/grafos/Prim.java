package grafos;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Algoritmo de Prim con cola de prioridad (montículo binario).
 *
 * Complejidad temporal: O(E log V) con listas de adyacencia. Cada arista
 * entra a la cola a lo sumo dos veces, por lo que la cola tiene O(E)
 * elementos y cada operación cuesta O(log E) = O(log V). Con la
 * representación de matriz de este proyecto, la enumeración de vecinos
 * suma O(V^2) adicional.
 *
 * Si el grafo no es conexo, solo se cubre la componente del vértice
 * inicial y {@code abarcaTodo} es false.
 */
public class Prim implements ArbolExpansionMinima {

    /** Calcula el MST comenzando en el vértice 0. */
    @Override
    public ResultadoMST calcular(Grafo g) {
        return calcular(g, 0);
    }

    /** Calcula el MST comenzando en el vértice {@code inicio}. */
    public ResultadoMST calcular(Grafo g, int inicio) {
        g.validarVertice(inicio);
        int n = g.cantidadVertices();
        boolean[] enArbol = new boolean[n];
        List<Arista> elegidas = new ArrayList<>();
        PriorityQueue<Arista> cola = new PriorityQueue<>();
        long costo = 0;

        enArbol[inicio] = true;
        cola.addAll(g.vecinos(inicio));
        while (!cola.isEmpty() && elegidas.size() < n - 1) {
            Arista a = cola.poll();
            if (enArbol[a.destino()]) {
                continue; // arista obsoleta: cerraría un ciclo
            }
            enArbol[a.destino()] = true;
            elegidas.add(a);
            costo += a.peso();
            for (Arista b : g.vecinos(a.destino())) {
                if (!enArbol[b.destino()]) {
                    cola.add(b);
                }
            }
        }
        return new ResultadoMST(elegidas, costo, elegidas.size() == n - 1);
    }
}
