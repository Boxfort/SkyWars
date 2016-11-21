import java.awt.Color;
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
import java.lang.*;

public class SkyWarsCore extends JPanel
{
	private static final int SPRITE_SIZE       = 70  ;
	private static final int DRAWING_GRID_SIZE = 137 ;
	private static final int GRID_COLUMNS      = 4   ;
	private static final int GRID_ROWS         = 4   ;
	
	private MasterSpaceShip playerShip;
	private ArrayList<SpaceShip> spaceShips = new ArrayList<SpaceShip>();
	private Stack<GameState> states = new Stack<GameState>();
	
	private boolean running = true;
	private double frameTime;
	
	public SkyWarsCore(int frameRate) 
	{ 
		 frameTime = 1.0 / frameRate;
			playerShip = new MasterSpaceShip(new Point(1,1));
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
			
			//Draw all spaceships that exist
			for(SpaceShip ship : spaceShips)
			{			
					BufferedImage img = ImageIO.read(new File(ship.getSpriteSrc()));
					g.drawImage(img, (ship.getPosition().x * DRAWING_GRID_SIZE) + ((DRAWING_GRID_SIZE / 4)), (ship.getPosition().y * DRAWING_GRID_SIZE) + ((DRAWING_GRID_SIZE / 4)), SPRITE_SIZE, SPRITE_SIZE, null);
			}	
			
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
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
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
		while(true)
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
		GameState state = new GameState();
		
		MoveCommandFactory mcf = new MoveCommandFactory();
		
		MoveCommand pmc = mcf.GenerateMove(playerShip);
		state.addCommand(pmc);
		
		for(SpaceShip ship : spaceShips)
		{
			MoveCommand mc = mcf.GenerateMove(ship);
			state.addCommand(mc);
		}
		
		if(Math.random() * 3 > 2)
		{
			CreateEnemyCommand cec = new CreateEnemyCommand(this.spaceShips);
			state.addCommand(cec);
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
			//DESTROY SHIP
		}
		else
		{
			for(SpaceShip s : onPlayerTile)
			{
				DestroyEnemyCommand dec = new DestroyEnemyCommand(s, spaceShips);
				dec.Execute();
				state.addCommand(dec);
				playSound("explosion.wav");
			}
		}
	}
	
	public void undo()
	{
		this.states.pop().revertState();
	}
	
	public void redo()
	{
		
	}
	
	public void stop()
	{
		//TODO: CLEARUP THE GAME
	}
	
	 public void actionPerformed(ActionEvent e)
	 {
		 Object s = e.getSource();
	 }
}
