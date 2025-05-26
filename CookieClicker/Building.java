package game;

import java.io.Serializable;

import javax.swing.ImageIcon;

/*
 * The following is a class that represents a Building in Cookie Clicker
 * 
 * It is the base class for all Buildings in the game
 */
public abstract class Building implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private transient final ImageIcon icon; //The icon of the building
	private final String type; //Type of building 
	private final String description; //Description of building
	private int numOwned; //# of buildings owned by player
	private double cookiesPerSecond; //Cookies per second by building
	private long cost; //What is the cost of the building
	private double grandmaBoost; //What boost does this building give to Grandma
	
	/*
	 * Initialize all fields
	 */
	public Building(ImageIcon icon, String type, String description, 
	int numOwned, double cookiesPerSecond, long cost, double grandmaBoost)
	{
		this.icon = icon;
		this.type = type;
		this.description = description;
		this.numOwned = numOwned;
		this.cookiesPerSecond = cookiesPerSecond; //Initial production
		this.cost = cost; //Initial cost
		this.grandmaBoost = grandmaBoost;
	}

	/*
	 * Gets the ImageIcon for the building
	 */
	public ImageIcon getIcon() 
	{
		return icon;
	}

	/*
	 * Gets the Building Type
	 */
	public String getType() 
	{
		return type;
	}
	
	/*
	 * Returns this building's description
	 */
	public String getDescription() 
	{
		return description;
	}

	/*
	 * Returns the number of this building owned
	 */
	public int getNumOwned() 
	{
		return numOwned;
	}
	
	//Updates the number of the building owned for each purchase of the building
	public void updateNumOwned()
	{
		numOwned++;
	}

	/*
	 * Returns the cookie production of this building
	 */
	public double getCookieProduction() 
	{
		return cookiesPerSecond;
	}
	
	/*
	 * Gets the total production of cookies of all buildings of this type owned
	 */
	public double getTotalCookieProduction()
	{
		return numOwned * cookiesPerSecond;
	}

	/*
	 * Returns the cost of this building
	 */
	public double getCost() 
	{
		return cost;
	}
	
	/*
	 * Updates the cost of the building after each purchase
	 * 
	 * The formula used is Price = Base * 1.1^M, where
	 * M is the number of buildings of this type owned by the player
	 */
	public void updateCost()
	{
		cost = (long) (cost * Math.pow(1.1, numOwned));
	}
	
	/*
	 * Returns the boost in CPS this building gives to the Grandma building
	 */
	public double getGrandmaBoost()
	{
		return grandmaBoost;
	}
	
	//Determines if the building can be bought with the # of cookies given
	public boolean canBuy(double cookies)
	{
		return cookies >= cost;
	}
	
	//Returns String representation of this Building
	public String toString()
	{
		return "<html>" + type + " - " + " Costs " 
		+ cost + " Cookies, " + numOwned + " Owned<br>" + description;
	}
}
