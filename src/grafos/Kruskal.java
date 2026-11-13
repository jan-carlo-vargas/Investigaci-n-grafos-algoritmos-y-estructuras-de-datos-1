package grafos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Algoritmo de Kruskal con Union-Find.
 *
 * Complejidad temporal: O(E log E) = O(E log V). El ordenamiento domina
 * el costo; las E operaciones de Union-Find suman O(E alfa(V)). Como
 * E <= V^2, se cumple log E <= 2 log V.
 *
 * Si el grafo no es conexo, el resultado es un bosque de expansión
 * mínima (uno por componente) y {@code abarcaTodo} es false.
 */
public class Kruskal implements ArbolExpansionMinima {

    @Override
    public ResultadoMST calcular(Grafo g) {
        int n = g.cantidadVertices();
        List<Arista> todas = new ArrayList<>(g.aristas());
        Collections.sort(todas);
        UnionFind uf = new UnionFind(n);
        List<Arista> elegidas = new ArrayList<>();
        long costo = 0;

        for (Arista a : todas) {
            if (uf.unir(a.origen(), a.destino())) {
                elegidas.add(a);
                costo += a.peso();
                if (elegidas.size() == n - 1) {
                    break; // el árbol ya está completo
                }
            }
        }
        return new ResultadoMST(elegidas, costo, elegidas.size() == n - 1);
    }
}
