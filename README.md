# Grafos: representación, recorridos y árboles de expansión mínima

Implementación en **Java 17+** (solo librería estándar, sin dependencias) de los algoritmos
de la investigación *Grafos y Algoritmos Fundamentales de Recorrido y Optimización*
(Algoritmos y Estructuras de Datos I, TEC, II Semestre 2026).

## Contenido

| Archivo (`src/grafos/`) | Descripción |
|---|---|
| `Grafo`, `Arista` | Contrato del grafo no dirigido y ponderado, y arista (record) |
| `GrafoMatriz` | Representación con matriz de adyacencia (espacio O(V²)) |
| `GrafoLista` | Representación con listas de adyacencia (espacio O(V+E)) |
| `Recorrido`, `BFS`, `DFS` | Recorrido en anchura (cola) y en profundidad (iterativo y recursivo) |
| `ArbolExpansionMinima`, `ResultadoMST` | Contrato y resultado de los algoritmos de MST |
| `Prim` | Prim con `PriorityQueue`: O(E log V) |
| `Kruskal`, `UnionFind` | Kruskal con Union-Find (compresión de caminos y unión por rango): O(E log E) = O(E log V) |
| `GrafoBase` | Grafo base de 7 vértices (A–G) usado en la demo, las pruebas y el documento |
| `Main` | Demostración por consola |
| `Pruebas` | Pruebas con aserciones propias (sin JUnit) |

Complejidades (V = vértices, E = aristas):

| Algoritmo | Lista de adyacencia | Matriz de adyacencia |
|---|---|---|
| BFS / DFS | O(V + E) | O(V²) |
| Prim | O(E log V) | O(V² + E log V) en esta implementación |
| Kruskal | O(E log E) = O(E log V) | O(V² + E log V) |

## Requisitos

- JDK 17 o superior (`javac` y `java` disponibles en el `PATH`).

## Compilar y ejecutar

Linux / macOS:

```bash
./compilar_y_ejecutar.sh
```

Windows (cmd): `compilar_y_ejecutar.bat`

Manualmente (Linux / macOS):

```bash
mkdir -p out
javac -encoding UTF-8 -d out src/grafos/*.java
java -cp out grafos.Main        # demostración
java -cp out grafos.Pruebas     # pruebas (código de salida 1 si alguna falla)
```

Manualmente (PowerShell):

```powershell
mkdir out -Force
javac -encoding UTF-8 -d out (Get-ChildItem src -Recurse -Filter *.java).FullName
java -cp out grafos.Main
java -cp out grafos.Pruebas
```

## Grafo base

Vértices A–G y aristas (peso): A-B(4), A-C(3), A-D(7), B-C(5), B-D(9), C-D(10), C-E(2),
D-F(6), D-G(8), E-F(11), F-G(12). Los pesos son distintos entre sí, por lo que el MST es único (costo 30).

## Salida de ejemplo (`grafos.Main`)

```text
=== Matriz de adyacencia ('-' = sin arista) ===
       A   B   C   D   E   F   G
A      -   4   3   7   -   -   -
B      4   -   5   9   -   -   -
C      3   5   -  10   2   -   -
D      7   9  10   -   -   6   8
E      -   -   2   -   -  11   -
F      -   -   -   6  11   -  12
G      -   -   -   8   -  12   -

=== Listas de adyacencia ===
A -> B(4) C(3) D(7)
B -> A(4) C(5) D(9)
C -> A(3) B(5) D(10) E(2)
D -> A(7) B(9) C(10) F(6) G(8)
E -> C(2) F(11)
F -> D(6) E(11) G(12)
G -> D(8) F(12)

=== Recorridos desde A ===
BFS (lista) : A -> B -> C -> D -> E -> F -> G
BFS (matriz): A -> B -> C -> D -> E -> F -> G
DFS iterativo (lista) : A -> B -> C -> D -> F -> E -> G
DFS iterativo (matriz): A -> B -> C -> D -> F -> E -> G
DFS recursivo (lista): A -> B -> C -> D -> F -> E -> G

=== Arbol de expansion minima (MST) ===
Prim (lista, inicio A):
  aristas: A-C(3), C-E(2), A-B(4), A-D(7), D-F(6), D-G(8)
  costo total: 30
Prim (matriz, inicio A):
  aristas: A-C(3), C-E(2), A-B(4), A-D(7), D-F(6), D-G(8)
  costo total: 30
Kruskal (lista):
  aristas: C-E(2), A-C(3), A-B(4), D-F(6), A-D(7), D-G(8)
  costo total: 30
Kruskal (matriz):
  aristas: C-E(2), A-C(3), A-B(4), D-F(6), A-D(7), D-G(8)
  costo total: 30

Los costos de Prim y Kruskal coinciden: true
```

## Pruebas

`grafos.Pruebas` verifica: orden de BFS/DFS (iterativo y recursivo) en el grafo base, costo mínimo
igual en Prim y Kruskal, grafo desconectado (BFS/DFS parciales, bosque de expansión), grafo de un
solo vértice, vértice inicial inválido y 300 grafos aleatorios (Prim = Kruskal, lista = matriz).
Resultado esperado: `Pruebas ejecutadas: 28, fallidas: 0`.

## Notas de diseño

- Los vecinos se devuelven ordenados por vértice destino, lo que hace los recorridos deterministas.
- Si el grafo no es conexo, Prim cubre solo la componente del vértice inicial y Kruskal devuelve
  un bosque de expansión mínima; en ambos casos `ResultadoMST.abarcaTodo()` es `false`.
- Los pesos pueden ser cero o negativos (la matriz usa `Integer.MAX_VALUE` como "sin arista").
