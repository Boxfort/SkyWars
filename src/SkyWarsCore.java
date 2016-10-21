import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.lang.*;

public class SkyWarsCore extends JPanel implements Runnable
{
	private static final int SPRITE_SIZE       = 70  ;
	private static final int DRAWING_GRID_SIZE = 137 ;
	private static final int GRID_COLUMNS      = 4   ;
	private static final int GRID_ROWS         = 4   ;
	
	private ArrayList<SpaceShip> spaceShips = new ArrayList<SpaceShip>();
	private boolean running = true;
	
	public SkyWarsCore() { }
	
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
					System.out.println(new File(ship.getSpriteSrc()).getAbsolutePath());
					BufferedImage img = ImageIO.read(new File(ship.getSpriteSrc()));
					g.drawImage(img, (ship.getPosition().x * DRAWING_GRID_SIZE) + (DRAWING_GRID_SIZE / 4), (ship.getPosition().y * DRAWING_GRID_SIZE) + (DRAWING_GRID_SIZE / 4), SPRITE_SIZE, SPRITE_SIZE, null);
			}	
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public void start()
	{
		//TODO: Spawn players ship
	}
	
	public void move()
	{
		
	}
	
	public void undo()
	{
		
	}
	
	public void redo()
	{
		
	}
	
	public void stop()
	{
		//TODO: CLEARUP THE GAME
	}
	
	
	
	
}
