# Rappresentazioni dei grafi — slide Bonomo

Package `Graph`, coerente con la cartella già presente nel progetto.

| Caso | Liste di adiacenza | Matrice di adiacenza |
| --- | --- | --- |
| Non orientato, non pesato | `Graph` | `AdjMatrixGraph` |
| Orientato, non pesato | `Digraph` | `AdjMatrixDigraph` |
| Non orientato, pesato | `EdgeWeightedGraph`, `Edge` | `AdjMatrixEdgeWeightedGraph` |
| Orientato, pesato | `EdgeWeightedDigraph`, `DirectedEdge` | `AdjMatrixEdgeWeightedDigraph` |

## Corrispondenza con le slide

Riferimenti alle slide del corso di Laboratorio di algoritmi del prof. Bonomo.

- `slide16 - Grafi.pdf`, pp. 17–18: matrice 0/1 e array delle liste di adiacenza. Non contiene codice Java per la matrice: le classi `AdjMatrix*` sono implementazioni della rappresentazione descritta, non trascrizioni di codice presente nelle slide.
- `slide17 Esplorazione di Grafi.pdf`, pp. 30–34: API e implementazione di `Digraph` con `Bag<Integer>[]`, `addEdge`, `adj`, `reverse`. `Graph` è il corrispondente non orientato: inserisce ciascun arco nelle liste di entrambi gli estremi.
- `slide20 - minimum spanning tree.pdf`, pp. 17–23: `Edge`, API e implementazione di `EdgeWeightedGraph`, `edges()` e pesi nella matrice.
- `slide23 Shortest Paths .pdf`, pp. 5–9: `DirectedEdge`, API e implementazione di `EdgeWeightedDigraph`, incluso `indegree`.

Le liste mantengono nomi, firme, `Bag`, struttura dei campi e inserimenti delle slide. Usano `Bag` e `In` dalla [libreria `algs4` di Robert Sedgewick e Kevin Wayne](https://algs4.cs.princeton.edu/code/).

Completamenti e differenze dichiarate rispetto agli estratti delle slide:

- Aggiunti import/package, controlli sugli argomenti e i metodi dell'API non riportati negli estratti (`V`, `E`, costruttore da `In`, `toString`, `reverse`, `edges` del digrafo pesato). Aggiunto `weight()` a `Edge`, previsto dall'API ma assente nell'estratto dell'implementazione. Il cast dell'array usa `new Bag<?>[V]` per evitare tipi raw.
- L'`edges()` di slide20 p. 21, con il solo controllo `e.other(v) > v`, omette i self-loop, dichiarati ammessi a p. 20. Qui ogni self-loop è restituito una volta, mentre compare due volte nella lista di adiacenza. Gli archi paralleli restano distinti.
- Le matrici non rappresentano archi paralleli: inserire di nuovo la stessa coppia non aumenta `E`; nel caso pesato aggiorna il peso. Per i non orientati la matrice è simmetrica. Un self-loop occupa una sola cella.
- Nelle matrici pesate `Double.POSITIVE_INFINITY` indica l'assenza di un arco; i pesi ammessi sono finiti, anche zero e negativi. La diagonale indica un arco solo dopo un inserimento esplicito. `weight(v,w)` restituisce infinito se l'arco è assente.

## Uso

Liste: `addEdge(v,w)` o `addEdge(new Edge(v,w,peso))` / `addEdge(new DirectedEdge(v,w,peso))`; `adj(v)` restituisce vertici o archi, come nelle slide.

Matrici: `addEdge(v,w)` oppure `addEdge(v,w,peso)`, `adjacent(v,w)`, `adj(v)` per i vertici vicini e `weight(v,w)` per i pesi. Spazio `O(V²)`, inserimento e adiacenza `O(1)`, enumerazione dei vicini `O(V)`.

Input delle liste: numero di vertici, numero di archi, poi una coppia di vertici per arco (con peso finale per i grafi pesati), separati da spazi o righe.

Dalla radice della repo, scarica `algs4.jar` nella cartella di compilazione, poi esegui i test dei grafi e l’esempio di Kruskal.

```sh
mkdir -p build
curl -fsSL https://algs4.cs.princeton.edu/code/algs4.jar -o build/algs4.jar
javac -cp build/algs4.jar -d build Main.java Graph/*.java
java -ea -cp build:build/algs4.jar Graph.GraphTest
java -cp build:build/algs4.jar Main
```
