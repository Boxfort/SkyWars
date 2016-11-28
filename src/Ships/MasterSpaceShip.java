package Ships;
import java.awt.Point;
import java.util.ArrayList;

import SW.DefensiveMode;
import SW.OperationalMode;

public class MasterSpaceShip extends SpaceShip
{	
	private OperationalMode operationalMode = new DefensiveMode();

	public MasterSpaceShip(Point position, String pilotName)
	{
		super(position, pilotName,"/sprites/MasterShip.png");
	}
	
	/**
	 * Take in a list of enemies and decided if the combat is won depending on the operational mode
	 * @param enemies List of enemies to fight
	 * @return true if combat was won, false if combat was lost
	 */
	public boolean Combat(ArrayList<SpaceShip> enemies)
	{
		return operationalMode.combat(enemies);
	}
	
	public OperationalMode getOperationalMode()
	{
		return this.operationalMode;
	}
	
	public void setOperationalMode(OperationalMode operationalMode)
	{
		this.operationalMode = operationalMode;
	}
	
	public String toString()
	{
		return "Master ship";
	}
}

