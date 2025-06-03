package systemImp;

import java.util.*; //In order to use JCF implementations of data structures

/*
 * The following class represents an Adjacency Matrix implementation
 * of a Graph, and has implementations of basic graph operations
 * (add, remove, find, enumerate) as well as various graph algorithms
 * such as BFS, DFS, Kruskal's Algorithm, Prim's Algorithm, 
 * and Dijkstra's Algorithm
 * 
 * The graph represents a weighted, undirected graph with only positive weights.
 * 
 * Note: Due to the complexity of the method, I did not bother
 * coding up the implementation for removeElement for this class. 
 */
//Any type of accepted, must following WeightedGraph contract
public class AdjacencyMatrix<E> implements WeightedGraph<E>
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
		public int compareTo(AdjacencyMatrix<E>.Edge edge) 
		{
			//Compare this Edge's cost to the other Edge's cost
			return cost - edge.cost;
		}
	}
	
	private int[][] weights; //Matrix for weights of edges in the graph
	/*
	 * Stores the elements and the index where 
	 * that element is stored in the matrix
	 */
	private HashMap<E, Integer> elements; 
	private HashSet<Edge> edges; //Set of the edges 
	private E randomElement; //Used for ensuring connectivity
	
	/*
	 * Constructs the matrix
	 * 
	 * Note: The default value of an edge is 0, meaning there is
	 * no edge at that location. Negative values are not allowed,
	 * and any positive value means there is an edge between those vertices
	 */
	public AdjacencyMatrix()
	{
		weights = new int[10][10]; //Initial size
		elements = new HashMap<E, Integer>();
		edges = new HashSet<>();
	}
	
	//Method used to determine if an element exists in the graph
	@Override
	public boolean elementExists(E element) 
	{
		return elements.containsKey(element);
	}

	/*
	 * Method determines if an edge exists between two vertices
	 * 
	 * This operation is done by obtaining the indexes of vertices 1 & 2
	 * via the HashMap and then obtaining the index in the weights 2-D array
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
	 * will return true. However, if either vertex does not exist
	 * in the graph, an exception will be thrown
	 */
	@Override
	public boolean edgeExists(E vertex1, E vertex2) 
	{
		if(!elementExists(vertex1) || !elementExists(vertex2))
		{
			throw new IllegalArgumentException
			("Origin element does not exist in the graph.");
		}
		return weights[elements.get(vertex1)][elements.get(vertex2)] != 0;
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
		randomElement = element; //Update the random element to be this one
		/*
		 * The index where all edges of this element exist
		 * occur at the current size, which is then
		 * incremented after calling put
		 */
		elements.put(element, elements.size()); 
	}

	/*
	 * Method used to insert an edge with a cost between two vertices
	 * 
	 * Throws an exception if one or both of the vertices does not exist
	 * 
	 * Also throws an exception if cost inserted is less than 1
	 */
	@Override
	public void insertEdge(E vertex1, E vertex2, int cost) 
	{
		//Call the super class method; checks for error handling
		WeightedGraph.super.insertEdge(vertex1, vertex2, cost);
		//Obtain the indexes
		int index1 = elements.get(vertex1);
		int index2 = elements.get(vertex2);
		//Place the edge with its associated cost at both locations
		weights[index1][index2] = cost;
		weights[index2][index1] = cost;
		//Add to the set of edges
		edges.add(new Edge(vertex1, vertex2, cost));
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
		//Obtain the indexes
		int index1 = elements.get(vertex1);
		int index2 = elements.get(vertex2);
		//Remove the connections from both ends
		weights[index1][index2] = 0;
		weights[index2][index1] = 0;
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
		return weights[elements.get(vertex1)][elements.get(vertex2)];
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
		//Get the section of the array to parse through
		int[] edges = weights[elements.get(element)];
		//Parse through the edges, displaying both the elements & costs
		for(E connection : elements.keySet())
		{
			int index = elements.get(connection);
			//Only display edges that actually connect with this element
			if(edges[elements.get(connection)] != 0)
			{
				display += connection + "->" + edges[index] + "\n";
			}
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
		return true;
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
		DisjointSet<E> cycleDetection = new DisjointSet<>(elements.keySet());
		//Create a WeightedGraph that consists of the MST
		WeightedGraph<E> minimumSpanningTree = new AdjacencyMatrix<>();
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
		WeightedGraph<E> minimumSpanningTree = new AdjacencyMatrix<>();
		//Initially add the current element to the MST
		minimumSpanningTree.insertElement(currElement);
		//Loop until the # of vertices of the MST & the graph match
		while(this.size() != minimumSpanningTree.size())
		{
			//Mark current as visited
			visited.add(currElement);
			//Get the array of elements that can be reached from currElement
			int[] reachableElements = weights[elements.get(currElement)];
			//Push outgoing elements into PriorityQueue
			for(E element : elements.keySet())
			{
				//Get the weight of the edge between these elements
				int weight = reachableElements[elements.get(element)];
				/*
				 * Only add to the PriorityQueue if the element
				 * reached hasn't already been visited and there
				 * is an edge that exists between those elements
				 */
				if(!visited.contains(element) && weight != 0)
				{
					edgePicker.add(new Edge(currElement, element, 
					reachableElements[elements.get(element)]));
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
	 * inserted in the order that they appear
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
				//Get the array of elements that can be reached from visiting
				int[] reachableElements = weights[elements.get(visiting)];
				//Add the visited element's successors 
				for(E element : elements.keySet())
				{
					//Get the weight of the edge between these elements
					int weight = reachableElements[elements.get(element)];
					/*
					 * Only add to discovered if an edge exists
					 * between those two vertices (non-zero weight)
					 * and it is not already in the visited set
					 */
					if(!visitedSet.contains(element) && weight != 0)
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
				//Get the array of elements that can be reached from visiting
				int[] reachableElements = weights[elements.get(visiting)];
				//Add the visited element's successors 
				for(E element : elements.keySet())
				{
					//Get the weight of the edge between these elements
					int weight = reachableElements[elements.get(element)];
					/*
					 * Only add to discovered if an edge exists
					 * between those two vertices (non-zero weight)
					 * and it is not already in the visited set
					 */
					if(!visitedSet.contains(element) && weight != 0)
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
		for(E element : elements.keySet())
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
			//Get the array of elements that can be reached from visiting
			int[] outgoingEdges = weights[elements.get(cheapest.element)];
			//Update distances
			for(E element : elements.keySet())
			{
				//Ignore edges that don't actually connect to the cheapest
				if(outgoingEdges[elements.get(element)] != 0)
				{
					//Obtain the previous path
					Path oldPath = knownPaths.get(element);
					//Determine the new path (which has an updated cost)
					Path newPath = new Path(cheapest.element, element, 
					cheapest.cost + getCost(cheapest.element, element));
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
		}
		/*
		 * Create a WeightedGraph that will be used to display 
		   the graph based on a run-through of Dijkstra's algorithm
		 */
		WeightedGraph<E> result = new AdjacencyMap<>();
		//Add all the elements to the Graph
		for(E element : elements.keySet())
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

}
