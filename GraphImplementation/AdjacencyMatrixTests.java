package tests;

//For testing purposes
import static org.junit.Assert.*;
import org.junit.Test;

import systemImp.*; //What needs to be tested
//Following are required to test certain methods
import java.util.LinkedHashSet; 

/*
 * The following class is used to test the AdjacencyMatrix
 */
public class AdjacencyMatrixTests 
{
	/*
	 * Tests standard operations of AdjacencyMatrix
	 */
	@Test
	public void basicGraphOperationsTest()
	{
		AdjacencyMatrix<String> matrix = new AdjacencyMatrix<>();
		//Test inserting elements and checking element existence
		matrix.insertElement("SpongeBob SquarePants");
		assertTrue(matrix.elementExists("SpongeBob SquarePants"));
		//Test edge existence with another element + getCost()
		matrix.insertElement("Patrick Star");
		matrix.insertEdge("SpongeBob SquarePants", "Patrick Star", 2);
		assertTrue(matrix.edgeExists("SpongeBob SquarePants", "Patrick Star"));
		assertTrue(matrix.edgeExists("Patrick Star", "SpongeBob SquarePants"));
		assertTrue(matrix.getCost
		("SpongeBob SquarePants", "Patrick Star") == 2);
		//Add some more elements
		matrix.insertElement("Sandy Cheeks");
		matrix.insertElement("Eugene H. Krabs");
		matrix.insertElement("Squidward Tentacles");
		//Add some more edges
		matrix.insertEdge("SpongeBob SquarePants", "Sandy Cheeks", 3);
		matrix.insertEdge("SpongeBob SquarePants", "Squidward Tentacles", 50);
		matrix.insertEdge("SpongeBob SquarePants", "Eugene H. Krabs", 7);
		matrix.insertEdge("Patrick Star", "Sandy Cheeks", 10);
		//Test removal of edge and test that they are no longer connected
		matrix.removeEdge("SpongeBob SquarePants", "Eugene H. Krabs");
		assertFalse(matrix.edgeExists
		("SpongeBob SquarePants", "Eugene H. Krabs"));
		assertFalse(matrix.edgeExists
		("Eugene H. Krabs", "SpongeBob SquarePants"));
	}
	
	/*
	 * Tests that the getMinimumSpanningTree method
	 * works with Kruskal's algorithm
	 */
	@Test
	public void testKruskalAlgorithm()
	{
		AdjacencyMatrix<Character> matrix = new AdjacencyMatrix<>();
		
		//Insert elements
		matrix.insertElement('A');
		matrix.insertElement('B');
		matrix.insertElement('C');
		matrix.insertElement('D');
		matrix.insertElement('E');
		//Insert edges
		matrix.insertEdge('A', 'B', 50);
		matrix.insertEdge('A', 'E', 60);
		matrix.insertEdge('B', 'C', 10);
		matrix.insertEdge('B', 'D', 30);
		matrix.insertEdge('C', 'D', 40);
		matrix.insertEdge('D', 'E', 20);
		
		//Call Kruskal's algorithm
		WeightedGraph<Character> minimumSpanningTree 
		= matrix.getMinimumSpanningTree(true);
		
		assertTrue(minimumSpanningTree.edgeExists('A', 'B'));
		assertTrue(minimumSpanningTree.edgeExists('B', 'C'));
		assertTrue(minimumSpanningTree.edgeExists('B', 'D'));
		assertTrue(minimumSpanningTree.edgeExists('D', 'E'));
		
		assertTrue(minimumSpanningTree.size() == 5);
	}
	
	/*
	 * Tests that the getMinimumSpanningTree method
	 * works with Prim's algorithm
	 */
	@Test
	public void testPrimAlgorithm()
	{
		AdjacencyMatrix<Character> matrix = new AdjacencyMatrix<>();
		
		//Insert elements
		matrix.insertElement('A');
		matrix.insertElement('B');
		matrix.insertElement('C');
		matrix.insertElement('D');
		matrix.insertElement('E');
		//Insert edges
		matrix.insertEdge('A', 'B', 50);
		matrix.insertEdge('A', 'E', 60);
		matrix.insertEdge('B', 'C', 10);
		matrix.insertEdge('B', 'D', 30);
		matrix.insertEdge('C', 'D', 40);
		matrix.insertEdge('D', 'E', 20);
		
		//Call Kruskal's algorithm
		WeightedGraph<Character> minimumSpanningTree 
		= matrix.getMinimumSpanningTree(false);
		
		assertTrue(minimumSpanningTree.edgeExists('A', 'B'));
		assertTrue(minimumSpanningTree.edgeExists('B', 'C'));
		assertTrue(minimumSpanningTree.edgeExists('B', 'D'));
		assertTrue(minimumSpanningTree.edgeExists('D', 'E'));
		
		assertTrue(minimumSpanningTree.size() == 5);
	}
	
