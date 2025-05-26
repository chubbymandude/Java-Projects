package game;

import javax.swing.ImageIcon;

public class Factory extends Building 
{
	private static final long serialVersionUID = 1L;

	public Factory()
	{
		super(new ImageIcon("factory.png"), "Factory", 
		"Produces large quantities of cookies.", 0, 4, 500, 0.2);
	}
}
