import java.awt.Point;

public class MasterSpaceShip extends SpaceShip
{
	private OperationalMode operationalMode = OperationalMode.defensive;
	
	public MasterSpaceShip(String name, Point position)
	{
		super(name, position);
	}
	
	public OperationalMode getOperationalMode()
	{
		return this.operationalMode;
	}
	
	public void setOperationalMode(OperationalMode operationalMode)
	{
		this.operationalMode = operationalMode;
	}
}

