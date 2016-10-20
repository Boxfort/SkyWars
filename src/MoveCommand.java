import java.awt.Point;

public class MoveCommand implements ICommand
{
	private Point startPosition;
	private Direction moveDirection;
	private SpaceShip spaceShip;
	
	public MoveCommand(SpaceShip ship, Direction direction)
	{
		this.moveDirection = direction;
		this.spaceShip = ship;
		this.startPosition = ship.getPosition();
	}
	
	public void Execute()
	{
		
	}

	public void UnExecute()
	{
		
	}	
}
