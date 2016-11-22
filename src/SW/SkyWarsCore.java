package SW;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Commands.CreateEnemyCommand;
import Commands.CreateEnemyFactory;
import Commands.DestroyEnemyCommand;
import Commands.MoveCommand;
import Commands.MoveCommandFactory;
import Commands.PlayerDefeatCommand;
import Ships.MasterSpaceShip;
import Ships.SpaceShip;
import Observer.IObservable;
import Observer.IObserver;

import java.lang.*;

public class SkyWarsCore extends JPanel implements IObservable
{
	private static final int SPRITE_SIZE       = 70  ;
	private static final int DRAWING_GRID_SIZE = 137 ;
	private static final int GRID_COLUMNS      = 4   ;
	private static final int GRID_ROWS         = 4   ;
	
	private ArrayList<IObserver> observers = new ArrayList<IObserver>();
	
	private MasterSpaceShip playerShip;
	private ArrayList<SpaceShip> spaceShips = new ArrayList<SpaceShip>();
	private Stack<GameState> states = new Stack<GameState>();
	private Stack<GameState> redoStates = new Stack<GameState>();
	
	private Random rand = new Random();
	private boolean running = false;
	private boolean paused = false;
	private double frameTime;
	
	public SkyWarsCore(int frameRate) 
	{ 
		 frameTime = 1.0 / frameRate;
		 spawnPlayer();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		try
		{
			//Draw the space image background
			BufferedImage img1 = ImageIO.read(new File("src/sprites/space.jpg"));
			g.drawImage(img1, 0, 0, DRAWING_GRID_SIZE * GRID_COLUMNS, DRAWING_GRID_SIZE * GRID_ROWS, null);
		
			//Draw the white grid
			g.setColor(Color.WHITE);
			
			for (int y = 0; y < GRID_ROWS + 1; ++y)
			{
			    g.drawLine(0, y * DRAWING_GRID_SIZE, GRID_ROWS * DRAWING_GRID_SIZE, y * DRAWING_GRID_SIZE);
			}
	
			for (int x = 0; x < GRID_COLUMNS + 1; ++x)
			{
				g.drawLine(x * DRAWING_GRID_SIZE, 0, x * DRAWING_GRID_SIZE, GRID_COLUMNS * DRAWING_GRID_SIZE);
			}
			
			//Draw all enemy spaceships that exist
			for(SpaceShip ship : spaceShips)
			{			
					BufferedImage img = ImageIO.read(new File(ship.getSpriteSrc()));
					g.drawImage(img, (ship.getPosition().x * DRAWING_GRID_SIZE) + ((DRAWING_GRID_SIZE / 4)), (ship.getPosition().y * DRAWING_GRID_SIZE) + ((DRAWING_GRID_SIZE / 4)), SPRITE_SIZE, SPRITE_SIZE, null);
					g.drawString(ship.getPilotName(), ship.getPosition().x * DRAWING_GRID_SIZE + ((DRAWING_GRID_SIZE / 4) + 15), ship.getPosition().y * DRAWING_GRID_SIZE + ((DRAWING_GRID_SIZE) - 15));
			}	
			
			//Draw the player Ship
			BufferedImage img = ImageIO.read(new File(playerShip.getSpriteSrc()));
			g.drawImage(img, (playerShip.getPosition().x * DRAWING_GRID_SIZE) + (DRAWING_GRID_SIZE / 4), (playerShip.getPosition().y * DRAWING_GRID_SIZE) + (DRAWING_GRID_SIZE / 4), SPRITE_SIZE, SPRITE_SIZE, null);
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Plays a sound file from the src/sounds/ folder
	 * with the specified name in its own thread.
	 * @param url Filename and extension of the sound to be played.
	 */
	public static synchronized void playSound(final String url) {
		  new Thread(new Runnable() {
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/sounds/"+url));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage() + "Shits fucked");
		      }
		    }
		  }).start();
		}
	
	/**
	 * Begins the game loop if not already started.
	 */
	public void start()
	{
		if(running)
			return;
		
		running = true;
		publishUpdate("Game started!");
		spawnPlayer();
		
		Thread loop = new Thread()
	    {
	         public void run()
	         {
	            gameLoop();
	         }
	    };
	    
	    loop.start();
	}
	
	/**
	 * Re-draws the panel every frame
	 */
	public void gameLoop()
	{
		while(running)
		{
			if(!paused)
				this.repaint();
			
			try
			{
				Thread.sleep(10);
			} 
			catch (InterruptedException e)
			{
			}
		}
	}
	
	/**
	 * Picks a random location in the grid which
	 * isn't (0,0) and instantiates the players ship.
	 */
	public void spawnPlayer()
	{
		int x, y;
		
		do
		{
			x = rand.nextInt(GRID_COLUMNS - 1);
			y = rand.nextInt(GRID_ROWS - 1);
		}
		while(x == 0 && y == 0);
		
		playerShip = new MasterSpaceShip(new Point(x,y), "Player");
	}
	
