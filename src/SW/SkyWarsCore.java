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
import javax.swing.JPanel;

import Commands.CreateEnemyCommand;
import Commands.CreateEnemyFactory;
import Commands.DestroyEnemyCommand;
import Commands.MoveCommand;
import Commands.MoveCommandFactory;
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
	
	private boolean running = false;
	private double frameTime;
	
	public SkyWarsCore(int frameRate) 
	{ 
		 frameTime = 1.0 / frameRate;
		 playerShip = new MasterSpaceShip(new Point(1,1), "You");
	}
	
	 @Override
     public Dimension getPreferredSize() {
         return new Dimension(548, 548);
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
	
	public void start()
	{
		running = true;
		publishUpdate("Game started!");
		
		Thread loop = new Thread()
	    {
	         public void run()
	         {
	            gameLoop();
	         }
	    };
	    
	    loop.start();
	}
	
	public void gameLoop()
	{
		while(running)
		{
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
	
	public void move()
	{
		if(!running)
			return;
		
		this.redoStates.clear();
		
		GameState state = new GameState();
		
		MoveCommandFactory moveFactory = new MoveCommandFactory();
		
		MoveCommand pmc = moveFactory.GenerateMove(playerShip);
		state.addCommand(pmc);
		
		for(SpaceShip ship : spaceShips)
		{
			MoveCommand mc = moveFactory.GenerateMove(ship);
			state.addCommand(mc);
		}
		
		if(Math.random() * 3 > 2)
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
	
	private void combat(GameState state)
	{
		ArrayList<SpaceShip> onPlayerTile = new ArrayList<SpaceShip>();
		
		for(SpaceShip s : spaceShips)
		{
			if(s.getPosition().getX() == playerShip.getPosition().getX() && s.getPosition().getY() == playerShip.getPosition().getY())
			{
				onPlayerTile.add(s);
			}
		}
		
		if(onPlayerTile.size() > 2 || (onPlayerTile.size() == 2 && playerShip.getOperationalMode() == OperationalMode.defensive))
		{
			//TODO: End Game
			publishUpdate("You have been defeated!");
		}
		else
		{
			for(SpaceShip s : onPlayerTile)
			{
				DestroyEnemyCommand dec = new DestroyEnemyCommand(s, spaceShips);
				dec.Execute();
				state.addCommand(dec);
				
				publishUpdate("Enemy \""+ dec.getShip().getPilotName() + "\" ("+dec.getShip().toString()+") has been destroyed!");
				
				playSound("explosion.wav");
			}
		}
	}
	
	public void undo()
	{
		if(!running)
			return;
		
		GameState state = this.states.pop();
		state.revertState();
		this.redoStates.add(state);
	}
	
	public void redo()
	{
		if(!running)
			return;
		
		GameState state = redoStates.pop();
		state.executeState();
		this.states.add(state);
	}
	
	public void stop()
	{
		if(!running)
			return;
		
		this.states.clear();
		this.redoStates.clear();
		this.spaceShips.clear();
		
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
