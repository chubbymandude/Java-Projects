package manager;

import javax.swing.*;

import manager.ClassManager.Task;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.PriorityQueue;

public class Driver 
{
	private ClassManager manager;
	/*
	 * Following is where data for the manager above
	 * is sent in order to save data for the next usage
	 * of the Class Manager
	 */
	private static final String classManagerFile = "manager.ser";
	private JFrame frame; //Creates the frame for the GUI
	private JPanel headerPanel; //Top panel
	private JPanel mainPanel; //Panel with the main information
	private JPanel fieldPanel; //Holds the text boxes
	private JPanel buttonPanel; //Holds the buttons
	private final JLabel instructions; //Label for the instructions
	private JLabel classList; //Label for the list of classes
	private JLabel buttonResult; //Label for the result of the button
	private JLabel tasksResult; //Label for display of tasks
	
	@SuppressWarnings("unused")
	public Driver()
	{
		//Create the frame
		frame = new JFrame();
		
		/*
		 * Following creates the header panel 
		 */
        headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		headerPanel.setLayout(new GridLayout(0, 1));
		headerPanel.setBackground(Color.gray);
		headerPanel.setPreferredSize(new Dimension(50, 250));
		
		JLabel header = new JLabel("Epic Class Manager", SwingConstants.CENTER);
		header.setFont(new Font("Comfortaa", Font.BOLD, 50));
		
		headerPanel.add(header);
		
		/*
		 * Load previously saved data into the class manager
		 */
		manager = loadData();
		
		if(manager == null) //If no data then just create a new Class Manager
		{
            manager = new ClassManager();
        } 
		
		/*
		 * Following stores a Label with the List of classes
		 * that are associated with this ClassManager
		 * so the user knows which classes to choose from
		 */
		classList = new JLabel(manager.printClasses());
		classList.setText(manager.printClasses());
		
		/*
		 * Remove any overdue tasks
		 */
		for(String course : manager.getClasses())
		{
			PriorityQueue<Task> tasks = manager.getTasks(course);
			while(!tasks.isEmpty() && 
			tasks.peek().getDate().isBefore(LocalDate.now()))
			{
				tasks.remove();
			}
		}
		
		/*
		 * Following creates a JLabel that holds the directions
		 * for how to use this ClassManager GUI
		 */
        instructions = new JLabel("Below are the text prompts "
        + "for you to insert information about your tasks. "
        + "The first time you input information for the class "
        + "it will add the class and ignore the other fields. "
        + "After the initial add of a class it will add the task "
        + "as well as the due date given. You will get an error "
        + "if you add a task without adding a due date. "
        + "You will also get an error if you input an invalid due date. "
        + "You can remove a class by solely entering the class name "
        + "without the associated task. To remove an individual task, "
        + "enter the appropriate class name as well as the task you want "
        + "to remove. Only tasks whose due date are past the present "
        + "will remain in the Class Manager after closing the program. "
        + "Click the reset button to remove everything from your class manager. "
        + "Click the GetTasks button to get the tasks of whichever class "
        + "you inputted into the class field.");
		
		/*
		 * Following creates the panel for the main area of the GUI
		 */
		mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		mainPanel.setLayout(new GridLayout(0, 1));
		mainPanel.setBackground(Color.lightGray);
		
		/*
		 * The following panel is used to store
		 * all the text fields for user input
		 */
		fieldPanel = new JPanel(new FlowLayout());
		
		JTextField addClassField = new JTextField(20);
		JTextField addTaskField = new JTextField(20);
		JTextField addDayField = new JTextField(20);
		JTextField addMonthField = new JTextField(20);
		JTextField addYearField = new JTextField(20);
		
		fieldPanel.add(addClassField);
		fieldPanel.add(addTaskField);
		fieldPanel.add(addDayField);
		fieldPanel.add(addMonthField);
		fieldPanel.add(addYearField);
		
		//Prompts for each of the text fields
		TextPrompt classFieldTextPrompt = new TextPrompt
		("<html>Insert class code here.</html>", addClassField);
		TextPrompt taskFieldTextPrompt = new TextPrompt
		("<html>Insert task here.</html>", addTaskField);
		TextPrompt dayFieldTextPrompt = new TextPrompt
		("<html>Insert day here (1-31).</html>", addDayField);
		TextPrompt monthFieldTextPrompt = new TextPrompt
		("<html>Insert month here (1-12).</html>", addMonthField);
		TextPrompt yearFieldTextPrompt = new TextPrompt
		("<html>Insert year here (ex. 2025).</html>", addYearField);
		
		/*
         * Following is a label that is updated whenever
         * one of the buttons is clicked
         */
		buttonResult = new JLabel();
		
		/*
		 * Following panel stores both the add and remove buttons
		 * of this GUI
		 */
		buttonPanel = new JPanel(new FlowLayout());
		
		/*
		 * The following creates the button that is used to 
	T	 * add classes and tasks to the ClassManager
		 */
        JButton addClassButton = new JButton("Add Class/Task");
        addClassButton.setBorderPainted(false);
        addClassButton.setOpaque(true);
        addClassButton.setBackground(Color.GREEN);
        addClassButton.setSize(new Dimension(100, 100));
        addClassButton.addActionListener(e -> 
        {
        	/*
        	 * Retrieve all data from text fields
        	 */
        	String classText = addClassField.getText();
        	String taskText = addTaskField.getText();
	        String dayText = addDayField.getText();
	        String monthText = addMonthField.getText();
	        String yearText = addYearField.getText();
	        //If an error occurs while capturing the date, output an error
        	try
        	{
        		//Add the class if it doesn't exist
	        	if(!manager.containsClass(classText))
	        	{
	        		manager.addClass(classText);
	        		buttonResult.setText(classText
	        		+ " has been added to your Class Manager!");
	        	}
	        	//Add task associated with class if the due date is given
	        	else if(!dayText.isEmpty() && !monthText.isEmpty() 
	        	&& !yearText.isEmpty())
	        	{
	        		manager.addTask(classText, taskText, 
	        		Integer.valueOf(dayText), Integer.valueOf(monthText), 
	        		Integer.valueOf(yearText));
	        		buttonResult.setText("You have added the following task to " 
	        		+ classText + ": " + taskText);
	        	}
	        	else //If an error occurred while trying to modify the Manager
	        	{
	        		buttonResult.setText("Your input was invalid. "
	        		+ "Please reread the directions and try again.");
	        	}
        	}
        	catch(Exception exp)
        	{
        		buttonResult.setText("The values you put for the due date"
        		+ " are invalid. Please try again.");
        	}
        	//Update the class List
        	classList.setText(manager.printClasses());
        	//Reset the text fields
	        addClassField.setText("");
	        addTaskField.setText("");
	        addDayField.setText("");
	        addMonthField.setText("");
	        addYearField.setText("");
        });
        
        /*
         * The following creates the button used to remove classes/tasks
         */
        JButton removeClassButton = new JButton("Remove Class/Task");
        removeClassButton.setBorderPainted(false);
        removeClassButton.setOpaque(true);
        removeClassButton.setBackground(Color.RED);
        removeClassButton.setSize(new Dimension(100, 100));
        removeClassButton.addActionListener(e -> 
        {
        	//Retrieve data from text fields
        	String classText = addClassField.getText();
        	String taskText = addTaskField.getText();
        	//Make sure you are removing from a class that exists
        	if(manager.containsClass(classText))
        	{
        		//Remove associated task from class
        		if(manager.containsTask
        		(classText, taskText))
        		{
        			manager.removeTask(classText, taskText);
        			buttonResult.setText("The following task was removed from " 
        			+ classText + ": " + taskText);
        		}
        		//If task is not provided, the class itself gets removed
        		else if(taskText.isEmpty())
        		{
        			manager.removeClass(classText);
        			buttonResult.setText(classText
        			+ " has been removed from the Class Manager.");
        		}
        		//Any error that occurs will result in the below
        		else
        		{
        			buttonResult.setText("The task you tried to remove does "
        			+ " not exist for the class " + classText + ".");
        		}
        	}
        	else
        	{
        		buttonResult.setText("The class you tried to "
        		+ "remove does not exist in the Manager");
        	}
        	//Update the class List
        	classList.setText(manager.printClasses());
        	//Reset text fields
        	addClassField.setText("");
        	addTaskField.setText("");
        	addDayField.setText("");
        	addMonthField.setText("");
        	addYearField.setText("");
        });
        
        /*
         * The following creates a button for resetting the Class Manager
         */
        JButton resetButton = new JButton("Reset Manager");
        resetButton.setBorderPainted(false);
        resetButton.setOpaque(true);
        resetButton.setBackground(Color.BLACK);
        resetButton.setForeground(Color.WHITE);
        resetButton.setSize(new Dimension(100, 100));
        resetButton.addActionListener(e ->
        {
        	//Reset the manager
        	manager = new ClassManager();
        	buttonResult.setText("Class Manager has been reset.");
        	//Update the class List
        	classList.setText(manager.printClasses());
        	//Reset text fields
        	addClassField.setText("");
        	addTaskField.setText("");
        	addDayField.setText("");
        	addMonthField.setText("");
        	addYearField.setText("");
        });
        
        //Label for the display of Tasks associated with a class
        tasksResult = new JLabel();
        
        //Create a scroll pane for the tasks listed
        JScrollPane tasksScrollPane = new JScrollPane(tasksResult);
        tasksScrollPane.setVisible(false);
        tasksScrollPane.setSize(new Dimension(100, 1000));
        
        /*
         * The following creates a button for retrieving tasks
         */
        JButton getTasksButton = new JButton("Get Tasks");
        getTasksButton.setBorderPainted(false);
        getTasksButton.setOpaque(true);
        getTasksButton.setBackground(Color.ORANGE);
        getTasksButton.setSize(new Dimension(100, 100));
        getTasksButton.addActionListener(e ->
        {
        	String classText = addClassField.getText();
        	//Load the tasks to the button result label
        	if(manager.containsClass(classText))
        	{
        		tasksResult.setText(manager.printTasks(classText));
        		buttonResult.setText("");
        		tasksScrollPane.setVisible(true);
        	}
        	else
        	{
        		buttonResult.setText("The class you tried to get tasks from "
        		+ "does not exist in your Manager. Please try again.");
        	}
        	//Reset text fields
        	addClassField.setText("");
        	addTaskField.setText("");
        	addDayField.setText("");
        	addMonthField.setText("");
        	addYearField.setText("");
        });
        
        //Add buttons to the button panel
        buttonPanel.add(addClassButton);
        buttonPanel.add(removeClassButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(getTasksButton);
        
        //Create a scroll pane so the instructions can be read
        JScrollPane instructionsScrollPane = new JScrollPane(instructions);
        
        mainPanel.add(instructionsScrollPane, BorderLayout.CENTER);
		mainPanel.add(fieldPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(classList);
        mainPanel.add(buttonResult);
        mainPanel.add(tasksScrollPane, BorderLayout.CENTER);
        
		
        //Other GUI components
        frame.add(headerPanel, BorderLayout.NORTH);
		frame.add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Class Manager");
		frame.pack();
		
		/*
		 * Saves the contents of the ClassManager
		 * upon closing the program
		 */
		frame.addWindowListener(new WindowAdapter() 
		{
            @Override
            public void windowClosing(WindowEvent e) 
            {
                saveData(manager); 
                frame.dispose(); // Close window
                System.out.println(manager);
            }
        });
		
		frame.setVisible(true);
	}
	
	//Run the program
	public static void main(String[] args)
	{
		new Driver();
	}
	
	/*
	 * The following is used to save the contents of the
	 * ClassManager so that when the user reruns the program 
	 * they can still have their ClassManager ready to be used
	 */
	private static void saveData(ClassManager manager) 
	{
        try (ObjectOutputStream oos = new 
        ObjectOutputStream(new FileOutputStream(classManagerFile))) 
        {
            oos.writeObject(manager);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

	/*
	 * The following is used to reload the content of 
	 * the ClassManager from the previous run of the program
	 * to the current run of the program
	 */
    private static ClassManager loadData() 
    {
        try (ObjectInputStream ois = new
        ObjectInputStream(new FileInputStream(classManagerFile))) 
        {
            return (ClassManager) ois.readObject();
        } 
        catch(Exception e) 
        {
            return null; 
        }
    }
}
