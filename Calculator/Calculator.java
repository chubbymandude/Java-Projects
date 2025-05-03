package calculator;

import java.util.Stack;

/*
 * This calculator program can be used to calculate
 * simple mathemtical expressions that involve
 * numerical values, paranthetical statements, and the operators 
 * +, -, *, /, ^, and %
 * 
 * The program works through the evaluate method, which takes
 * in any amount of parameters. These parameters represent
 * individual components of a mathematical expression. 
 * The expression given is first converted to postfix notation
 * (see the method infixToPostfix), and is then
 * evaluated via Stack properties. 
 */
public class Calculator 
{
	/*
	 * Performs evaluation by converting an expression
	 * to an array and then evaluating it using the 
	 * evaluate method below this one
	 * 
	 * @param expression
	 * --> There must be a space between all operators and numerics
	 * --> Parantheses can be implemented as normal, will
	 * have a space artifically created for them
	 * --> PI and e must be multipied with *, cannot 
	 * simply put them next to a number
	 */
	public static double evaluate(String expression)
	{
		//Add a space after each opening parantheses
		expression = expression.replace("(", "( ");
		//Add a space before each closing parantheses
		expression = expression.replace(")", " )");
		//Convert expression to array and evaluate
		return evaluate(expression.split(" "));
	}
	
	/*
	 * Alternate version of the evaluate method
	 * that can be used if the user wants to
	 * round the values obtained to an integer whole number
	 * 
	 * @param roundUp
	 * --> if true, end result is rounded up
	 * --> if false, end result is rounded down
	 */
	public static int evaluate(boolean roundUp, String expression)
	{
		return roundUp ? (int) Math.ceil(evaluate(expression)) : 
		(int) Math.floor(evaluate(expression));
	}
	
	/*
	 * Performs evaluation of expression using postfix notation
	 * 
	 * @param expression
	 * --> Allows any # of parameters
	 * --> Parameters must be either numerical values, operators, or parentheses
	 * 
	 * Notes
	 * --> PI and e must be represented by the symbol π and the symbol e
	 * --> No scientific notation
	 */
	private static double evaluate(String... expression)
	{
		System.out.println(expression[0]);
		//Convert expression (can lead to an exception)
		try
		{
			expression = infixToPostfix(expression);
		}
		catch(Exception e) //For invalid expressions
		{
			throw new IllegalArgumentException
			("The expression you put is was not able to be converted "
			+ "to a valid postfix expression, so the evaluation cannot occur.");
		}
		//Stack to hold the integers in the array
    	Stack<Double> values = new Stack<>();
    	/*
    	 * currIndex --> Holds the index of the array being processed 
    	 * value --> Should end up with the final value evaluated
    	 */
    	int currIndex = 0;
    	double result;
    	/*
    	 * Loop to push elements and evaluate expressions
    	 */
    	do
    	{
    		if(!expression[currIndex].matches("[0-9+\\-*/^%.πe]+"))
    	    {
    	    	throw new IllegalArgumentException("The expression you put in"
    	    	+ " is not valid! Please try again with a valid expression!");
    	    }
    		//Pop two elements and evaluate if an operator, push one if integer
			switch(expression[currIndex])
			{
				/*
				 * Note: In postfix the items are evaluated
				 * in reverse order of their pops
				 */
				case "+": //Addition
					double second1 = values.pop();
					values.push(values.pop() + second1);
					break;
				case "-": //Subtraction
					double second2 = values.pop();
					values.push(values.pop() - second2);
					break;
				case "*": //Multiplication
					double second3 = values.pop();
					values.push(values.pop() * second3);
					break;
				case "/": //Division
					double second4 = values.pop();
					values.push(values.pop() / second4);
					break;
				case "^": //Exponentiation
					double second5 = values.pop();
					values.push(Math.pow(values.pop(), second5));
					break;
				case "%": //Modulus
					double second6 = values.pop();
					values.push(values.pop() % second6);
					break;
				case "π": //Pi
					values.push(Math.PI);
					break;
				case "e": //Exponential constant
					values.push(Math.E);
					break;
				default: //Integer value
					values.push(Double.valueOf(expression[currIndex]));
					break;
			}
			//Update the final expression
    		result = values.peek();
    	}
    	while(++currIndex != expression.length); //Loop until no more elements 
    	return result;
	}
	
	/*
	 * Converts infix (standard look of equation) to postfix
	 * notation so that an expression can be properly computed
	 */
	private static String[] infixToPostfix(String... expression) 
	{
		//Initialize a stack for holding integers
		Stack<Character> stack = new Stack<>();
		/*
		 * Array that will have the final resulting 
		 * postfix expression as an array
		 */
		String[] postfix = new String
		[expression.length - countParentheses(expression)];
		//Index of the new array
		int postfixIndex = 0;
		//Iterate through the infix expression
		for(int currIndex = 0; currIndex < expression.length; currIndex++)
		{
			//Element being processed
			char processing = expression[currIndex].charAt(0);
			 //Pushing operators
			if(expression[currIndex].length() == 1 && isOperator(processing))
			{
				/*
				 * When stack is empty, push the operator
				 * 
				 * If the element at the top of the stack is (, 
				 * just push the operator
				 * 
				 * If operator precedence of the top element of the stack
				 * is below the operator precedence of the element
				 * being processed, then again, just push
				 * the operator
				 */
				if(stack.isEmpty() || stack.peek() == '('
				|| operatorPrecedence(stack.peek()) 
				< operatorPrecedence(processing))
				{
					stack.push(processing);
				}
				/*
				 * Add to the postfix expression
				 * when the stack is not empty
				 * and the operator precedence of the top element is 
				 * greater or equal to the operator precedence
				 * of the element being processed
				 */
				else
				{
					postfix[postfixIndex++] = "" + stack.pop();
					stack.push(processing);
				}
			}
			//Push opening parantheses when encountered
			else if(processing == '(')
			{
				stack.push(processing);
			}
			/*
			 * When closing parantheses are encountered and stack is not empty, 
			 * pop from the stack until an opening parantheses
			 * is encountered and add those elements to the expression
			 * Then pop the opening parantheses
			 */
			else if(!stack.isEmpty() && processing == ')')
			{
				while(stack.peek() != '(')
				{
					postfix[postfixIndex++] = "" + stack.pop();
				}
				stack.pop();
			}
			else //Output any operands when encountered
			{
				postfix[postfixIndex++] = expression[currIndex];
			}
		}
		//Pop any remaining elements in the stack
		while(!stack.isEmpty())
		{
			postfix[postfixIndex++] = "" + stack.pop();
		}
		return postfix;
    }
	
	//Determines if the String to be processed represents an operator in Java
	private static boolean isOperator(char character)
	{
		return character == '+' || character == '-' || character == '*' 
		|| character == '/' || character == '^' || character == '%';
	}
	
	//Determines operator precedence (^ highest, * & / next, lastly + & -)
	private static int operatorPrecedence(char character)
	{
		return character == '^' ? 3 : 
		(character == '*' || character == '/' || character == '%' ? 2 : 1);
	}
	
	//Counts # of parantheses in an expression 
	private static int countParentheses(String... expression)
	{
		int numParentheses = 0;
		for(int currIndex = 0; currIndex < expression.length; currIndex++)
		{
			if(expression[currIndex].charAt(0) == '(' 
			|| expression[currIndex].charAt(0) == ')')
			{
				numParentheses++;
			}
		}
		return numParentheses;
	}
}
