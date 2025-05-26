package game;

import javax.swing.ImageIcon;

public class Shipment extends Building
{
	private static final long serialVersionUID = 1L;
	
	public Shipment()
	{
		super(new ImageIcon("shipment.png"), "Shipment", 
		"Brings in fresh cookies from the cookie planet.", 0, 20, 7000, 0.6);
	}
}
