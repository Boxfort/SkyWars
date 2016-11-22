package Ships;
import java.awt.Point;

public class BattleStar extends SpaceShip
{
	public BattleStar(Point position, String pilotName)
	{
		super(position, pilotName,"src/sprites/BattleStar.png");
	}
	
	public String toString()
	{
		return "Battle Star";
	}
}
