package manager;

import java.util.*;
import java.io.Serializable;
import java.time.LocalDate;

/*
 * This class is used in order to create a ClassManager, 
 * which allows a user to input their classes and any
 * associated tasks of those classes + their due dates.
 * These tasks will exist until either the user manually
 * removes them or the date specified is reached
 */
public class ClassManager implements Serializable
{
	private static final long serialVersionUID = 1L;

	/*
	 * Private class is used in order
	 * to represent Tasks associated with a class
	 */
	public static class Task implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		private String taskName; //The task
		private LocalDate dueDate; //The due date of the task
		
		//The due date consists of the day, month, and year
		public Task(String taskName, int day, int month, int year)
		{
			this.taskName = taskName;
			dueDate = LocalDate.of(day, month, year);
		}
		
		//Getter method for the task
		public String getTask()
		{
			return taskName;
		}
		
		//Getter method for the date
		public LocalDate getDate()
		{
			return dueDate;
		}
		
		//Getter method for the task
		public void getTask(String taskName)
		{
			this.taskName = taskName;
		}
		
		//Getter method for the date
		public void getDate(LocalDate dueDate)
		{
			this.dueDate = dueDate;
		}
		
		//Prints out information about the task
		@Override
		public String toString()
		{
			return taskName + " (" + dueDate + ")";
		}
		
		//Determines if two tasks are the same (if their tasks are equivalent)
		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
			{
				return true;
			}
			if(this.getClass() != obj.getClass())
			{
				return false;
			}
			Task task = (Task) obj;
			return this.taskName.equals(task.taskName);
		}
	}

	//Allows comparing of tasks based on their due date
	public class TaskComparator implements Comparator<Task>, Serializable
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(Task t1, Task t2) 
		{
			return t1.dueDate.compareTo(t2.dueDate);
		}
	}
	
	/*
	 * The String key of this HashMap
	 * refers to the individual classes 
	 * 
	 * The PriorityQueue refers to
	 * specific tasks associated with the class key
	 * that have a a related due date. 
	 */
	private LinkedHashMap<String, PriorityQueue<Task>> manager;
	
	/*
	 * Constructs the manager for this class
	 */
	public ClassManager()
	{
		manager = new LinkedHashMap<String, PriorityQueue<Task>>();
	}
	
	/*
	 * Adds a new class to the class manager
	 * Note: If a class is re-added to the ClassManager, 
	 * all tasks associated with the class get wiped out.
	 * 
	 * The PriorityQueue given will remove tasks based
	 * on the whether the due date is passed or not
	 */
	public void addClass(String className)
	{
		manager.put(className, new PriorityQueue<>(new TaskComparator()));
	}
	
	/*
	 * Removes a class from the class manager
	 * Nothing happens if the class that the user
	 * tries to remove doesn't exist
	 */
	public void removeClass(String className)
	{
		manager.remove(className);
	}
	
	/*
	 * Adds a task associated with a specific class
	 */
	public void addTask(String className, String taskName, 
	int day, int month, int year)
	{
		if(manager.containsKey(className))
		{
			manager.get(className).add(new Task(taskName, year, month, day));
		}
	}
	
	/*
	 * This is used to check if the task should be removed based
	 * on the day (if the due date is passed the task should
	 * automatically be removed)
	 */
	public void removeTaskByDate(String className)
	{
		if(!manager.get(className).isEmpty() 
		&& manager.get(className).peek().dueDate.isAfter(LocalDate.now()))
		{
			manager.get(className).remove();
		}
	}
	
	/*
	 * Performs brute force removal of a task
	 * 
	 * If the task is not in the PriorityQueue nothing happens
	 */
	public void removeTask(String className, String taskName)
	{
		manager.get(className).remove(new Task(taskName, 1, 1, 1));
	}
	
	//Determines if the manager has a class
	public boolean containsClass(String className)
	{
		return manager.containsKey(className);
	}
	
	//Determines if a task exists within the specified class
	public boolean containsTask(String className, String taskName)
	{
		return manager.get(className).contains(new Task(taskName, 1, 1, 1));
	}
	
	//Obtains the Tasks associated with the class provided
	public PriorityQueue<Task> getTasks(String className)
	{
		return manager.get(className);
	}
	
	/*
	 * Gets the Set of classes associated with this Class Manager
	 */
	public Set<String> getClasses()
	{
		return manager.keySet();
	}
	
	//Prints the tasks associated with the class provided
	public String printTasks(String className)
	{
		PriorityQueue<Task> tasks = getTasks(className);
		String display = "<html>Below are the tasks for " 
		+ className + ":<br/>";
		int priority = 1;
		for(Task task : tasks)
		{
			display += priority++ + ". " + task + "<br/>";
		}
		return priority != 1 ? display : display + "None for now!";
	}
	
	/*
	 * Prints the classes associated with this ClassManager
	 */
	public String printClasses()
	{
		String display = "Your Class Manager consists of these courses: ";
		for(String course : getClasses())
		{
			display += course + ", ";
		}
		return display.equals("Your Class Manager consists of these courses: ") 
		? display + "None currently listed" : 
	    display.substring(0, display.length() - 2);
	}
	
	/*
	 * Prints out all contents of ClassManager (for debugging)
	 */
	public String toString()
	{
		String display = "";
		for(String className : manager.keySet())
		{
			display += "-------\n" + className + "\n-------\n";
			for(Task task : manager.get(className))
			{
				display += task + "\n";
			}
			if(manager.get(className).isEmpty())
			{
				display += "None for now!\n";
			}
			display += "\n";
		}
		return display;
	}
}