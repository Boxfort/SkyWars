package Commands;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import SW.Direction;
import Ships.BattleCruiser;
import Ships.BattleShooter;
import Ships.BattleStar;
import Ships.SpaceShip;

public class CreateEnemyCommand implements ICommand
{
	private ArrayList<SpaceShip> shipsList; 
	private SpaceShip enemy;
	private Random rand = new Random();
	
	public CreateEnemyCommand(ArrayList<SpaceShip> shipsList, String pilotName)
	{
		this.shipsList = shipsList; 

		int num = rand.nextInt((3-1)+ 1) + 1;
		
		switch(num)
		{
			case 1:
				enemy = new BattleStar(new Point(0,0), pilotName);
				break;
			case 2:
				enemy = new BattleCruiser(new Point(0,0), pilotName);
				break;
			case 3:
				enemy = new BattleShooter(new Point(0,0), pilotName);
				break;
		}
	}
	
	public SpaceShip getShip()
	{
		return this.enemy;
	}
	
	public void Execute()
	{
		shipsList.add(enemy);
	}

	public void UnExecute()
	{
		shipsList.remove(enemy);
	}

}
