package grafos;

import java.util.List;

/**
 * Demostración: construye el grafo base con ambas representaciones y
 * ejecuta BFS, DFS, Prim y Kruskal sobre cada una.
 */
public class Main {

    public static void main(String[] args) {
        GrafoMatriz matriz = new GrafoMatriz(GrafoBase.NOMBRES.length);
        GrafoLista lista = new GrafoLista(GrafoBase.NOMBRES.length);
        GrafoBase.cargar(matriz);
        GrafoBase.cargar(lista);

        System.out.println("=== Matriz de adyacencia ('-' = sin arista) ===");
        System.out.print(matriz.mostrar(GrafoBase.NOMBRES));
        System.out.println("\n=== Listas de adyacencia ===");
        System.out.print(lista.mostrar(GrafoBase.NOMBRES));

        System.out.println("\n=== Recorridos desde A ===");
        mostrarRecorrido("BFS (lista) ", new BFS().recorrer(lista, 0));
        mostrarRecorrido("BFS (matriz)", new BFS().recorrer(matriz, 0));
        DFS dfs = new DFS();
        mostrarRecorrido("DFS iterativo (lista) ", dfs.recorrer(lista, 0));
        mostrarRecorrido("DFS iterativo (matriz)", dfs.recorrer(matriz, 0));
        mostrarRecorrido("DFS recursivo (lista)",
                dfs.recorrerRecursivo(lista, 0));

        System.out.println("\n=== Arbol de expansion minima (MST) ===");
        ResultadoMST primLista = new Prim().calcular(lista);
        ResultadoMST primMatriz = new Prim().calcular(matriz);
        ResultadoMST kruskalLista = new Kruskal().calcular(lista);
        ResultadoMST kruskalMatriz = new Kruskal().calcular(matriz);
        mostrarMST("Prim (lista, inicio A)", primLista);
        mostrarMST("Prim (matriz, inicio A)", primMatriz);
        mostrarMST("Kruskal (lista)", kruskalLista);
        mostrarMST("Kruskal (matriz)", kruskalMatriz);

        boolean coinciden = primLista.costoTotal() == kruskalLista.costoTotal()
                && primMatriz.costoTotal() == kruskalMatriz.costoTotal()
                && primLista.costoTotal() == primMatriz.costoTotal();
        System.out.println("\nLos costos de Prim y Kruskal coinciden: "
                + coinciden);
    }

    private static void mostrarRecorrido(String titulo,
                                         List<Integer> orden) {
        System.out.println(titulo + ": " + GrafoBase.formatoVertices(orden));
    }

    private static void mostrarMST(String titulo, ResultadoMST r) {
        System.out.println(titulo + ":");
        System.out.println("  aristas: "
                + GrafoBase.formatoAristas(r.aristas()));
        System.out.println("  costo total: " + r.costoTotal());
    }
}
