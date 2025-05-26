package game;

import java.io.Serializable;

public class Cookie implements Serializable
{
	private static final long serialVersionUID = 1L; 
	
	private double cookies; //Tracks cookies in bank
	public Building[] buildings; //Array of the buildings in the game
	private double cookiesPerSecond; //Tracks cookies per second
	
	/*
	 * Constructs the Cookie object
	 */
	public Cookie()
	{
		cookies = 0;
		buildings = new Building[8]; //There are 8 buildings in the game
		//Instantiate each array slot
		buildings[0] = new Cursor();
		buildings[1] = new Grandma(buildings);
		buildings[2] = new Factory();
		buildings[3] = new Mine();
		buildings[4] = new Shipment();
		buildings[5] = new AlchemyLab();
		buildings[6] = new Portal();
		buildings[7] = new TimeMachine();
		calculateCookiesPerSecond(); //Calculate cookies per second from above
	}
	
	/*
	 * Returns the number of cookies in the bank
	 */
	public double getNumCookies()
	{
		return cookies;
	}
	
	/*
	 * Returns the number of cookies being made by the game
	 */
	public double getCookiesPerSecond()
	{
		return cookiesPerSecond;
	}
	
	/*
	 * Increments the # of cookies
	 * 
	 * @param clicker, 
	 * --> if true that means the mouse clicked the cookie
	 * --> if false increment by cookies per second
	 * 
	 * Note: Cookie production is done every 100th of a second
	 */
	public void incrementCookies(boolean clicker)
	{
		cookies = clicker ? cookies + 1 : 
	    cookies + (getCookiesPerSecond() / 100);
	}
	
	/*
	 * Calculates the total cookie production per second
	 * and assigns it to the corresponding variable
	 */
	public void calculateCookiesPerSecond()
	{
		double totalCookieProduction = 0;
		for(Building building: buildings)
		{
			totalCookieProduction += building.getTotalCookieProduction();
		}
		cookiesPerSecond = Math.round(totalCookieProduction * 100.0) / 100.0;
	}
	
	/*
	 * Performs events that occur when a building is bought in the game
	 */
	public void buyBuilding(Building building)
	{
		//Access the corresponding Building in the array
		building = buildings[Buildings.getIndex(building.getType())]; 
		//Make sure user has enough cookies
		if(building.canBuy(cookies))
		{
			cookies -= building.getCost();
			building.updateNumOwned();
			building.updateCost();
			calculateCookiesPerSecond();
			
		}
		else //Throw exception if user does not have enough cookies
		{
			throw new IllegalArgumentException
			("You do not have enough cookies to buy this building.");
		}
	}
}
