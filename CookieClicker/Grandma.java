package game;

import javax.swing.ImageIcon;

public class Grandma extends Building
{
	private static final long serialVersionUID = 1L;
	
	private Building[] boosters; //Used to boost CPS of Grandma 
	
	/*
	 * Constructs a Cursor constructor that extends the Building class
	 */
	public Grandma(Building... boosters)
	{
		super(new ImageIcon("grandma.png"), "Grandma", 
		"A nice grandma to bake more cookies.", 0, 0.8, 100, 0);
		this.boosters = boosters;
	}
	
	/*
	 * An override of the getCookieProduction() method
	 * that takes into account the boosts in CPS
	 * given to the Grandma by other buildings
	 */
	@Override
	public double getCookieProduction()
	{
		double cps = super.getCookieProduction();
		for(Building building : boosters)
		{
			cps += building.getGrandmaBoost();
		}
		return cps;
	}
}
