package tictactoe;

import java.util.Scanner;

public class Main 
{
	public static void main (String[] args)
	{
		//Instantiate the scanner
		Scanner sc = new Scanner(System.in);
		//Create a 2-D array for the T-T-T board and initialize all values to empty.
		String[][] board = new String[3][3];
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				board[i][j] = "_";
			}
		}
		//Ask the user to determine if X or O goes first.
		String first = "";
		int turn = 0;
		do
		{
			System.out.print("Who goes first? (X or O): ");
			first = sc.nextLine();
			if(first.equals("X"))
				turn = 0;
			else if(first.equals("O"))
				turn = 1;
			else
				System.out.println("OOPS! You put wrong input! Try again...");
		}
		while(!(first.equals("O") || first.equals("X")));
		//Play the game
		boolean play = true;
		String curr = "";
		do
		{
			//Print the board after each round
			printBoard(board);
			//Determine which player is going right now
			if(turn%2 == 0)
			{
				curr = "X";
				System.out.println();
				System.out.println("IT IS X'S TURN!");
				System.out.println();	
			}
			else
			{
				curr = "O";
				System.out.println();
				System.out.println("IT IS O'S TURN!");
				System.out.println();
			}
			//User enters input for the coordinates of where they want to place "X" or "O"
			int i;
			int j;
			boolean cont = false;
			do
			{
				System.out.print("Pick row (0-2): ");
				i = sc.nextInt();
				System.out.print("Pick column (0-2): ");
				j = sc.nextInt();
				if((i >= 0 && i <= 2) && (j >= 0 && j <= 2) && board[i][j].equals("_"))
				{
					board[i][j] = curr;
					cont = true;
					System.out.println();
				}
				else
					System.out.println("Please put in a valid input...");
			}
			while(cont == false);
			//Check if there is a winner or not based on placement from the previous round
			if(checkHorizontals(board, curr) || checkVerticals(board, curr) || checkDiagonals(board, curr))
			{
				printBoard(board);
				System.out.println();
				System.out.println("THE GAME IS OVER! " + curr + " WINS!");
				System.out.println();
				play = false;
				break;
			}
			//Move on to the next player
			turn++;
		}
		while(play == true);
		//Close the scanner
		sc.close();
	}
	//Method to print the board
	public static void printBoard(String[][] board)
	{
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	//Three methods that check if there is a win horizontally, vertically, or diagonally
	public static boolean checkHorizontals(String[][] r, String t)
	{
		if((r[0][0].equals(t) && r[0][1].equals(t) && r[0][2].equals(t)) ||
		  (r[1][0].equals(t) && r[1][1].equals(t) && r[1][2].equals(t)) ||
		  (r[1][0].equals(t) && r[1][1].equals(t) && r[1][2].equals(t)))
			return true;
		return false;
	}
	public static boolean checkVerticals(String[][] r, String t)
	{
		if((r[0][0].equals(t) && r[1][0].equals(t) && r[2][0].equals(t)) ||
		   (r[0][1].equals(t) && r[1][1].equals(t) && r[2][1].equals(t)) ||
		   (r[0][2].equals(t) && r[1][2].equals(t) && r[2][2].equals(t)))
			return true;
		return false;
	}
	public static boolean checkDiagonals(String[][] r, String t)
	{
		if((r[0][0].equals(t) && r[1][1].equals(t) && r[2][2].equals(t)) ||
		  (r[0][2].equals(t) && r[1][1].equals(t) && r[2][0].equals(t)))
			return true;
		return false;
	}
}
