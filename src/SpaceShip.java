import java.awt.Point;

public abstract class SpaceShip
{
	private String name;
	private Point  position;
	
	public SpaceShip(String name, Point position)
	{
		this.name = name;
		this.position = position;
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
}
