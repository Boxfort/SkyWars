package Ships;
import java.awt.Point;

public abstract class SpaceShip
{
	private String spriteSrc;

	protected String pilotName;
	protected Point  position;
	
	/**
	 * @param position The position on the grid where the spaceship is located.
	 * @param pilotName Name of the pilot of the ship.
	 * @param src Filepath to the sprite used for this spaceship.
	 */
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
	
	public void setSpriteSrc(String src)
	{
		this.spriteSrc = src;
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
