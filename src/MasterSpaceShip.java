import java.awt.Point;

public class MasterSpaceShip extends SpaceShip
{
	private OperationalMode operationalMode = OperationalMode.defensive;
	
	public MasterSpaceShip(String name, Point position)
	{
		super(name, position);
	}
}
