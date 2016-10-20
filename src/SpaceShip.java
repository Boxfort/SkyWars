import java.awt.Point;

public abstract class SpaceShip
{
	private final String spriteSrc;
	
	private String name;
	private Point  position;
	
	public SpaceShip(String name, Point position, String src)
	{
		this.name = name;
		this.position = position;
		this.spriteSrc = src;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
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
