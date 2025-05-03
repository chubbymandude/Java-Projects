package rock_paper_scissors;

import java.util.Scanner;

public class Main 
{
	public static void main (String[] args)
	{
		//Instantiate the scanner
		Scanner sc = new Scanner(System.in);
		//Variable for the # of rounds the player wants to play
		int rounds;
		/*
		 * Loop that iterates until the user provides an input for 
		 * an odd number of rounds they want to play.
		 */
		do
		{
			System.out.print("Best of: ");
			rounds = sc.nextInt();
			if(rounds%2 != 1)
				System.out.println("Please input an odd number of rounds... ");
		}
		while(rounds%2 != 1);
		//Return scanner to String inputs
		sc.nextLine();
		//Declare and initialize variables that keep track of player and computer wins
		int playerWins = 0;
		int compWins = 0;
		//Declare variable that holds the round variable
		int roundsHolder = rounds;
		//Loop that iterates until the number of rounds has been reached
		do
		{
			//Let the player choose Rock, Paper, or Scissors
			String choice;
			System.out.print("Choose Rock, Paper, or Scissors (Enter the Name): ");
			choice = sc.nextLine();
			choice = choice.toLowerCase();
			//Avoid invalid input
			if(!(choice.equals("rock") || choice.equals("scissors") || choice.equals("paper")))
				System.out.println("Please input a valid choice... ");
			else
			{
				//Evaluate the computer's choice using random selection
				int compChoice = (int)(Math.random()*3);
				//Output the computer's choice
				System.out.println("Computer: " + getComputerChoice(compChoice));
				//Determine winner, decrement rounds and increment wins unless the round was a tie
				String winner = determineWinner(choice, getComputerChoice(compChoice));
				if(winner.equals(choice))
				{
					playerWins++;
					rounds--;
					System.out.println("You have won this round!");
				}
				else if(winner.equals(getComputerChoice(compChoice)))
				{
					compWins++;
					rounds--;
					System.out.println("The computer has won this round!");
				}
				else
					System.out.println("This round was a tie...");
			}
			System.out.println();
		}
		while(rounds != 0 && playerWins < roundsHolder/2 + 1 && compWins < roundsHolder/2 + 1);
		//Determine the winner by comparing player wins to computer wins
		if(playerWins > compWins)
			System.out.println("You won the game!");
		else
			System.out.println("The computer won the game!");
		//Close the scanner
		sc.close();
	}
	//Method that relies on the random method to obtain a resulting choice for the computer
	public static String getComputerChoice(int choice)
	{
		String result = "";
		switch(choice)
		{
			case 0: 
				result = "rock";
				break;
			case 1:
				result = "paper";
				break;
			case 2:
				result = "scissors";
				break;
		}
		return result;
	}
	//Method that determines the winner of the round based on the input of the user and the computer's random output
	public static String determineWinner(String p1, String p2)
	{
		String winner = "";
		if((p1.equals("rock") && p2.equals("scissors")) || 
		   (p1.equals("paper") && p2.equals("rock")) || 
		   (p1.equals("scissors") && p2.equals("paper")))
			winner = p1;
		if((p2.equals("rock") && p1.equals("scissors")) || 
		   (p2.equals("paper") && p1.equals("rock")) || 
		   (p2.equals("scissors") && p1.equals("paper")))
			winner = p2;
		return winner;
			
	}
}
