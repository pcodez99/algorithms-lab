# Ordinamento topologico

Progetto di Laboratorio di algoritmi sulla pianificazione di attività con vincoli di precedenza. L’algoritmo di Kahn costruisce un ordine valido in (O(V + E)). Se l’ordinamento è incompleto, una DFS ricostruisce e stampa un ciclo.

## Compilazione ed esecuzione

Serve solo il JDK. Dalla radice della repo:

```sh
javac -d build Graph/TopologicalOrder/*.java
java -cp build Graph.TopologicalOrder.Main < Graph/TopologicalOrder/test/esempioInputProfessoressa.txt
```

L’input contiene il numero di attività e dipendenze, gli identificativi delle attività e una coppia `A B` per ogni vincolo in cui A precede B. La cartella [test](test/) include grafi aciclici, un ciclo, attività isolate, un grafo vuoto e una dipendenza verso un’attività sconosciuta.
