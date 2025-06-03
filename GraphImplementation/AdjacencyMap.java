package systemImp;

import java.util.*; //In order to use JCF implementations of data structures

/*
 * The following class represents an Adjacency Map implementation
 * of a Graph, and has implementations of basic graph operations
 * (add, remove, find, enumerate) as well as various graph algorithms
 * such as BFS, DFS, Kruskal's Algorithm, Prim's Algorithm, 
 * and Dijkstra's Algorithm
 * 
 * The graph represents a weighted, undirected graph with only positive weights.
 */
//Any type of accepted, must following WeightedGraph contract
public class AdjacencyMap<E> implements WeightedGraph<E>
{
	/*
	 * Following class represents an edge and its weight, 
	 * this class is mainly used for use in edge-based algorithms
	 */
	private class Edge implements Comparable<Edge>
	{
		E vertex1, vertex2; //The endpoints of the edge
		int cost; //Represents the cost to traverse the edge
		
		//Construct the edge
		Edge(E vertex1, E vertex2, int cost)
		{
			this.cost = cost;
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
		}
		
		/*
		 * Equals method, two edges are the same if they 
		 * have the same vertices in either direction
		 */
		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object obj)
		{
			//If they are the same reference they are equal
			if(this == obj)
			{
				return true;
			}
			//If the types are different they are NOT equal
			if(this.getClass() != obj.getClass())
			{
				return false;
			}
			//Cast obj to an Edge type
			Edge edge = (Edge) obj;
			//Determine equivalency
			return 
			(vertex1.equals(edge.vertex1) && vertex2.equals(edge.vertex2)) || 
			(vertex1.equals(edge.vertex2) && vertex2.equals(edge.vertex1));
		}
		
		/*
		 * hashCode() method for Edge, which is determined by
		 * the hashCodes of the two vertices
		 */
		@Override
		public int hashCode()
		{
			return vertex1.hashCode() + vertex2.hashCode();
		}
		
