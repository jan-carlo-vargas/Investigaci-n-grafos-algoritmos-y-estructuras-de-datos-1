package grafos;

/**
 * Estructura de conjuntos disjuntos (Union-Find) con compresión de
 * caminos y unión por rango. El costo amortizado de cada operación es
 * O(alfa(V)), prácticamente constante.
 */
public class UnionFind {

    private final int[] padre;
    private final int[] rango;
    private int componentes;

    /** Crea n conjuntos unitarios {0}, {1}, ..., {n-1}. */
    public UnionFind(int n) {
        padre = new int[n];
        rango = new int[n];
        componentes = n;
        for (int i = 0; i < n; i++) {
            padre[i] = i;
        }
    }

    /** Devuelve el representante de x aplicando compresión de caminos. */
    public int encontrar(int x) {
        if (padre[x] != x) {
            padre[x] = encontrar(padre[x]);
        }
        return padre[x];
    }

    /**
     * Une los conjuntos de a y b.
     *
     * @return true si estaban en conjuntos distintos (se unieron);
     *         false si ya pertenecían al mismo conjunto
     */
    public boolean unir(int a, int b) {
        int ra = encontrar(a);
        int rb = encontrar(b);
        if (ra == rb) {
            return false;
        }
        if (rango[ra] < rango[rb]) {
            padre[ra] = rb;
        } else if (rango[ra] > rango[rb]) {
            padre[rb] = ra;
        } else {
            padre[rb] = ra;
            rango[ra]++;
        }
        componentes--;
        return true;
    }

    /** @return cantidad de conjuntos disjuntos actuales */
    public int componentes() {
        return componentes;
    }
}
