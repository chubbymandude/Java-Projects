package sorting;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Driver
{	
	private JFrame frame; //Creates the frame
	private JPanel panel; //Creates the panel
	private JTextField input; //Takes in array input
	//Displays searching/sorting algorithm based on button click
	private JLabel result; 
	private String inputText; //Converts JTextField input to String
	
	public Driver()
	{
		frame = new JFrame(); //Instantiate frame
		input = new JTextField();
		result = new JLabel();
		
		/*
		 * Visualizes linear search, where the last element inputted
		 * into the array is the value being searched for
		 * 
		 * Highlight step by step each comparison being made
		 * until either the value is found or the end of the array is reached
		 */
		JButton linearSearch = new JButton("Linear Search");
		linearSearch.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				inputText = input.getText()
				.substring(0, input.getText().length() - 3);
				
				String[] arr  = inputText.split(", ");
				int[] toSearch = new int[arr.length];
				for(int i = 0; i < arr.length; i++)
				{
					toSearch[i] = Integer.valueOf(arr[i]);
				}
				
				int value = Integer.valueOf
				(input.getText().substring(input.getText().length() - 1));
				
				result.setText("<html>Searching for " + value + " in: " + 
				java.util.Arrays.toString(toSearch) + "<br/>");
				
				int res = -1;
				
				for(int i = 0; i < toSearch.length; i++)
				{
					result.setText(result.getText() + "<br/>" 
					+ toSearch[i] + " = " + value + "? : " + 
					(toSearch[i] == value));
					if(toSearch[i] == value)
					{
						res = i;
						break;
					}
				}
				
				result.setText(result.getText() + "<br/><br/>" + (res != -1 ? 
				"Element was found at index " + res + "." 
				: "Element was not found in the given array."));
			}
			
		});
		
		/*
		 * Button, when clicked, performs binary search
		 * on the given array, with the last value inputted
		 * being the value that is searched for
		 * 
		 * First sorts the array (if the user did not input a sorted array), 
		 * then visualizes all the branching that occurs during 
		 * binary search until the algorithm stops and
		 * determines whether the item was found or not
		 * and the index it was found on if it was found
		 */
		JButton binarySearch = new JButton("Binary Search");
		binarySearch.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				inputText = input.getText()
				.substring(0, input.getText().length() - 3);
				
				String[] arr  = inputText.split(", ");
				int[] toSearch = new int[arr.length];
				for(int i = 0; i < arr.length; i++)
				{
					toSearch[i] = Integer.valueOf(arr[i]);
				}
				
				int value = Integer.valueOf
				(input.getText().substring(input.getText().length() - 1));
				
				result.setText("<html>Searching for " + value + " in: " + 
				java.util.Arrays.toString(toSearch) + "<br/><br/>");
				
				Arrays.sort(toSearch);
				
				//Just in case the user inputs an unsorted array
				result.setText(result.getText() + "Sorted Array: " + 
				java.util.Arrays.toString(toSearch) + "<br/>");
				
				int res = -1;
				int left = 0, right = arr.length - 1;
		        
		        while(left <= right) 
		        {
		            int mid = left + (right - left) / 2;
		            if(toSearch[mid] == value) 
		            {
		            	result.setText(result.getText() + "<br/>" + 
				        toSearch[mid] + " = " + value + ", search is done");
		                res = mid;
		                break;
		            } 
		            else if(toSearch[mid] < value) 
		            {
		            	result.setText(result.getText() + "<br/>" + toSearch
		            	[mid] + " &lt; " + value + ", move to the right.");
		            	left = mid + 1; 
		            }
		            else 
		            {
		            	result.setText(result.getText() + "<br/>" + 
				        toSearch[mid] + " > " + value + ", move to the left.");
		            	right = mid - 1;
		            }
		        }
				
				result.setText(result.getText() + "<br/><br/>" + (res != -1 ? 
				"Element was found at index " + res + "." 
				: "Element was not found in the given array."));
			}
		});
		
		/*
		 * Button, when clicked, visualizes the step-by-step
		 * algorithm of BubbleSort (specifically, all the swaps
		 * and each individual pass of the array)
		 */
		JButton bubbleSort = new JButton("BubbleSort");
		bubbleSort.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				/*
				 * Convert the input given
				 * to an integer array
				 */
				inputText = input.getText();
				String[] arr = inputText.split(", ");
				int[] toSort = new int[arr.length];
				for(int i = 0; i < arr.length; i++)
				{
					toSort[i] = Integer.valueOf(arr[i]);
				}
				/*
				 * Create a Thread to perform BubbleSort
				 * and log both when a pass is complete
				 * and each swap until the array
				 * is sorted, adding each of these logs
				 * to the result label
				 */
				result.setText("<html>Original Array: " + 
				java.util.Arrays.toString(toSort) + "<br/>");
				Thread performBubbleSort = new Thread(() -> 
				{
					for(int i = 0; i < toSort.length; i++)
					{
						result.setText(result.getText() 
						+ "<br/>Pass " + (i + 1) + ": " + 
						java.util.Arrays.toString(toSort));
						boolean swapped = false;
						for(int j = 0; j < arr.length - i - 1; j++)
						{
							if(toSort[j] > toSort[j + 1])
							{
								swapped = true;
								int temp = toSort[j];
								toSort[j] = toSort[j + 1];
								toSort[j + 1] = temp;
								result.setText(result.getText() + 
								" -> Swap indexes " + j + " and " + (j + 1) 
								+ ", " + java.util.Arrays.toString(toSort));
							}
						}
						if(swapped == false) 
						{
							//At the end, the array is sorted (log this)
							result.setText(result.getText() + " -> " + "Sorted");
							break;
						}
					}
		        });
				
				//Start the thread
				performBubbleSort.start();
				
				//Join the thread so it runs before the main
				try 
				{
					performBubbleSort.join();
				} 
				catch(InterruptedException e1) //Exception handling
				{
					e1.printStackTrace();
				}
			}
		});
		
		/*
		 * Button, when clicked, visualizes SelectionSort, 
		 * highlighting the minimum at each pass
		 * and highlighting the swap that occurs at each pass
		 */
		JButton selectionSort = new JButton("SelectionSort");
		selectionSort.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				inputText = input.getText();
				String[] arr = inputText.split(", ");
				int[] toSort = new int[arr.length];
				for(int i = 0; i < arr.length; i++)
				{
					toSort[i] = Integer.valueOf(arr[i]);
				}
				result.setText("<html>Original Array: " + 
				java.util.Arrays.toString(toSort) + "<br/>");
				Thread performSelectionSort = new Thread(() -> 
				{
					int n = arr.length;
			        for(int i = 0; i < n - 1; i++) 
			        {       	
			            int minIndex = i;
			            
			            for(int j = i + 1; j < n; j++) 
			            {
			                if(toSort[j] < toSort[minIndex])
			                	minIndex = j;
			            }
			            result.setText(result.getText() + "<br/>Pass " + (i + 1) 
			            + ": Minimum = " + toSort[minIndex]);
			            int temp = toSort[minIndex];
			            toSort[minIndex] = toSort[i];
			            toSort[i] = temp;
			            result.setText(result.getText() + " -> " + 
			            java.util.Arrays.toString(toSort));
					}
		        });
				
				performSelectionSort.start();
				
				try 
				{
					performSelectionSort.join();
				} 
				catch(InterruptedException e1) 
				{
					e1.printStackTrace();
				}
				
				result.setText(result.getText() + " -> Sorted");
			}
		});
		
		/*
		 * Button, when clicked, visualizes InsertionSort, 
		 * highlighting at each pass between which indexes
		 * each element being processed gets placed
		 * until the entire array is sorted
		 */
		JButton insertionSort = new JButton("InsertionSort");
		insertionSort.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				inputText = input.getText();
				String[] arr = inputText.split(", ");
				int[] toSort = new int[arr.length];
				for(int i = 0; i < arr.length; i++)
				{
					toSort[i] = Integer.valueOf(arr[i]);
				}
				result.setText("<html>Original Array: " + 
				java.util.Arrays.toString(toSort) + "<br/>");
				Thread performInsertionSort = new Thread(() -> 
				{
					for(int i = 1; i < toSort.length; i++) 
					{
						result.setText(result.getText() 
						+ "<br/>Pass " + i + ": ");
			            int key = toSort[i];
			            int j = i - 1;  
			            
			            while(j >= 0 && toSort[j] > key) 
			            {
			                toSort[j + 1] = toSort[j]; 
			                j--;                 
			            }
			            
			            if(j == -1)
			            {
			            	result.setText(result.getText() 
			            	+ "Place " + key + " at the start of the array.");
			            }
			            else if(j == toSort.length - 2)
			            {
			            	result.setText(result.getText() 
					        + "Place " + key + " at the end of the array.");
			            }
			            else
			            {
			            	result.setText(result.getText() 
					        + "Place " + key + " between index " + j + 
					        " and index " + (j + 2));
			            }
			            toSort[j + 1] = key; 
			            result.setText(result.getText() + " -> " 
			            + java.util.Arrays.toString(toSort));
			        }
		        });
				
				performInsertionSort.start();
				
				try 
				{
					performInsertionSort.join();
				} 
				catch(InterruptedException e1) 
				{
					e1.printStackTrace();
				}
				
				result.setText(result.getText() + " -> Sorted");
			}
		});
		
		/*
		 * When button clicked, visualizes the merging
		 * process of mergeSort at the first call of the method
		 * 
		 * First performs mergeSort on the left and right
		 * halves of the array, then visualizes
		 * the merge method of the mergeSort on the left and right
		 * sides by highlighting how each element gets placed
		 * into the temporary array 
		 */
		JButton mergeSort = new JButton("MergeSort");
		mergeSort.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				inputText = input.getText();
				String[] arr = inputText.split(", ");
				int[] toSort = new int[arr.length];
				for(int i = 0; i < arr.length; i++)
				{
					toSort[i] = Integer.valueOf(arr[i]);
				}
				result.setText("<html>Original Array: " + 
				java.util.Arrays.toString(toSort) + "<br/>");
				Thread sortFront = new Thread(() ->
				{
					mergeSort(toSort, 0, (toSort.length - 1) / 2);
				});
				Thread sortBack = new Thread(() ->
				{
					mergeSort(toSort, 
					((toSort.length - 1) / 2) + 1, toSort.length - 1);
				});
				
				sortFront.start();
				sortBack.start();
				
				try 
				{
					sortFront.join();
					sortBack.join();
				} 
				catch (InterruptedException e1) 
				{
					e1.printStackTrace();
				}
				
				result.setText(result.getText() + "New Array (to demonstrate "
				+ "merging part of MergeSort): " + 
				java.util.Arrays.toString(toSort) + "<br/><br/>");
				
				Thread performMergeSort = new Thread(() -> 
				{
					int[] temp = new int[toSort.length];
					
					result.setText(result.getText() + "Temporary Array: " +  
					java.util.Arrays.toString(temp) + "<br/><br/>");
					
					int from = 0, mid = (toSort.length - 1) / 2
					, to = toSort.length - 1, i = 0, j = mid + 1, k = 0;     
			        
			        result.setText(result.getText() + "Note: The left subarray "
			        + "starts at index 0. The right subarray "
			        + "starts at index " + j + ".<br/>The element "
			        + "left to &lt; or > is from the left subarray, while the"
			        + " element right to those operators is from "
			        + "the right subarray.<br/><br/>");

			        while(i <= mid && j <= to) 
			        {
			            if(toSort[i] <= toSort[j]) 
			            {
			            	result.setText(result.getText() + toSort[i]
			            	+ " &lt;= " + toSort[j] + " -> ");
			            	temp[k++] = toSort[i++];  
			            }
			        	else 
			            {
			        		result.setText(result.getText() + toSort[i]
					         + " >= " + toSort[j] + " -> ");
			                temp[k++] = toSort[j++]; 
			            }
			            result.setText(result.getText() + 
				        java.util.Arrays.toString(temp) + "<br/>");
			        }

			        result.setText(result.getText() + "Adding remaining "
				    + "elements from left subarray...<br/>");
			        while(i <= mid) 
			        {
			            temp[k++] = toSort[i++];
			        }

			        result.setText(result.getText() + "Adding remaining "
				    + "elements from right subarray...<br/><br/>");
			        while (j <= to) 
			        {
			            temp[k++] = toSort[j++];
			        }

			        for(int p = 0; p < temp.length; p++) 
			        {
			            toSort[from + p] = temp[p];
			        }
			        
			        result.setText(result.getText() + "Final Sorted Array: "
			        + java.util.Arrays.toString(toSort));
		        });
				
				performMergeSort.start();
				
				try 
				{
					performMergeSort.join();
				} 
				catch(InterruptedException e1) 
				{
					e1.printStackTrace();
				}
			}
			
			/*
			 * Solely to sort the first and second half of the array
			 * in order to demonstrate how merging works in merge sort
			 */
		    private static void mergeSort(int[] arr, int from, int to) 
		    {
		        if (from >= to) 
		            return; 
		        int mid = (from + to) / 2;
		        mergeSort(arr, from, mid);
		        mergeSort(arr, mid + 1, to);
		        merge(arr, from, mid, to);
		    }

		    /*
		     * Helps out the mergeSort method above (not the one
		     * to be used when actually demonstrating mergeSort)
		     */
		    private static void merge(int[] arr, int from, int mid, int to) 
		    {
		        int[] temp = new int[to - from + 1];
		        int i = from, j = mid + 1, k = 0;   
		        
		        while (i <= mid && j <= to) 
		        {
		            if (arr[i] <= arr[j]) temp[k++] = arr[i++];  
		        	else temp[k++] = arr[j++]; 
		        }
		        
		        while (i <= mid) temp[k++] = arr[i++];
		        while (j <= to) temp[k++] = arr[j++];
		        for (int p = 0; p < temp.length; p++) arr[from + p] = temp[p];
		    }
		});
		
		/*
		 * Visualizes quickSort's splitting algorithm
		 * for the iteration at the top of the stack
		 * 
		 * Highlights which pivot is chosen (the element at the end)
		 * 
		 * Highlights how the location of the smaller index changes
		 * as well as any swaps that occur during the process
		 * 
		 * Logs the final swap and presents the end array
		 * where the pivot is in the right location and
		 * all the elements smaller than it are to its left
		 * while all the elements larger than it are to its right
		 */
		JButton quickSort = new JButton("QuickSort");
		quickSort.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				inputText = input.getText();
				String[] arr = inputText.split(", ");
				int[] toSort = new int[arr.length];
				for(int i = 0; i < arr.length; i++)
				{
					toSort[i] = Integer.valueOf(arr[i]);
				}
				result.setText("<html>Original Array: " + 
				java.util.Arrays.toString(toSort) + "<br/><br/>");
				
				Thread performQuickSort = new Thread(() -> 
				{
					int low = 0, high = toSort.length - 1, 
					pivot = toSort[high], i = low - 1; 
					
					result.setText(result.getText() + "Pivot is " + pivot + 
					" and the index it should be placed is currently " 
					+ (i + 1) + "<br/><br/>");
			        
			        for(int j = low; j < high; j++)
			        {
			        	result.setText(result.getText() + toSort[j] + " &lt; "
			        	+ pivot + "? : ");
			            if(toSort[j] < pivot) 
			            {
			                i++; 
			                result.setText(result.getText() + "True, pivot "
			                + "should now be placed at index " + (i + 1) 
			                + ", swap occurs at index " + i + 
			                " and index " + j + ".");
			                int temp = toSort[i];
			                toSort[i] = toSort[j];
			                toSort[j] = temp;
			            }
			            else
			            {
			            	result.setText(result.getText() + "False, pivot "
			            	+ "remains at same index as before and no "
			            	+ "swaps are needed.");
			            }
			            result.setText(result.getText() + " -> " + 
			    		java.util.Arrays.toString(toSort) + "<br/>");
			        }

			        result.setText(result.getText() + "Placing pivot in "
			        + "correct location by swapping it with "
			        + "the element at index " + (i + 1) + "...<br/><br/>");
			        // Swap the pivot element to the correct position
			        int temp = toSort[i + 1];
			        toSort[i + 1] = toSort[high];
			        toSort[high] = temp;
			        
			        result.setText(result.getText() + "<html>Final Array: " + 
					java.util.Arrays.toString(toSort) + "<br/><br/>"
					+ "After partitioning, the pivot is at the correct "
					+ "spot, all the elements to the left of the pivot "
					+ "are smaller than the pivot, and all the elements "
					+ "to the right of the pivot are bigger than it! "
					+ " The algorithm will recursively do quicksort"
					+ " on the left and right subarrays until the"
					+ " entire array is sorted.");
				});
				
				performQuickSort.start();
				
				try
				{
					performQuickSort.join();
				}
				catch(InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		
		/*
		 * Use to display the text prompt to let the user 
		 * know how to use the program properly
		 */
		@SuppressWarnings("unused")
		TextPrompt inputTextPrompt = new TextPrompt("<html>Input an array of "
		+ "integers here. For the search algorithms, the last element placed "
		+ "in is the element that will be searched for. <br/>Merge sort shows "
		+ "a demonstration of the merging mechanism ONLY. Quick sort will"
		+ " show a demonstration of the splitting mechanism ONLY. "
		+ "Separate numbers with , and a space", input);
		
		//Create the panel
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(input);
		//Add all the buttons
		panel.add(linearSearch);
		panel.add(binarySearch);
		panel.add(bubbleSort);
		panel.add(selectionSort);
		panel.add(insertionSort);
		panel.add(mergeSort);
		panel.add(quickSort);
		
		/*
		 * Due to the length of some sorting algorithms, 
		 * we need a scrolling pane so that we can 
		 * effectivelly log all the passes in these algorithms
		 */
		JScrollPane scrollPane = new JScrollPane(result);
        panel.add(scrollPane, BorderLayout.CENTER);
		
        //Other aspects of the frame
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Sorting Visualizer");
		frame.pack();
		frame.setVisible(true);
	}
	
	//Run the application
	public static void main(String[] args)
	{
		new Driver();
	}
}
