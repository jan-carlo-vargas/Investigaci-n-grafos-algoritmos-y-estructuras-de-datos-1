package grafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Pruebas simples con aserciones propias (sin JUnit). Termina con
 * código de salida 1 si alguna verificación falla.
 */
public class Pruebas {

    private static int fallos = 0;
    private static int total = 0;

    public static void main(String[] args) {
        pruebaRecorridosGrafoBase();
        pruebaMSTGrafoBase();
        pruebaGrafoDesconectado();
        pruebaUnSoloVertice();
        pruebaVerticeInvalido();
        pruebaAleatoria();

        System.out.println("\nPruebas ejecutadas: " + total
                + ", fallidas: " + fallos);
        if (fallos > 0) {
            System.exit(1);
        }
    }

    private static void verificar(String descripcion, boolean condicion) {
        total++;
        if (!condicion) {
            fallos++;
        }
        System.out.println((condicion ? "[OK]    " : "[FALLA] ")
                + descripcion);
    }

    private static Grafo[] grafosBase() {
        int n = GrafoBase.NOMBRES.length;
        Grafo[] gs = {new GrafoLista(n), new GrafoMatriz(n)};
        for (Grafo g : gs) {
            GrafoBase.cargar(g);
        }
        return gs;
    }

    private static void pruebaRecorridosGrafoBase() {
        List<Integer> bfsEsperado = List.of(0, 1, 2, 3, 4, 5, 6);
        List<Integer> dfsEsperado = List.of(0, 1, 2, 3, 5, 4, 6);
        DFS dfs = new DFS();
        for (Grafo g : grafosBase()) {
            String tipo = g.getClass().getSimpleName();
            verificar("BFS grafo base (" + tipo + ") = A B C D E F G",
                    new BFS().recorrer(g, 0).equals(bfsEsperado));
            verificar("DFS iterativo (" + tipo + ") = A B C D F E G",
                    dfs.recorrer(g, 0).equals(dfsEsperado));
            verificar("DFS recursivo (" + tipo + ") = A B C D F E G",
                    dfs.recorrerRecursivo(g, 0).equals(dfsEsperado));
        }
    }

    private static Set<String> conjunto(List<Arista> aristas) {
        Set<String> s = new HashSet<>();
        for (Arista a : aristas) {
            s.add(Math.min(a.origen(), a.destino()) + "-"
                    + Math.max(a.origen(), a.destino()) + ":" + a.peso());
        }
        return s;
    }

    private static void pruebaMSTGrafoBase() {
        for (Grafo g : grafosBase()) {
            String tipo = g.getClass().getSimpleName();
            ResultadoMST p = new Prim().calcular(g);
            ResultadoMST k = new Kruskal().calcular(g);
            verificar("Prim costo 30 (" + tipo + ")", p.costoTotal() == 30);
            verificar("Kruskal costo 30 (" + tipo + ")",
                    k.costoTotal() == 30);
            verificar("Prim y Kruskal eligen las mismas aristas ("
                    + tipo + ")",
                    conjunto(p.aristas()).equals(conjunto(k.aristas())));
            verificar("El MST tiene V-1 = 6 aristas (" + tipo + ")",
                    p.aristas().size() == 6 && p.abarcaTodo()
                    && k.aristas().size() == 6 && k.abarcaTodo());
        }
    }

