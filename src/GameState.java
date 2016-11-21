import java.util.ArrayList;

public class GameState
{
	private ArrayList<ICommand> commands = new ArrayList<ICommand>();
	
	public GameState() { }
	
	public void addCommand(ICommand command)
	{
		commands.add(command);
	}
	
	public void executeState()
	{
		for(ICommand c : this.commands)
		{
			c.Execute();
		}
	}
	
	public void revertState()
	{
		for(ICommand c : this.commands)
		{
			c.UnExecute();
		}
	}
}
