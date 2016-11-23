package Commands;



import SW.SkyWarsCore;
import Ships.SpaceShip;

public class PlayerDefeatCommand implements ICommand
{
	private SkyWarsCore game;
	
	
	public PlayerDefeatCommand(SkyWarsCore game)
	{
		this.game = game;
	}

	public void Execute()
	{
		game.onDefeat();
	}

	public void UnExecute()
	{
	}
}
