This project is a Java Project that implements the Graph abstract data type via two implementations: AdjacencyMap and AdjacancyMatrix.

The project starts off with a backbone of an undirected, weighted graph, that is shown as an interface. 
Both the AdjacencyMap and AdjacencyMatrix classes follow this backbone to a tee. 

This interface requires that both classes not only implement basic graph operations (existence check, insert, delete), 
but that both graphs implement various graph algorithms, such as BFS, DFS, Minimum-Spanning Tree (Kruskal & Prim), and
shortest path from a single source (Dijkstra's).

This project demonstrates the following knowledge: 
* Knowledge of Class Design
* Knowledge of Java Concepts --> Interfaces, Nested Types (Inner Class, Local Class), Generics
* Knowledge of Graph Algorithms & Features
* Knowledge of various data structures, their implementation in Java, and usage in algorithms
(Stack, Queue, HashSet, HashMap, TreeSet, PriorityQueue, LinkedHashSet, DisjointSet)
* Knowledge of Unit Testing (I tested the DisjointSet, AdjacencyMap, and AdjancencyMatrix in 3 seperate files)
