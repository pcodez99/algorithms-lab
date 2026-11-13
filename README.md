# Algorithms Lab

Raccolta di esercitazioni e implementazioni in Java per il corso di **Laboratorio di algoritmi** dell’Università di Palermo, a partire dalle slide del prof. Bonomo.

## Esercitazioni

| Argomento | Contenuti |
| --- | --- |
| [Strutture dati](dataStructure/) | Alberi binari di ricerca, AVL e rosso-neri, coda di priorità indicizzata e Union-Find |
| [Grafi](Graph/) | Liste e matrici di adiacenza, grafi orientati e pesati, albero ricoprente minimo con Kruskal |
| [Progetto di ordinamento topologico](Graph/TopologicalOrder/) | Pianificazione delle attività con l’algoritmo di Kahn e rilevamento dei cicli |
| [Stringhe](patternMatching/) e [hashing](hashing/) | Ricerca ingenua, KMP e tabella hash con concatenamento separato |
| [Laboratorio AVL](bst-lab/index.html) | Demo interattiva di inserimenti, cancellazioni e rotazioni da aprire nel browser |

## Esegui un esempio

Con il JDK, dalla radice della repo puoi eseguire l’esempio di pattern matching.

```sh
javac -d build patternMatching/*.java
java -cp build patternMatching.Main
```

Grafi e hashing usano [algs4](algs4/), inclusa nella repo. I comandi per i grafi sono nella [guida del package](Graph/README.md).