	/**
	 * Carries out a single turn in the game.
	 */
	public void move()
	{
		if(!running)
			return;
		
		//Since a move has been made, all redos are removed.
		this.redoStates.clear();
		
		GameState state = new GameState();
		
		MoveCommandFactory moveFactory = new MoveCommandFactory();
		
		//Generate a movement for the player
		MoveCommand pmc = moveFactory.GenerateMove(playerShip);
		state.addCommand(pmc);
		
		//For each enemy generate a movement
		for(SpaceShip ship : spaceShips)
		{
			MoveCommand mc = moveFactory.GenerateMove(ship);
			state.addCommand(mc);
		}
		
		//1 in 3 chance of a new enemy spawning each turn
		if((rand.nextInt(3)+1) > 2)
		{
			CreateEnemyFactory enemyFactory = new CreateEnemyFactory();
			CreateEnemyCommand cec = enemyFactory.generateEnemy(this.spaceShips);
			
			state.addCommand(cec);
			
			publishUpdate("Enemy \""+ cec.getShip().getPilotName() + "\" ("+cec.getShip().toString()+") has entered the battle!");
			playSound("spawn.wav");
		}
		
		state.executeState();
		
		combat(state);
		
		this.states.push(state);
	}
	
	public void setOperationalMode(OperationalMode opMode)
	{
		if(!running || paused)
			return;
		
		playerShip.setOperationalMode(opMode);
		publishUpdate("Operational mode set to : " + opMode.toString());
	}
	
	/**
	 * Detects whether or not the player occupies the same space as
	 * an enemy, and how many, then decides what to do.
	 * @param state The game state which combat is to take place in.
	 */
	private void combat(GameState state)
	{
		ArrayList<SpaceShip> onPlayerTile = new ArrayList<SpaceShip>();
		
		//Get all enemy ships on the same tile as the player
		for(SpaceShip s : spaceShips)
		{
			if(s.getPosition().getX() == playerShip.getPosition().getX() && s.getPosition().getY() == playerShip.getPosition().getY())
			{
				onPlayerTile.add(s);
			}
		}
		
		if(playerShip.Combat(onPlayerTile))
		{
			//Destoy all ships on the same tile as the player
			for(SpaceShip s : onPlayerTile)
			{
				DestroyEnemyCommand dec = new DestroyEnemyCommand(s, spaceShips);
				dec.Execute();
				state.addCommand(dec);
				
				publishUpdate("Enemy \""+ dec.getShip().getPilotName() + "\" ("+dec.getShip().toString()+") has been destroyed!");
				
				playSound("explosion.wav");
			}

		}
		else
		{
			publishUpdate("You have been defeated!");
			PlayerDefeatCommand pdf = new PlayerDefeatCommand(this);
			state.addCommand(pdf);
			this.states.push(state);
			pdf.Execute();
		}
	}
	
	public void onDefeat()
	{
		playerShip.setSpriteSrc("src/sprites/explosion.png");
		paused = true;
		
		String[] options = { "Restart", "Undo", "Exit" };
		int response = JOptionPane.showOptionDialog(null, "You were defeated!", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		playerShip.setSpriteSrc("src/sprites/MasterShip.png");
		paused = false;
		
		switch(response) 
		{
		case 0:
			this.stop();
			this.start();
			break;
		case 1:
			undo();
			break;
		case 2:
			System.exit(0);
		}
	}

	/**
	 * Reverts back to the previous game state if one exists.
	 */
	public void undo()
	{
		if(!running || paused)
			return;
		
		try
		{
			GameState state = this.states.pop();
			state.revertState();
			this.redoStates.add(state);
		}
		catch(NullPointerException ex)
		{
			
		}
	}
	
	/**
	 * Reverts back to a previously undone game state, if one exists.
	 */
	public void redo()
	{
		if(!running || paused)
			return;
		
		try
		{
		GameState state = redoStates.pop();
		state.executeState();
		combat(state);
		this.states.add(state);
		}
		catch(NullPointerException ex)
		{
			
		}
	}
	
	/**
	 * Ends the current game if it is running.
	 */
	public void stop()
	{
		if(!running || paused)
			return;
		
		this.states.clear();
		this.redoStates.clear();
		this.spaceShips.clear();
		
		publishUpdate("Game stopped.");
		running = false;
	}
	
	 public void actionPerformed(ActionEvent e)
	 {
		 Object s = e.getSource();
	 }

	@Override
	public void register(IObserver observer)
	{
		this.observers.add(observer);
	}
	
	@Override
	public void unregister(IObserver observer)
	{
		this.observers.remove(observer);
	}

	@Override
	public void publishUpdate(String updateText)
	{
		for(IObserver o : this.observers)
		{
			o.update(updateText);
		}
	}
}
