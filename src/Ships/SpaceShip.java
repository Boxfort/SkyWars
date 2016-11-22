package Ships;
import java.awt.Point;

public abstract class SpaceShip
{
	private final String spriteSrc;

	protected String pilotName;
	protected Point  position;
	
	public SpaceShip(Point position, String pilotName, String src)
	{
		this.position = position;
		this.pilotName = pilotName;
		this.spriteSrc = src;
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public void setPosition(Point position)
	{
		this.position = position;
	}
	
	public String getSpriteSrc()
	{
		return this.spriteSrc;
	}
	
	public String getPilotName()
	{
		return this.pilotName;
	}
	
	public void setPilotName(String pilotName)
	{
		this.pilotName = pilotName;
	}
}
