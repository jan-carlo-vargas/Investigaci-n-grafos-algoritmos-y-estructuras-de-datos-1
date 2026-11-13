package grafos;

/**
 * Arista ponderada de un grafo. En un grafo no dirigido cada arista se
 * almacena en ambos sentidos; el campo {@code origen} indica desde qué
 * vértice se está mirando la arista.
 *
 * @param origen  vértice de partida
 * @param destino vértice de llegada
 * @param peso    costo asociado a la arista
 */
public record Arista(int origen, int destino, int peso)
        implements Comparable<Arista> {

    /** Orden por peso; los empates se rompen por origen y destino. */
    @Override
    public int compareTo(Arista otra) {
        int c = Integer.compare(peso, otra.peso);
        if (c != 0) {
            return c;
        }
        c = Integer.compare(origen, otra.origen);
        return c != 0 ? c : Integer.compare(destino, otra.destino);
    }
}
