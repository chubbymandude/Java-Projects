package game;

import javax.swing.ImageIcon;

public class Cursor extends Building
{
	private static final long serialVersionUID = 1L;

	/*
	 * Constructs a Cursor constructor that extends the Building class
	 */
	public Cursor()
	{
		super(new ImageIcon("cursor.png"), "Cursor", 
		"Autoclicks every 5 seconds.", 0, 0.2, 15, 0);
	}
}
