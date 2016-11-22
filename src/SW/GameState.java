package SW;
import java.util.ArrayList;

import Commands.ICommand;

/**
 * Contains all commands which were executed in a turn of SkyWars
 * @author Jack
 *
 */
public class GameState
{
	private ArrayList<ICommand> commands = new ArrayList<ICommand>();
	
	public GameState() { }
	
	public void addCommand(ICommand command)
	{
		commands.add(command);
	}
	
	/**
	 * Executes all commands in this state
	 */
	public void executeState()
	{
		for(ICommand c : this.commands)
		{
			c.Execute();
		}
	}
	
	/**
	 * Unexecutes all commands in this state
	 */
	public void revertState()
	{
		for(ICommand c : this.commands)
		{
			c.UnExecute();
		}
	}
}