    private static void pruebaGrafoDesconectado() {
        Grafo[] gs = {new GrafoLista(5), new GrafoMatriz(5)};
        for (Grafo g : gs) {
            g.agregarArista(0, 1, 1);
            g.agregarArista(1, 2, 2);
            g.agregarArista(3, 4, 3);
            String tipo = g.getClass().getSimpleName();
            verificar("BFS desde 0 en grafo desconectado = [0,1,2] ("
                    + tipo + ")",
                    new BFS().recorrer(g, 0).equals(List.of(0, 1, 2)));
            verificar("DFS desde 3 en grafo desconectado = [3,4] ("
                    + tipo + ")",
                    new DFS().recorrer(g, 3).equals(List.of(3, 4)));
            ResultadoMST p = new Prim().calcular(g);
            verificar("Prim en desconectado: abarcaTodo=false, costo 3 ("
                    + tipo + ")",
                    !p.abarcaTodo() && p.costoTotal() == 3
                    && p.aristas().size() == 2);
            ResultadoMST k = new Kruskal().calcular(g);
            verificar("Kruskal en desconectado: bosque, costo 6 ("
                    + tipo + ")",
                    !k.abarcaTodo() && k.costoTotal() == 6
                    && k.aristas().size() == 3);
        }
    }

    private static void pruebaUnSoloVertice() {
        Grafo[] gs = {new GrafoLista(1), new GrafoMatriz(1)};
        for (Grafo g : gs) {
            String tipo = g.getClass().getSimpleName();
            verificar("Un vertice: BFS y DFS = [0] (" + tipo + ")",
                    new BFS().recorrer(g, 0).equals(List.of(0))
                    && new DFS().recorrer(g, 0).equals(List.of(0)));
            ResultadoMST p = new Prim().calcular(g);
            ResultadoMST k = new Kruskal().calcular(g);
            verificar("Un vertice: MST vacio, costo 0, abarcaTodo ("
                    + tipo + ")",
                    p.aristas().isEmpty() && p.costoTotal() == 0
                    && p.abarcaTodo() && k.aristas().isEmpty()
                    && k.costoTotal() == 0 && k.abarcaTodo());
        }
    }

    private static void pruebaVerticeInvalido() {
        boolean lanzo = false;
        try {
            new BFS().recorrer(new GrafoLista(3), 7);
        } catch (IllegalArgumentException e) {
            lanzo = true;
        }
        verificar("Vertice inicial invalido lanza excepcion", lanzo);
    }

    /** Compara Prim, Kruskal y ambas representaciones en grafos al azar. */
    private static void pruebaAleatoria() {
        Random azar = new Random(2026);
        boolean todoBien = true;
        for (int caso = 0; caso < 300; caso++) {
            int n = 2 + azar.nextInt(14);
            Grafo lista = new GrafoLista(n);
            Grafo matriz = new GrafoMatriz(n);
            List<int[]> pares = new ArrayList<>();
            for (int v = 1; v < n; v++) { // garantiza conexidad
                pares.add(new int[]{azar.nextInt(v), v, 1 + azar.nextInt(30)});
            }
            int extras = azar.nextInt(n * 2);
            for (int i = 0; i < extras; i++) {
                int u = azar.nextInt(n);
                int v = azar.nextInt(n);
                if (u != v) {
                    pares.add(new int[]{u, v, 1 + azar.nextInt(30)});
                }
            }
            for (int[] e : pares) {
                lista.agregarArista(e[0], e[1], e[2]);
                matriz.agregarArista(e[0], e[1], e[2]);
            }
            long pl = new Prim().calcular(lista).costoTotal();
            long pm = new Prim().calcular(matriz).costoTotal();
            long kl = new Kruskal().calcular(lista).costoTotal();
            long km = new Kruskal().calcular(matriz).costoTotal();
            boolean mismoBfs = new BFS().recorrer(lista, 0)
                    .equals(new BFS().recorrer(matriz, 0));
            boolean mismoDfs = new DFS().recorrer(lista, 0)
                    .equals(new DFS().recorrerRecursivo(matriz, 0));
            boolean todosVisitados =
                    new BFS().recorrer(lista, 0).size() == n;
            if (!(pl == pm && pm == kl && kl == km) || !mismoBfs
                    || !mismoDfs || !todosVisitados) {
                todoBien = false;
            }
        }
        verificar("300 grafos aleatorios: Prim = Kruskal, lista = matriz",
                todoBien);
    }
}
