import java.util.ArrayList;

public class DestroyEnemyCommand implements ICommand
{
	ArrayList<SpaceShip> shipsList;
	SpaceShip enemy;

	public DestroyEnemyCommand(SpaceShip enemy, ArrayList<SpaceShip> shipsList)
	{
		this.shipsList = shipsList;
		this.enemy = enemy;
	}
	
	public void Execute()
	{
		shipsList.remove(enemy);
	}

	public void UnExecute()
	{
		shipsList.add(enemy);
	}

}
