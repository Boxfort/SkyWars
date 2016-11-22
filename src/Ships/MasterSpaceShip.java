package Ships;
import java.awt.Point;

import SW.OperationalMode;

public class MasterSpaceShip extends SpaceShip
{	
	private OperationalMode operationalMode = OperationalMode.offensive;

	public MasterSpaceShip(Point position, String pilotName)
	{
		super(position, pilotName,"src/sprites/MasterShip.png");
	}
	
	public OperationalMode getOperationalMode()
	{
		return this.operationalMode;
	}
	
	public void setOperationalMode(OperationalMode operationalMode)
	{
		this.operationalMode = operationalMode;
	}
	
	public String toString()
	{
		return "Master ship";
	}
}

