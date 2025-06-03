package tests;

//For testing purposes
import static org.junit.Assert.*;
import org.junit.Test;

//What needs to be tested
import systemImp.*;

/*
 * Following class is meant for Testing 
 * of the DisjointSet
 */
public class DisjointSetTests 
{
	/*
	 * Test to see if makeSet properly initializes
	 * both the parents and size array 
	 */
	@Test
	public void testMakeSet()
	{
		//Create a DisjointSet
		Integer[] noDuplicates = {1, 5, 2, 6, 3};
		DisjointSet<Integer> ds = new DisjointSet<>(noDuplicates);
		//Check that the toString matches accordingly
		assertEquals(ds.toString(), "1->1, Size: 1\n2->2, Size: 1"
		+ "\n3->3, Size: 1\n5->5, Size: 1\n6->6, Size: 1\n");
	}
	
	/*
	 * Tests exception handling for the union method
	 * of the DisjointSet, when elements that are not
	 * in the original set are passed in
	 */
	@Test
	public void testUnionExceptionHandling()
	{
		//Create a DisjointSet
		Integer[] noDuplicates = {1, 5, 2, 6, 3};
		DisjointSet<Integer> ds = new DisjointSet<>(noDuplicates);
		//Call union with an element that isn't in the original set
		try
		{
			ds.union(5, 7); //7 is not in the set
			fail(); //Make the test fail if the previous line works
		}
		catch(IllegalArgumentException e) {};
	}

	/*
	 * Tests the basic functionality of the DisjointSet
	 */
	@Test
	public void testDisjointSetFindAndUnionWithExample()
	{
		Character[] example = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
		DisjointSet<Character> ds = new DisjointSet<>(example);
		ds.union('B', 'F');
		ds.union('F', 'H');
		assertTrue(ds.find('H') == 'B');
		ds.union('A', 'G');
		ds.union('F', 'A');
		assertTrue(ds.find('G') == 'B');
		ds.union('C', 'D');
		ds.union('C', 'E');
		assertTrue(ds.find('E') == 'C');
		ds.union('D', 'A');
		assertTrue(ds.find('E') == 'B');
	}
}
