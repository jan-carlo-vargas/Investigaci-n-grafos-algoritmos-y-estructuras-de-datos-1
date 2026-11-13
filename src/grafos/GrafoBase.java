package grafos;

import java.util.List;

/**
 * Grafo base de la investigación: 7 vértices (A..G) y 11 aristas
 * ponderadas con pesos distintos entre sí (2..12), por lo que su árbol
 * de expansión mínima es único. Se reutiliza en la demostración y en
 * las pruebas para que coincidan con el documento.
 */
public final class GrafoBase {

    public static final String[] NOMBRES =
            {"A", "B", "C", "D", "E", "F", "G"};

    /** Cada fila es {origen, destino, peso}. */
    private static final int[][] ARISTAS = {
        {0, 1, 4},  {0, 2, 3},  {0, 3, 7},  {1, 2, 5},
        {1, 3, 9},  {2, 3, 10}, {2, 4, 2},  {3, 5, 6},
        {3, 6, 8},  {4, 5, 11}, {5, 6, 12}
    };

    private GrafoBase() {
    }

    /** Agrega las 11 aristas del grafo base a un grafo de 7 vértices. */
    public static void cargar(Grafo g) {
        for (int[] e : ARISTAS) {
            g.agregarArista(e[0], e[1], e[2]);
        }
    }

    /** Formatea una secuencia de vértices, por ejemplo "A -> B -> C". */
    public static String formatoVertices(List<Integer> orden) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < orden.size(); i++) {
            if (i > 0) {
                sb.append(" -> ");
            }
            sb.append(NOMBRES[orden.get(i)]);
        }
        return sb.toString();
    }

    /** Formatea aristas, por ejemplo "A-C(3), C-E(2)". */
    public static String formatoAristas(List<Arista> aristas) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aristas.size(); i++) {
            Arista a = aristas.get(i);
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(NOMBRES[a.origen()]).append('-')
              .append(NOMBRES[a.destino()])
              .append('(').append(a.peso()).append(')');
        }
        return sb.toString();
    }
}
