package game;

import javax.swing.ImageIcon;

public class AlchemyLab extends Building 
{
	private static final long serialVersionUID = 1L;

	public AlchemyLab()
	{
		super(new ImageIcon("alchemylab.png"), "Alchemy Lab", 
		"Turns gold into cookies!", 0, 100, 50000, 0.8);
	}
}
