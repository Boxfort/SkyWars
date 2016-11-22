package SW;

import java.util.ArrayList;

import Ships.SpaceShip;

public class DefensiveMode implements OperationalMode
{

	@Override
	public boolean combat(ArrayList<SpaceShip> enemies)
	{
		if(enemies.size() < 2)
			return true;
		
		return false;
	}

}
