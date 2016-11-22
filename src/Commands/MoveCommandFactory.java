package Commands;
import java.awt.Point;
import java.util.Random;

import SW.Direction;
import Ships.SpaceShip;

public class MoveCommandFactory
{
	private Random rand;
	
	public MoveCommandFactory()
	{
		rand = new Random();
	}
	
	public MoveCommand GenerateMove(SpaceShip ship)
	{
		Direction d;
		
		do
		{
			d = generateRandomDirection();
		}
		while(!isValidDirection(ship, d));
		
		MoveCommand moveCommand = new MoveCommand(ship, d);
		return moveCommand;
	}
	
	private Direction generateRandomDirection()
	{
		int x = rand.nextInt(Direction.class.getEnumConstants().length);
		return Direction.class.getEnumConstants()[x];
	}
	
	private boolean isValidDirection(SpaceShip ship, Direction direction)
	{
		Point endPosition = new Point((int)(ship.getPosition().getX() + direction.x), (int)(ship.getPosition().getY() + direction.y));
		
		if(endPosition.x < 0 || endPosition.x > 3 || endPosition.y < 0 || endPosition.y > 3)
		{
			return false;
		}
		
		return true;
	}
}
