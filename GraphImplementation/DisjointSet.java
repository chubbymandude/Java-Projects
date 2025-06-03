package systemImp;

import java.util.Arrays; //Used for using utility methods involving arrays
import java.util.Set; //Used to pass in any type of Set to the constructor
import java.util.HashSet; //Used for representing the set of elements
import java.util.HashMap; //Used for the actual DisjointSet functionality

/*
 * The following class represents a DisjointSet data structure,
 * which is used in order to group together elements that
 * are related in some way. This is mainly important
 * for implementing Kruskal's algorithm, an important
 * algorithm for finding the Minimum Spanning Tree of a graph
 * 
 * Implementation is based on this video: 
 * https://www.youtube.com/watch?v=QYivmB3B7S0
 */
public class DisjointSet<E> //Generic, so can work with any type
{
	private HashSet<E> set; //Represents the elements in the set
	private HashMap<E, E> parents; //Used to hold parents of elements
	private HashMap<E, Integer> sizes; //Used to hold sizes of element trees
	
	/*
	 * Constructs the DisjointSet based on the passed in array
	 * 
	 * Initializes the Set based on the passed in array (handles duplicates)
	 * 
	 * Initializes the parents and sizes to their initial value
	 * with all the elements entered into both HashMaps
	 */
	public DisjointSet(E[] elements)
	{
		//Initialize the HashSet containing the elements
		set = new HashSet<>(Arrays.asList(elements));
		//Initialize the HashMaps for parents and sizes
		parents = new HashMap<E, E>();
		sizes = new HashMap<E, Integer>();
		//Call makeSet for each element of the array
		for(E element : elements)
		{
			makeSet(element);
		}
		
	}
	
	/*
	 * Constructs the DisjointSet based on the passed in Set
	 * 
	 * The rest of the Constructor is the same as the above Constructor
	 */
	public DisjointSet(Set<E> elements)
	{
		//Initialize the HashSet containing the elements
		set = new HashSet<>(elements);
		//Initialize the HashMaps for parents and sizes
		parents = new HashMap<E, E>();
		sizes = new HashMap<E, Integer>();
		//Call makeSet for each element of the array
		for(E element : elements)
		{
			makeSet(element);
		}
	}
	
	/*
	 * Used for initializing the Map contain the DisjointSet; 
	 * all elements start off pointing to itself and have a size of 1
	 */
	private void makeSet(E element)
	{
		parents.put(element, element);
		sizes.put(element, 1);
	}
	
	/*
	 * Used for finding the root of the tree 
	 * the specified element resides in; 
	 * 
	 * This method is done with recursion until 
	 * the parent is found; if the element passed in
	 * is itself a parent, the element itself is returned
	 */
	public E find(E element)
	{
		//Base case, parent is the element itself
		if(parents.get(element).equals(element))
		{
			return element;
		}
		else //Need to continue traversing
		{
			/*
			 * Path compression; ensure minimum # of edges 
			 * will be needed to obtain the root element
			 */
			parents.put(element, find(parents.get(element)));
			//Move on to the next parent
			return parents.get(element);
		}
	}
	
	/*
	 * Performs the union operation of the DisjointSet;
	 * merges the sets that exist between the two elements
	 * 
	 * If one or both of the elements does not exist in the original
	 * array, then an exception is thrown
	 * 
	 * If both elements are in the same set, then nothing happens
	 * 
	 * @param element1, element2
	 * --> The two elements whose sets are going to be merged
	 */
	public void union(E element1, E element2)
	{
		if(!set.contains(element1) || !set.contains(element2))
		{
			throw new IllegalArgumentException("One or both of the elements "
		    + "you passed in are not in your "
		    + "original set, and thus cannot be merged.");
		}
		//Find the roots of the two elements
		E root1 = find(element1);
		E root2 = find(element2);
		//If both have the same root, do nothing
		if(root1.equals(root2))
		{
			return;
		}
		/*
		 * If the first element has a smaller tree than the second element, 
		 * set the parent of the first element's root to the root
		 * of the second element's tree (so that increasing height
		 * is not an issue)
		 */
		else if(sizes.get(root1).compareTo(sizes.get(root2)) < 0)
		{
			parents.put(root1, root2); //Update parent
			sizes.put(root2, sizes.get(root1) + sizes.get(root2)); //Update size
		}
		/*
		 * Otherwise the second element's root's 
		 * parent becomes the first element's root
		 */
		else
		{
			parents.put(root2, root1); //Update parent
			sizes.put(root1, sizes.get(root1) + sizes.get(root2));
		}
	}
	
	/*
	 * For debugging purposes; prints out the DisjointSet
	 */
	public String toString()
	{
		String display = ""; //Used to hold the display of the DisjointSet
		//Loop through the elements of the array
		for(E element : set)
		{
			//Print each element's parent and size
			display += element + "->" + parents.get(element)
			+ ", Size: " + sizes.get(element) + "\n";
		}
		return display;
	}
}