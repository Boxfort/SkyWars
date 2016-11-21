import java.awt.Point;

public class MasterSpaceShip extends SpaceShip
{	
	private OperationalMode operationalMode = OperationalMode.offensive;

	public MasterSpaceShip(Point position)
	{
		super(position, "src/sprites/MasterShip.png");
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

