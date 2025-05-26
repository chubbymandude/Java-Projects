package game;

import javax.swing.ImageIcon;

public class Portal extends Building 
{
	private static final long serialVersionUID = 1L;

	public Portal()
	{
		super(new ImageIcon("portal.png"), "Portal", 
		"Opens a door to the cookieverse.", 0, 1333.2, 1000000, 1.0);
	}
}
