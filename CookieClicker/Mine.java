package game;

import javax.swing.ImageIcon;

public class Mine extends Building 
{
	private static final long serialVersionUID = 1L;

	public Mine()
	{
		super(new ImageIcon("mine.png"), "Mine", 
		"Mines out cookie dough and chocolate chips.", 0, 10, 2000, 0.4);
	}
}
