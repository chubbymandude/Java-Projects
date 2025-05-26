package game;

import javax.swing.ImageIcon;

public class TimeMachine extends Building 
{
	private static final long serialVersionUID = 1L;

	public TimeMachine()
	{
		super(new ImageIcon("timemachine.png"), "Time Machine", 
		"Brings cookies from the past, before they were even eaten.",
		0, 123456, 123456789, 1.2);
	}
}
