package tests;

//For testing purposes
import static org.junit.Assert.*;
import org.junit.Test;

import systemImp.*; //What needs to be tested
//Following are required to test certain methods
import java.util.LinkedHashSet; 

/*
 * The following class is used to test the AdjacencyMap
 */
public class AdjacencyMapTests 
{
	/*
	 * Tests standard operations of AdjacencyMap
	 */
	@Test
	public void basicGraphOperationsTest()
	{
		AdjacencyMap<String> map = new AdjacencyMap<>();
		//Test inserting elements and checking element existence
		map.insertElement("SpongeBob SquarePants");
		assertTrue(map.elementExists("SpongeBob SquarePants"));
		assertFalse(map.edgeExists("SpongeBob SquarePants", "Patrick Star"));
		//Test edge existence with another element + getCost()
		map.insertElement("Patrick Star");
		map.insertEdge("SpongeBob SquarePants", "Patrick Star", 2);
		assertTrue(map.edgeExists("SpongeBob SquarePants", "Patrick Star"));
		assertTrue(map.edgeExists("Patrick Star", "SpongeBob SquarePants"));
		assertTrue(map.getCost("SpongeBob SquarePants", "Patrick Star") == 2);
		//Add some more elements
		map.insertElement("Sandy Cheeks");
		map.insertElement("Eugene H. Krabs");
		map.insertElement("Squidward Tentacles");
		//Add some more edges
		map.insertEdge("SpongeBob SquarePants", "Sandy Cheeks", 3);
		map.insertEdge("SpongeBob SquarePants", "Squidward Tentacles", 50);
		map.insertEdge("SpongeBob SquarePants", "Eugene H. Krabs", 7);
		map.insertEdge("Patrick Star", "Sandy Cheeks", 10);
		//Test removal of edge and test that they are no longer connected
		map.removeEdge("SpongeBob SquarePants", "Eugene H. Krabs");
		assertFalse(map.edgeExists("SpongeBob SquarePants", "Eugene H. Krabs"));
		assertFalse(map.edgeExists("Eugene H. Krabs", "SpongeBob SquarePants"));
		/*
		 * Test removal of element and test that 
		 * no connections exist anymore with that element
		 */
		map.removeElement("Squidward Tentacles");
		assertFalse(map.elementExists("Squidward Tentacles"));
		assertFalse(map.edgeExists
		("SpongeBob SquarePants", "Squidward Tentacles"));
	}
	
	//Tests breadth-first search for the AdjacencyMap
	@Test
	public void testBFS()
	{
		AdjacencyMap<String> map = new AdjacencyMap<>();
		//Insert elements
		map.insertElement("SpongeBob SquarePants");
		map.insertElement("Patrick Star");
		map.insertElement("Sandy Cheeks");
		map.insertElement("Eugene H. Krabs");
		map.insertElement("Squidward Tentacles");
		map.insertElement("Sheldon J. Plankton");
		//Insert edges
		map.insertEdge("SpongeBob SquarePants", "Patrick Star", 2);
		map.insertEdge("SpongeBob SquarePants", "Sandy Cheeks", 4);
		map.insertEdge("SpongeBob SquarePants", "Eugene H. Krabs", 6);
		map.insertEdge("Patrick Star", "Squidward Tentacles", 10);
		map.insertEdge("Sheldon J. Plankton", "Eugene H. Krabs", 24);
		map.insertEdge("Sheldon J. Plankton", "Squidward Tentacles", 16);
		
		//Perform BFS
		LinkedHashSet<String> bfs = 
		map.breadthFirstSearch("SpongeBob SquarePants");
		
		//Outcome should roughly be level-order traversal
		assertEquals(bfs.toString(), "[SpongeBob SquarePants, "
		+ "Patrick Star, Eugene H. Krabs, Sandy Cheeks, "
		+ "Squidward Tentacles, Sheldon J. Plankton]");
	}
	
