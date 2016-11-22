package SW;

import java.util.ArrayList;

import Ships.SpaceShip;

public class AggressiveMode implements OperationalMode
{

	@Override
	public boolean combat(ArrayList<SpaceShip> enemies)
	{
		if(enemies.size() < 3)
			return true;
		
		return false;
	}

}
