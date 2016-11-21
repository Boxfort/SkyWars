import java.awt.Point;

public abstract class SpaceShip
{
	private final String spriteSrc;
	
	protected Point  position;
	
	public SpaceShip(Point position, String src)
	{
		this.position = position;
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
}