	//Tests breadth-first search for the AdjacencyMatrix
	@Test
	public void testBFS()
	{
		AdjacencyMatrix<String> matrix = new AdjacencyMatrix<>();
		//Insert elements
		matrix.insertElement("SpongeBob SquarePants");
		matrix.insertElement("Patrick Star");
		matrix.insertElement("Sandy Cheeks");
		matrix.insertElement("Eugene H. Krabs");
		matrix.insertElement("Squidward Tentacles");
		matrix.insertElement("Sheldon J. Plankton");
		//Insert edges
		matrix.insertEdge("SpongeBob SquarePants", "Patrick Star", 2);
		matrix.insertEdge("SpongeBob SquarePants", "Sandy Cheeks", 4);
		matrix.insertEdge("SpongeBob SquarePants", "Eugene H. Krabs", 6);
		matrix.insertEdge("Patrick Star", "Squidward Tentacles", 10);
		matrix.insertEdge("Sheldon J. Plankton", "Eugene H. Krabs", 24);
		matrix.insertEdge("Sheldon J. Plankton", "Squidward Tentacles", 16);
		
		//Perform BFS
		LinkedHashSet<String> bfs = 
		matrix.breadthFirstSearch("SpongeBob SquarePants");
		
		//Outcome should roughly be level-order traversal
		assertEquals(bfs.toString(), "[SpongeBob SquarePants, "
		+ "Patrick Star, Eugene H. Krabs, Sandy Cheeks, "
		+ "Squidward Tentacles, Sheldon J. Plankton]");
	}
	
	//Tests depth-first search for the AdjacencyMatrix
	@Test
	public void testDFS()
	{
		AdjacencyMatrix<String> matrix = new AdjacencyMatrix<>();
		//Insert elements
		matrix.insertElement("SpongeBob SquarePants");
		matrix.insertElement("Patrick Star");
		matrix.insertElement("Sandy Cheeks");
		matrix.insertElement("Eugene H. Krabs");
		matrix.insertElement("Squidward Tentacles");
		matrix.insertElement("Sheldon J. Plankton");
		//Insert edges
		matrix.insertEdge("SpongeBob SquarePants", "Patrick Star", 2);
		matrix.insertEdge("SpongeBob SquarePants", "Sandy Cheeks", 4);
		matrix.insertEdge("SpongeBob SquarePants", "Eugene H. Krabs", 6);
		matrix.insertEdge("Patrick Star", "Squidward Tentacles", 10);
		matrix.insertEdge("Sheldon J. Plankton", "Eugene H. Krabs", 24);
		matrix.insertEdge("Sheldon J. Plankton", "Squidward Tentacles", 16);
		
		//Perform DFS
		LinkedHashSet<String> dfs = 
		matrix.depthFirstSearch("SpongeBob SquarePants");
		
		/*
		 * Outcome should show that algorithm goes 
		 * as deep as possible before backtracking
		 */
		assertEquals(dfs.toString(), "[SpongeBob SquarePants, "
		+ "Sandy Cheeks, Eugene H. Krabs, Sheldon J. Plankton, "
		+ "Squidward Tentacles, Patrick Star]");
	}
	
	/*
	 * Tests Dijkstra's Algorithm from AdjacencyMap
	 */
	@Test
	public void testDijkstrasAlgorithm()
	{
		AdjacencyMatrix<Integer> matrix = new AdjacencyMatrix<>();
		
		//Insert elements
		matrix.insertElement(1);
		matrix.insertElement(2);
		matrix.insertElement(3);
		matrix.insertElement(4);
		matrix.insertElement(5);
		
		//Insert edges
		matrix.insertEdge(1, 2, 5);
		matrix.insertEdge(1, 3, 8);
		matrix.insertEdge(2, 3, 1);
		matrix.insertEdge(2, 4, 10);
		matrix.insertEdge(3, 4, 3);
		matrix.insertEdge(4, 5, 9);
		
		WeightedGraph<Integer> dijkstra = matrix.getShortestPathGraphFrom(1);
		
		assertTrue(dijkstra.edgeExists(1, 2));
		assertTrue(dijkstra.edgeExists(2, 3));
		assertTrue(dijkstra.edgeExists(3, 4));
		assertTrue(dijkstra.edgeExists(4, 5));
	}
}