		/*
		 * compareTo method sorts Edges based on their cost
		 */
		@Override
		public int compareTo(AdjacencyMap<E>.Edge edge) 
		{
			//Compare this Edge's cost to the other Edge's cost
			return cost - edge.cost;
		}
	}
	
	private HashMap<E, HashMap<E, Integer>> adjacencyMap; //Represents the map
	private HashSet<E> elements; //Set of the elements
	private HashSet<Edge> edges; //Set of the edges 
	private E randomElement; //Used for ensuring connectivity
	
	//Constructs the map
	public AdjacencyMap()
	{
		adjacencyMap = new HashMap<E, HashMap<E, Integer>>();
		elements = new HashSet<>();
		edges = new HashSet<>();
	}
	
	//Method used to determine if an element exists in the Map
	@Override
	public boolean elementExists(E element)
	{
		return elements.contains(element);
	}
	
	/*
	 * Method used to determine if an edge exists between two vertices
	 * 
	 * @param vertex1
	 * --> This is the vertex of origin
	 * @param vertex2
	 * --> This is the vertex that is being checked for whether
	 * vertex1 points to it or not
	 * 
	 * Note that because this is an undirected graph, it does 
	 * not matter which vertex is chosen as the first or second
	 * parameter, as either way if there is an edge the method
	 * will return true. However, if the first vertex does 
	 * not exist within the graph, an exception is thrown
	 */
	@Override
	public boolean edgeExists(E vertex1, E vertex2)
	{
		if(!elementExists(vertex1))
		{
			throw new IllegalArgumentException
			("Origin element does not exist in the graph.");
		}
		return adjacencyMap.get(vertex1).containsKey(vertex2);
	}
	
	/*
	 * Method used to insert an element into the Map
	 * 
	 * If the element already exists in the Map, 
	 * throw an exception
	 */
	@Override
	public void insertElement(E element)
	{
		if(elementExists(element))
		{
			throw new IllegalArgumentException
			("Element is already in the Graph.");
		}
		randomElement = element; //Update the random element to this one
		adjacencyMap.put(element, new HashMap<E, Integer>()); //Add to the Map
		elements.add(element); //Add to the set
	}
	
	/*
	 * Method used to insert an edge with a cost between two vertices
	 * 
	 * Throws an exception if one or both of the vertices does not exist
	 * or if the edge if a self-edge
	 * 
	 * Also throws an exception if cost inserted is less than 1
	 */
	@Override
	public void insertEdge(E vertex1, E vertex2, int cost)
	{
		//Call the super class method; checks for error handling
		WeightedGraph.super.insertEdge(vertex1, vertex2, cost);
		//Create connection between v1 and v2
		adjacencyMap.get(vertex1).put(vertex2, cost);
		//Create connection between v2 and v1
		adjacencyMap.get(vertex2).put(vertex1, cost);
		//Add to the set of edges
		edges.add(new Edge(vertex1, vertex2, cost));
	}
	
	/*
	 * Removes the element and all connections it had with other elements
	 */
	public void removeElement(E element)
	{
		//Remove the element from the map
		adjacencyMap.remove(element);
		//Remove all connections
		for(E connection : adjacencyMap.keySet())
		{
			//Just in case the random element gets removed
			randomElement = connection; 
			//Remove from the connecting element
			adjacencyMap.get(connection).remove(element);	
			//Remove from the set of edges
			edges.remove(new Edge(element, connection, 0));
		}
		//Remove from the set
		elements.remove(element);
	}
	
	/*
	 * Removes an edge connecting two vertices
	 */
	@Override
	public void removeEdge(E vertex1, E vertex2)
	{
		if(!elementExists(vertex1) || !elementExists(vertex2))
		{
			throw new IllegalArgumentException("One or both of "
		    + "the elements you passed in doesn't exist in the graph.");
		}
		//Remove the connections from both ends
		adjacencyMap.get(vertex1).remove(vertex2);
		adjacencyMap.get(vertex2).remove(vertex1);
		//Remove from the set of edges
		edges.remove(new Edge(vertex1, vertex2, 0));
	}
	
	/*
	 * Returns the cost to traverse between the two passed in vertices
	 * 
	 * Throws exception if an edge does not exist between these elements
	 */
	@Override
	public int getCost(E vertex1, E vertex2)
	{
		if(!edgeExists(vertex1, vertex2))
		{
			throw new IllegalArgumentException
			("There is no edge between these two elements.");
		}
		return adjacencyMap.get(vertex1).get(vertex2);
	}
	
	/*
	 * Returns all the edges that the element is connected
	 * to + the cost to reach all those edges from this element
	 * 
	 * Throw exception if the element is not in the graph
	 */
	@Override
	public String getEdges(E element)
	{
		if(!elementExists(element))
		{
			throw new IllegalArgumentException
			("This element is not in the graph.");
		}
		//Used to display the edges
		String display = "Connections from element: " + element + "\n";
		//Get the Map of edges associated with this element
		HashMap<E, Integer> edges = adjacencyMap.get(element);
		//Append all the connections
		for(E connection : adjacencyMap.get(element).keySet())
		{
			display += connection + "->" + edges.get(connection) + "\n";
		}
		return display;
	}
	
	/*
	 * Determines if the graph is connected
	 * 
	 * An undirected graph is connected if all edges
	 * can be reached from any one particular node
	 * 
	 * If the graph is empty it is considered connected
	 */
	@Override
	public boolean isConnected()
	{
		return isEmpty() || 
		breadthFirstSearch(randomElement).size() == elements.size();
	}
	
	//Returns the # of elements in the graph (elements in the set)
	@Override
	public int size()
	{
		return elements.size();
	}
	
	/*
	 * Returns the MST associated with this graph
	 * 
	 * @param kruskalOrPrim
	 * --> If true, done with Kruskal's algorithm
	 * --> If false, done with Prim's algorithm
	 */
	@Override
	public WeightedGraph<E> getMinimumSpanningTree(boolean kruskalOrPrim)
	{
		//If the graph is not connected there is no MST
		if(!isConnected())
		{
			throw new IllegalStateException
			("The graph is not connected, so an MST cannot be found.");
		}
		//Determine which algorithm to pick based on the boolean
		return kruskalOrPrim ? kruskalAlgorithm() : primAlgorithm();
	}
	
	/*
	 * Performs Kruskal's algorithm on the graph to find its MST
	 * 
	 * First sorts the edges by increasing weight.
	 * Then traverses the edges, adding an edge if it doesn't create a cycle
	 * 
	 * Cycles are detected using the DisjointSet
	 */
	private WeightedGraph<E> kruskalAlgorithm()
	{
		//Create a sorted Set of the edges
		TreeSet<Edge> sortedEdges = new TreeSet<>(edges);
		//Create a DisjointSet of the edges for detecting cycles
		DisjointSet<E> cycleDetection = new DisjointSet<>(elements);
		//Create a WeightedGraph that consists of the MST
		WeightedGraph<E> minimumSpanningTree = new AdjacencyMap<>();
		//Loop through the edges
		for(Edge edge : sortedEdges)
		{
			//Check that the new edge won't create a cycle
			if(!cycleDetection.find(edge.vertex1)
			.equals(cycleDetection.find(edge.vertex2)))
			{
				//Add vertex1 to the WeightedGraph if it is not already in there
				if(!minimumSpanningTree.elementExists(edge.vertex1))
				{
					minimumSpanningTree.insertElement(edge.vertex1);
				}
				//Add vertex2 to the WeightedGraph if it is not already in there
				if(!minimumSpanningTree.elementExists(edge.vertex2))
				{
					minimumSpanningTree.insertElement(edge.vertex2);
				}
				//Add the edge
				minimumSpanningTree.insertEdge
				(edge.vertex1, edge.vertex2, edge.cost);
				//Update the disjoint set
				cycleDetection.union(edge.vertex1, edge.vertex2);
			}
			//If cycle will be created skip this edge
		}
		return minimumSpanningTree;
	}
	
	/*
	 * Performs Prim's Algorithm on the graph to find its MST
	 * 
	 * Starts from the random element (it doesn't matter which one)
	 * and adds elements based on which is the shortest edge
	 * that connects a visited element to an unvisited element
	 * 
	 * The algorithm uses a PriorityQueue to determine which
	 * element to insert next into the graph
	 */
	private WeightedGraph<E> primAlgorithm()
	{
		//Use the random element as the first element
		E currElement = randomElement;
		//Create a Set that holds the visited elements
		HashSet<E> visited = new HashSet<>();
		//Create a PriorityQueue for quick access to the smallest edge
		PriorityQueue<Edge> edgePicker = new PriorityQueue<>();
		//Create a WeightedGraph that consists of the MST
		WeightedGraph<E> minimumSpanningTree = new AdjacencyMap<>();
		//Initially add the current element to the MST
		minimumSpanningTree.insertElement(currElement);
		//Loop until the # of vertices of the MST & the graph match
		while(this.size() != minimumSpanningTree.size())
		{
			//Mark current as visited
			visited.add(currElement);
			//Get the Map of elements that can be reached from currElement
			Map<E, Integer> reachableElements = adjacencyMap.get(currElement);
			//Push outgoing elements into PriorityQueue
			for(E element : reachableElements.keySet())
			{
				//Add outgoing edge if it reaches an unvisited element
				if(!visited.contains(element))
				{
					edgePicker.add(new Edge
					(currElement, element, reachableElements.get(element)));
				}
			}
			//Following variable holds the cheapest edge
			Edge cheapestEdge;
			/*
			 * Repeatedly pop the cheapest edge until 
			 * an edge reaching an unvisited element is found
			 * 
			 * Done at least once so an element can be obtained
			 */
			do
			{
				cheapestEdge = edgePicker.poll();
			}
			while(visited.contains(cheapestEdge.vertex2));
			//Add the outgoing element to the MST
			minimumSpanningTree.insertElement(cheapestEdge.vertex2);
			//Add the edge to the MST
			minimumSpanningTree.insertEdge
			(cheapestEdge.vertex1, cheapestEdge.vertex2, cheapestEdge.cost);
			//Update the current element
			currElement = cheapestEdge.vertex2;
		}
		return minimumSpanningTree;
	}
	
	/*
	 * Performs breadthFirstSearch (level-order traversal) on the graph.
	 * 
	 * This is done using the Queue data structure. The elements are 
	 * inserted in the order that they appear in the Map
	 * 
	 * @param source --> Where the BFS begins
	 */
	@Override
	public LinkedHashSet<E> breadthFirstSearch(E source)
	{
		//Keep track of visited elements in insertion order
		LinkedHashSet<E> visitedSet = new LinkedHashSet<>();
		//Keep track of what is being discovered (pop when visiting)
		Queue<E> discovered = new LinkedList<>();
		//Add the source to discovered
		discovered.add(source);
		//Loop through until all elements are visited
		while(!discovered.isEmpty())
		{
			//Take out an element from the queue
			E visiting = discovered.remove();
			/*
			 * Determine if the element currently visiting
			 * has already been visited; if it hasn't, process
			 * and add its successors; otherwise, skip it
			 */
			if(!visitedSet.contains(visiting))
			{
				//Add visiting to the Set
				visitedSet.add(visiting);
				//Add the visited element's successors
				for(E element : adjacencyMap.get(visiting).keySet())
				{
					//Only add to discovered if it is not in the set
					if(!visitedSet.contains(element))
					{
						discovered.add(element);
					}
				}
			}
			
		}
		return visitedSet;
	}
	
	/*
	 * Performs depth-first search on the graph
	 * 
	 * This is done using the Stack data structure. The elements are
	 * inserted in the order that they appear in the Map
	 * 
	 * @param source --> Where the DFS begins
	 */
	@Override
	public LinkedHashSet<E> depthFirstSearch(E source)
	{
		//Keep track of visited elements in insertion order
		LinkedHashSet<E> visitedSet = new LinkedHashSet<>();
		//Keep track of what is being discovered (pop when visiting)
		Stack<E> discovered = new Stack<>();
		//Push the source to discovered
		discovered.push(source);
		//Loop through until all elements are visited
		while(!discovered.isEmpty())
		{
			//Take out an element from the stack
			E visiting = discovered.pop();
			/*
			 * Determine if the element currently visiting
			 * has already been visited; if it hasn't, process
			 * and add its successors; otherwise, skip it
			 */
			if(!visitedSet.contains(visiting))
			{
				//Add visiting to the Set
				visitedSet.add(visiting);
				//Push the visited element's successors
				for(E element : adjacencyMap.get(visiting).keySet())
				{
					//Only add to discovered if it is not in the set
					if(!visitedSet.contains(element))
					{
						discovered.push(element);
					}
				}
			}
			
		}
		return visitedSet;
	}
	
	/*
	 * Obtains a graph where the edges in the graph are the cheapest
	 * route out of all edge where the given source element can
	 * reach all other elements. This is done via Dijkstra's Algorithm
	 * 
	 * The algorithm finds the cheapest path from one element
	 * to all other elements in the graph
	 * 
	 * The WeightedGraph returns gives the exact graph that
	 * provides the shortest path to all nodes based on the source node, 
	 * but not necessarily any specifics of the actual path lengths
	 */
	@Override
	public WeightedGraph<E> getShortestPathGraphFrom(E source)
	{
		/*
		 * Following inner class holds information about
		 * the predecessor and cost to reach an element from a source
		 * 
		 * This differs from the Edge class, which refers
		 * to edge relations. The cost refers to the total
		 * cost between the source and the element. 
		 * 
		 * The predecessor is simply the element reached prior
		 * to this element. This is used to determine edge information
		 * which will be necessary for creating the final WeightedGraph
		 */
		class Path implements Comparable<Path>
		{
			E predecessor; //Element before the current element
			E element; //The current element
			int cost; //Total cost from source to this element
			
			//Constructor
			Path(E predecessor, E element, int cost)
			{
				this.predecessor = predecessor;
				this.element = element;
				this.cost = cost;
			}

			/*
			 * Necessary for usage in PriorityQueue
			 * 
			 * Path comparisons are done based on cost; 
			 * the cheaper cost gets popped out of the
			 * PriorityQueue first
			 */
			@Override
			public int compareTo(Path path) 
			{
				return cost - path.cost;
			}
		}
		//Holds all elements with known paths from source
		HashMap<E, Path> knownPaths = new HashMap<E, Path>();
		/*
		 * Add all the elements to the Map; 
		 * the source has cost 0 and no predecessor; 
		 * all other elements have max cost and currently no precessor
		 */
		for(E element : elements)
		{
			knownPaths.put(element, new Path(null, element,
			element.equals(source) ? 0 : Integer.MAX_VALUE));
		}
		//PriorityQueue used to obtain the cheapest path
		PriorityQueue<Path> cheapestPath = new PriorityQueue<>();
		//Add the source to PriorityQueue
		cheapestPath.add(knownPaths.get(source));
		//Loop until the PriorityQueue has no more elements to process
		while(!cheapestPath.isEmpty())
		{
			//Pop the cheapest element to reach; this will be processed
			Path cheapest = cheapestPath.poll();
			//Obtain the map consisting of all outgoing edges from the element
			HashMap<E, Integer> outgoingEdges = 
			adjacencyMap.get(cheapest.element);
			//Update distances
			for(E element : outgoingEdges.keySet())
			{
				//Obtain the previous path
				Path oldPath = knownPaths.get(element);
				//Determine the new path (which has an updated cost)
				Path newPath = new Path(cheapest.element, 
				element, cheapest.cost + getCost(cheapest.element, element));
				//If the new path is cheaper the old path gets replaced
				if(newPath.cost < oldPath.cost)
				{
					//Update the path in the set
					knownPaths.put(element, newPath);
					//Push it back into the PriorityQueue
					cheapestPath.add(oldPath);
				}
				
			}
		}
		/*
		 * Create a WeightedGraph that will be used to display 
		   the graph based on a run-through of Dijkstra's algorithm
		 */
		WeightedGraph<E> result = new AdjacencyMap<>();
		//Add all the elements to the Graph
		for(E element : elements)
		{
			result.insertElement(element);
		}
		//Add all the edges based on results from Dijkstra's Algorithm
		for(E element : knownPaths.keySet())
		{
			//Obtain the predecessor from the Map
			E predecessor = knownPaths.get(element).predecessor;
			//Insert the edge
			result.insertEdge(element, predecessor, 
			getCost(element, predecessor));
		}
		return result;
	}
	
	//Prints all edges associated with every element of the Map
	public String toString()
	{
		String display = ""; //Used to display the Map
		//Loop through each element, and append getEdges upon each loop
		for(E element : elements)
		{
			display += getEdges(element) + "\n";
		}
		return display;
	}
}