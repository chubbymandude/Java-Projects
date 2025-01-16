package connect4;

import java.util.Scanner;

public class Main 
{
	public static void main (String[] args)
	{
		//Instantiate the scanner
		Scanner sc = new Scanner(System.in);
		//Create a 2-D array for the C-4 board and initialize all values to empty.
		String[][] board = new String[6][7];
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
			System.out.print("Who goes first? (Y or R): ");
			first = sc.nextLine();
			if(first.equals("Y"))
				turn = 0;
			else if(first.equals("R"))
				turn = 1;
			else
				System.out.println("OOPS! You put wrong input! Try again...");
		}
		while(!(first.equals("Y") || first.equals("R")));
		//Play the game
		boolean play = true;
		String curr = "";
		do
		{
			//Print the board after every move
			printBoard(board);
			//Determine which player is making their move
			if(turn%2 == 0)
			{
				curr = "Y";
				System.out.println();
				System.out.println("IT IS YELLOW'S TURN!");
				System.out.println();
			}
			if(turn%2 == 1)
			{
				curr = "R";
				System.out.println();
				System.out.println("IT IS RED'S TURN!");
				System.out.println();
			}
			//Let player choose which column to drop their piece
			int col;
			boolean cont = false;
			do
			{
				System.out.print("Pick a column to drop a piece: ");
				col = sc.nextInt();
				if(col >= 0 && col <= 6 && board[0][col].equals("_"))
				{
					board = addPiece(board, col, curr);
					cont = true;
					System.out.println();
				}
				else
				    System.out.println("Please put in a valid input...");
			}
			while(cont == false);
			//Determine if there is a winner based on current conditions of the board
			if(checkHorizontals(board, curr) || checkVerticals(board, curr) || checkDiagonals(board, curr))
			{
				printBoard(board);
				System.out.println();
				if(curr.equals("R"))
					curr = "RED";
				else
					curr = "YELLOW";
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
	//Method to print the board after each round
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
		System.out.println();
	}
	//Method to add pieces to the board
	public static String[][] addPiece(String[][] board, int column, String color)
	{
		int row = 0;
		String curr = board[row][column];
		while(curr.equals("_") && row < 5)	
		{
			row++;
			curr = board[row][column];
		}
		if(row == 5 && curr.equals("_"))
			board[row][column] = color;
		else
			board[row-1][column] = color;
		return board;
	}
	//Method to check if the horizontals form a win
	public static boolean checkHorizontals(String[][] r, String t)
	{
		if((r[0][0].equals(t) && r[0][1].equals(t) && r[0][2].equals(t) && r[0][3].equals(t)) || 
		  (r[1][0].equals(t) && r[1][1].equals(t) && r[1][2].equals(t) && r[1][3].equals(t)) || 
		  (r[2][0].equals(t) && r[2][1].equals(t) && r[2][2].equals(t) && r[2][3].equals(t)) || 
		  (r[3][0].equals(t) && r[3][1].equals(t) && r[3][2].equals(t) && r[3][3].equals(t)) || 
		  (r[4][0].equals(t) && r[4][1].equals(t) && r[4][2].equals(t) && r[4][3].equals(t)) || 
		  (r[5][0].equals(t) && r[5][1].equals(t) && r[5][2].equals(t) && r[5][3].equals(t)) || 
		  (r[0][1].equals(t) && r[0][2].equals(t) && r[0][3].equals(t) && r[0][4].equals(t)) || 
		  (r[1][1].equals(t) && r[1][2].equals(t) && r[1][3].equals(t) && r[1][4].equals(t)) || 
		  (r[2][1].equals(t) && r[2][2].equals(t) && r[2][3].equals(t) && r[2][4].equals(t)) || 
		  (r[3][1].equals(t) && r[3][2].equals(t) && r[3][3].equals(t) && r[3][4].equals(t)) || 
		  (r[4][1].equals(t) && r[4][2].equals(t) && r[4][3].equals(t) && r[4][4].equals(t)) || 
		  (r[5][1].equals(t) && r[5][2].equals(t) && r[5][3].equals(t) && r[5][4].equals(t)) || 
		  (r[0][2].equals(t) && r[0][3].equals(t) && r[0][4].equals(t) && r[0][5].equals(t)) || 
		  (r[1][2].equals(t) && r[1][3].equals(t) && r[1][4].equals(t) && r[1][5].equals(t)) || 
		  (r[2][2].equals(t) && r[2][3].equals(t) && r[2][4].equals(t) && r[2][5].equals(t)) || 
		  (r[3][2].equals(t) && r[3][3].equals(t) && r[3][4].equals(t) && r[3][5].equals(t)) || 
		  (r[4][2].equals(t) && r[4][3].equals(t) && r[4][4].equals(t) && r[4][5].equals(t)) || 
		  (r[5][2].equals(t) && r[5][3].equals(t) && r[5][4].equals(t) && r[5][5].equals(t)) || 
		  (r[0][3].equals(t) && r[0][4].equals(t) && r[0][5].equals(t) && r[0][6].equals(t)) || 
		  (r[1][3].equals(t) && r[1][4].equals(t) && r[1][5].equals(t) && r[1][6].equals(t)) || 
		  (r[2][3].equals(t) && r[2][4].equals(t) && r[2][5].equals(t) && r[2][6].equals(t)) || 
		  (r[3][3].equals(t) && r[3][4].equals(t) && r[3][5].equals(t) && r[3][6].equals(t)) || 
		  (r[4][3].equals(t) && r[4][4].equals(t) && r[4][5].equals(t) && r[4][6].equals(t)) || 
		  (r[5][3].equals(t) && r[5][4].equals(t) && r[5][5].equals(t) && r[5][6].equals(t)))
			return true;
		return false;
	}
	//Method to check if the verticals form a win
	public static boolean checkVerticals(String[][] r, String t)
	{
		if((r[0][0].equals(t) && r[1][0].equals(t) && r[2][0].equals(t) && r[3][0].equals(t)) ||
		  (r[0][1].equals(t) && r[1][1].equals(t) && r[2][1].equals(t) && r[3][1].equals(t)) || 
		  (r[0][2].equals(t) && r[1][2].equals(t) && r[2][2].equals(t) && r[3][2].equals(t)) ||
		  (r[0][3].equals(t) && r[1][3].equals(t) && r[2][3].equals(t) && r[3][3].equals(t)) || 
		  (r[0][4].equals(t) && r[1][4].equals(t) && r[2][4].equals(t) && r[3][4].equals(t)) ||
		  (r[0][5].equals(t) && r[1][5].equals(t) && r[2][5].equals(t) && r[3][5].equals(t)) || 
		  (r[0][6].equals(t) && r[1][6].equals(t) && r[2][6].equals(t) && r[3][6].equals(t)) ||
		  (r[1][0].equals(t) && r[2][0].equals(t) && r[3][0].equals(t) && r[4][0].equals(t)) ||
		  (r[1][1].equals(t) && r[2][1].equals(t) && r[3][1].equals(t) && r[4][1].equals(t)) || 
		  (r[1][2].equals(t) && r[2][2].equals(t) && r[3][2].equals(t) && r[4][2].equals(t)) ||
		  (r[1][3].equals(t) && r[2][3].equals(t) && r[3][3].equals(t) && r[4][3].equals(t)) || 
		  (r[1][4].equals(t) && r[2][4].equals(t) && r[3][4].equals(t) && r[4][4].equals(t)) ||
		  (r[1][5].equals(t) && r[2][5].equals(t) && r[3][5].equals(t) && r[4][5].equals(t)) || 
		  (r[1][6].equals(t) && r[2][6].equals(t) && r[3][6].equals(t) && r[4][6].equals(t)) ||
		  (r[2][0].equals(t) && r[3][0].equals(t) && r[4][0].equals(t) && r[5][0].equals(t)) ||
		  (r[2][1].equals(t) && r[3][1].equals(t) && r[4][1].equals(t) && r[5][1].equals(t)) || 
		  (r[2][2].equals(t) && r[3][2].equals(t) && r[4][2].equals(t) && r[5][2].equals(t)) ||
		  (r[2][3].equals(t) && r[3][3].equals(t) && r[4][3].equals(t) && r[5][3].equals(t)) || 
		  (r[2][4].equals(t) && r[3][4].equals(t) && r[4][4].equals(t) && r[5][4].equals(t)) ||
		  (r[2][5].equals(t) && r[3][5].equals(t) && r[4][5].equals(t) && r[5][5].equals(t)) || 
		  (r[2][6].equals(t) && r[3][6].equals(t) && r[4][6].equals(t) && r[5][6].equals(t)))
			return true;
		return false;
	}
	//Method to check if the diagonals form a win
	public static boolean checkDiagonals(String[][] r, String t)
	{
		if((r[5][0].equals(t) && r[4][1].equals(t) && r[3][2].equals(t) && r[2][3].equals(t)) ||
		  (r[4][0].equals(t) && r[3][1].equals(t) && r[2][2].equals(t) && r[1][3].equals(t)) ||
		  (r[3][0].equals(t) && r[2][1].equals(t) && r[1][2].equals(t) && r[0][3].equals(t)) ||
		  (r[5][1].equals(t) && r[4][2].equals(t) && r[3][3].equals(t) && r[2][4].equals(t)) ||
		  (r[4][1].equals(t) && r[3][2].equals(t) && r[2][3].equals(t) && r[1][4].equals(t)) ||
		  (r[3][1].equals(t) && r[2][2].equals(t) && r[1][3].equals(t) && r[0][4].equals(t)) ||
		  (r[5][2].equals(t) && r[4][3].equals(t) && r[3][4].equals(t) && r[2][5].equals(t)) ||
		  (r[4][2].equals(t) && r[3][3].equals(t) && r[2][4].equals(t) && r[1][5].equals(t)) ||
		  (r[3][2].equals(t) && r[2][3].equals(t) && r[1][4].equals(t) && r[0][5].equals(t)) ||
		  (r[5][3].equals(t) && r[4][4].equals(t) && r[3][5].equals(t) && r[2][6].equals(t)) ||
		  (r[4][3].equals(t) && r[3][4].equals(t) && r[2][5].equals(t) && r[1][6].equals(t)) ||
		  (r[3][3].equals(t) && r[2][4].equals(t) && r[1][5].equals(t) && r[0][6].equals(t)) ||
		  (r[0][0].equals(t) && r[1][1].equals(t) && r[2][2].equals(t) && r[3][3].equals(t)) ||
		  (r[0][1].equals(t) && r[1][2].equals(t) && r[2][3].equals(t) && r[3][4].equals(t)) ||
		  (r[0][2].equals(t) && r[1][3].equals(t) && r[2][4].equals(t) && r[3][5].equals(t)) ||
		  (r[0][3].equals(t) && r[1][4].equals(t) && r[2][5].equals(t) && r[3][6].equals(t)) ||
		  (r[1][0].equals(t) && r[2][1].equals(t) && r[3][2].equals(t) && r[4][3].equals(t)) ||
		  (r[1][1].equals(t) && r[2][2].equals(t) && r[3][3].equals(t) && r[4][4].equals(t)) ||
		  (r[1][2].equals(t) && r[2][3].equals(t) && r[3][4].equals(t) && r[4][5].equals(t)) ||
		  (r[1][3].equals(t) && r[2][4].equals(t) && r[3][5].equals(t) && r[4][6].equals(t)) ||
		  (r[2][0].equals(t) && r[3][1].equals(t) && r[4][2].equals(t) && r[5][3].equals(t)) ||
		  (r[2][1].equals(t) && r[3][2].equals(t) && r[4][3].equals(t) && r[5][4].equals(t)) ||
		  (r[2][2].equals(t) && r[3][3].equals(t) && r[4][4].equals(t) && r[5][5].equals(t)) ||
		  (r[2][3].equals(t) && r[3][4].equals(t) && r[4][5].equals(t) && r[5][6].equals(t)))
			return true;
		return false;
	}
}
