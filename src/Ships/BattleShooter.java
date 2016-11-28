package Ships;
import java.awt.Point;

public class BattleShooter extends SpaceShip
{
	public BattleShooter(Point position, String pilotName)
	{
		super(position, pilotName, "/sprites/BattleShooter.png");
	}
	
	public String toString()
	{
		return "Battle Shooter";
	}
}