	//Tests depth-first search for the AdjacencyMap
	@Test
	public void testDFS()
	{
		AdjacencyMap<String> map = new AdjacencyMap<>();
		//Insert elements
		map.insertElement("SpongeBob SquarePants");
		map.insertElement("Patrick Star");
		map.insertElement("Sandy Cheeks");
		map.insertElement("Eugene H. Krabs");
		map.insertElement("Squidward Tentacles");
		map.insertElement("Sheldon J. Plankton");
		//Insert edges
		map.insertEdge("SpongeBob SquarePants", "Patrick Star", 2);
		map.insertEdge("SpongeBob SquarePants", "Sandy Cheeks", 4);
		map.insertEdge("SpongeBob SquarePants", "Eugene H. Krabs", 6);
		map.insertEdge("Patrick Star", "Squidward Tentacles", 10);
		map.insertEdge("Sheldon J. Plankton", "Eugene H. Krabs", 24);
		map.insertEdge("Sheldon J. Plankton", "Squidward Tentacles", 16);
		
		//Perform DFS
		LinkedHashSet<String> dfs = 
		map.depthFirstSearch("SpongeBob SquarePants");
		
		/*
		 * Outcome should show that algorithm goes 
		 * as deep as possible before backtracking
		 */
		assertEquals(dfs.toString(), "[SpongeBob SquarePants, "
		+ "Sandy Cheeks, Eugene H. Krabs, Sheldon J. Plankton, "
		+ "Squidward Tentacles, Patrick Star]");
	}
	
	/*
	 * Tests that the getMinimumSpanningTree method
	 * works with Kruskal's algorithm
	 */
	@Test
	public void testKruskalAlgorithm()
	{
		AdjacencyMap<Character> map = new AdjacencyMap<>();
		
		//Insert elements
		map.insertElement('A');
		map.insertElement('B');
		map.insertElement('C');
		map.insertElement('D');
		map.insertElement('E');
		//Insert edges
		map.insertEdge('A', 'B', 50);
		map.insertEdge('A', 'E', 60);
		map.insertEdge('B', 'C', 10);
		map.insertEdge('B', 'D', 30);
		map.insertEdge('C', 'D', 40);
		map.insertEdge('D', 'E', 20);
		
		//Call Kruskal's algorithm
		WeightedGraph<Character> minimumSpanningTree 
		= map.getMinimumSpanningTree(true);
		
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
		AdjacencyMap<Character> map = new AdjacencyMap<>();
		
		//Insert elements
		map.insertElement('A');
		map.insertElement('B');
		map.insertElement('C');
		map.insertElement('D');
		map.insertElement('E');
		//Insert edges
		map.insertEdge('A', 'B', 50);
		map.insertEdge('A', 'E', 60);
		map.insertEdge('B', 'C', 10);
		map.insertEdge('B', 'D', 30);
		map.insertEdge('C', 'D', 40);
		map.insertEdge('D', 'E', 20);
		
		//Call Kruskal's algorithm
		WeightedGraph<Character> minimumSpanningTree 
		= map.getMinimumSpanningTree(false);
		
		assertTrue(minimumSpanningTree.edgeExists('A', 'B'));
		assertTrue(minimumSpanningTree.edgeExists('B', 'C'));
		assertTrue(minimumSpanningTree.edgeExists('B', 'D'));
		assertTrue(minimumSpanningTree.edgeExists('D', 'E'));
		
		assertTrue(minimumSpanningTree.size() == 5);
	}
	
	/*
	 * Tests Dijkstra's Algorithm from AdjacencyMap
	 */
	@Test
	public void testDijkstrasAlgorithm()
	{
		AdjacencyMap<Integer> map = new AdjacencyMap<>();
		
		//Insert elements
		map.insertElement(1);
		map.insertElement(2);
		map.insertElement(3);
		map.insertElement(4);
		map.insertElement(5);
		
		//Insert edges
		map.insertEdge(1, 2, 5);
		map.insertEdge(1, 3, 8);
		map.insertEdge(2, 3, 1);
		map.insertEdge(2, 4, 10);
		map.insertEdge(3, 4, 3);
		map.insertEdge(4, 5, 9);
		
		WeightedGraph<Integer> dijkstra = map.getShortestPathGraphFrom(1);
		
		assertTrue(dijkstra.edgeExists(1, 2));
		assertTrue(dijkstra.edgeExists(2, 3));
		assertTrue(dijkstra.edgeExists(3, 4));
		assertTrue(dijkstra.edgeExists(4, 5));
	}
	
}
