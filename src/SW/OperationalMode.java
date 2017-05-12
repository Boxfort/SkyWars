package SW;

import java.util.ArrayList;

import Ships.SpaceShip;

public interface OperationalMode 
{
	public boolean combat(ArrayList<SpaceShip> enemies);

}
