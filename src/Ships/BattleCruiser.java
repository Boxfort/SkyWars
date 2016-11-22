package Ships;
import java.awt.Point;

public class BattleCruiser extends SpaceShip
{
	public BattleCruiser(Point position, String pilotName)
	{
		super(position, pilotName, "src/sprites/BattleCruiser.png");
	}
	
	public String toString()
	{
		return "Battle Cruiser";
	}
}
