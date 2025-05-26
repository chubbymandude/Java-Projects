package game;

/*
 * This Enum is used for quick access of Buildings in Array
 */
public enum Buildings 
{
	CURSOR, GRANDMA, FACTORY, MINE, SHIPMENT, 
	ALCHEMY_LAB, PORTAL, TIME_MACHINE;
	
	/*
	 * Obtain the index of the Building (Enums naturally
	 * act as an array of constants)
	 */
	public static int getIndex(String building)
	{
		return switch(building)
		{
			case "Cursor" -> Buildings.CURSOR.ordinal();
			case "Grandma" -> Buildings.GRANDMA.ordinal();
			case "Factory" -> Buildings.FACTORY.ordinal();
			case "Mine" -> Buildings.MINE.ordinal();
			case "Shipment" -> Buildings.SHIPMENT.ordinal();
			case "Alchemy_Lab" -> Buildings.ALCHEMY_LAB.ordinal();
			case "Portal" -> Buildings.PORTAL.ordinal();
			default -> Buildings.TIME_MACHINE.ordinal();
		};
	}
}
