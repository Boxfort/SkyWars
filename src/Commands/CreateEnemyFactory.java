package Commands;

import java.util.ArrayList;
import java.util.Random;

import Ships.SpaceShip;

public class CreateEnemyFactory
{
	public CreateEnemyFactory(){}
	
	public CreateEnemyCommand generateEnemy(ArrayList<SpaceShip> shiplist)
	{
		CreateEnemyCommand cec = new CreateEnemyCommand(shiplist, getRandomName());
		return cec;
	}
	
	private String getRandomName()
	{
		Random rand = new Random();
		int x = rand.nextInt(Names.class.getEnumConstants().length);
		return Names.class.getEnumConstants()[x].toString();
	}
}

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