package Commands;



import java.util.ArrayList;
import java.util.Random;

import Ships.SpaceShip;

public class CreateEnemyFactory
{
	public CreateEnemyFactory(){}
	
	/**
	 * Generates a CreateEnemyCommand with a random pilotName
	 * @param shiplist The list which contains all enemies.
	 * @return Returns the command to create an enemy
	 */
	public CreateEnemyCommand generateEnemy(ArrayList<SpaceShip> shiplist)
	{
		CreateEnemyCommand cec = new CreateEnemyCommand(shiplist, getRandomName());
		return cec;
	}
	
	/**
	 * Retrieves a random name from the Names enumeration
	 */
	private String getRandomName()
	{
		Random rand = new Random();
		int x = rand.nextInt(Names.class.getEnumConstants().length);
		return Names.class.getEnumConstants()[x].toString();
	}
}

/**
 * List of names for enemy pilots
 */
enum Names
{
	Kmuyd,
	Ivolp,
	Traud,
	Knujart,
	Qlepheo,
	Ugnakxea,
	Ouddisp,
	Oethygu,
	Crerk,
	Xeivolz,
	Britrit,
	Troeamt,
	Flithihua,
	Vaeffua,
	Qliehlimn,
	Iekkolz,
	Stuozrsta,
	Povruts,
	Dropird,
	Pemisk,
	Steallaw,
	Fubhu,
	Zukapa,
	Slepsrqae,
	Wruyclub,
	Vaechylsta
}