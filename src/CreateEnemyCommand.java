import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class CreateEnemyCommand implements ICommand
{
	private ArrayList<SpaceShip> shipsList; 
	private SpaceShip enemy;
	
	public CreateEnemyCommand(ArrayList<SpaceShip> shipsList)
	{
		this.shipsList = shipsList; 
		
		Random rand = new Random();
		
		int num = rand.nextInt((3-1)+ 1) + 1;
		
		switch(num)
		{
			case 1:
				enemy = new BattleStar(new Point(0,0));
				break;
			case 2:
				enemy = new BattleCruiser(new Point(0,0));
				break;
			case 3:
				enemy = new BattleShooter(new Point(0,0));
				break;
		}
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
