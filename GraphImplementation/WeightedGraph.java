package systemImp;

import java.util.LinkedHashSet; //Used as return type for BFS & DFS

/*
 * The following interface represents a contract that
 * all Graph implementations must follow.
 * 
 * Both the AdjacencyMap and the AdjacencyMatrix must follow this contract
 * 
 * Note that this graph specifically specifies that the graph
 * must be weighted
 */
public interface WeightedGraph<E> 
{
	//Checks if a specific element is in the graph
	public boolean elementExists(E element);
	
	//Checks if an edge exists between two vertices
	public boolean edgeExists(E vertex1, E vertex2);
	
	//Inserts new element to the graph if it doesn't exist
	public void insertElement(E element);
	
	//Inserts new edge into the graph with an associated cost
	public default void insertEdge(E vertex1, E vertex2, int cost)
	{
		if(!elementExists(vertex1) || !elementExists(vertex2))
		{
			throw new IllegalArgumentException("One or both of "
		    + "the elements you passed in doesn't exist in the graph.");
		}
		if(vertex1.equals(vertex2))
		{
			throw new IllegalArgumentException("No self-edges allowed.");
		}
		if(cost < 1)
		{
			throw new IllegalArgumentException
			("The cost you passed in was invalid.");
		}
	}
	
	//Removes edge between two vertices
	public void removeEdge(E vertex1, E vertex2);
	
	//Gets the edge cost between two vertices
	public int getCost(E vertex1, E vertex2);
	
	//Prints all the edges associated with an element
	public String getEdges(E element);
	
	//Determines if the undirected graph is connected (all nodes can be reached)
	public boolean isConnected();
	
	//Determines if the graph has no elements, given default implementation
	public default boolean isEmpty()
	{
		return size() == 0;
	}
	
	//# of elements in the graph
	public int size();
	
	/*
	 * Returns MST with either Kruskal's (true) or Prim's (false) algorithm
	 */
	public WeightedGraph<E> getMinimumSpanningTree(boolean kruskalOrPrim);
	
	//Performs breadth-first search on the graph from a source element
	public LinkedHashSet<E> breadthFirstSearch(E source);
	
	//Performs depth-first search on the graph from a source element
	public LinkedHashSet<E> depthFirstSearch(E source);
	
	/*
	 * Finds the cheapest path from a source element to any other element
	 * 
	 * This is done using Dijkstra's Algorithm
	 */
	public WeightedGraph<E> getShortestPathGraphFrom(E source);
}
