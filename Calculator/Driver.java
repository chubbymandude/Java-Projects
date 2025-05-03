package calculator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Driver implements ActionListener
{
	private JFrame frame; //Creates the frame
	private JLabel result; //Outputs the result of expression
	private JPanel panel; //Creates the panel
	JTextField expressionInput; //Text field for inputting expressions
	String expression = "";
	
	//Perform the action of the "Evaluate Expression" button
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		expression = expressionInput.getText();
		result.setText(expression + " = " + Calculator.evaluate(expression));
	}
	
	//Construct the GUI
	public Driver()
	{
		frame = new JFrame(); //Instantiate frame
		
		expressionInput = new JTextField(10); //Instantiate text field
		//Button to evaluate expressions
		JButton button = new JButton("Evaluate"); 
		//Action listener calls the evaluate function of the Calculator
		button.addActionListener(this);
		
		//Set text of label
		result = new JLabel(expressionInput.getText());
		
		//Create the panel
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(0, 1));
		//Add the visual components
		panel.add(expressionInput);
		panel.add(button);
		panel.add(result);
		
		//Other GUI features
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Calculator");
		frame.pack();
		frame.setVisible(true);
	}
	
	//Run the application
	public static void main(String[] args)
	{
		new Driver();
	}
}
