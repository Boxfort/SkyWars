import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Painter extends JPanel
{
	private static final int SPRITE_SIZE       = 70  ;
	private static final int DRAWING_GRID_SIZE = 137 ;
	private static final int GRID_WIDTH        = 560 ;
	private static final int GRID_HEIGHT       = 580 ;
	private static final int GRID_COLUMNS      = 4   ;
	private static final int GRID_ROWS         = 4   ;
	
	public Painter() { }
	
	private ArrayList<SpaceShip> spaceShips = null;
	
	@Override
	public void paintComponent(Graphics g)
	{
		for(SpaceShip ship : spaceShips)
		{			
			try
			{				
				System.out.println(new File(ship.getSpriteSrc()).getAbsolutePath());
				BufferedImage img = ImageIO.read(new File(ship.getSpriteSrc()));
				g.drawImage(img, (ship.getPosition().x * DRAWING_GRID_SIZE) + (DRAWING_GRID_SIZE / 4), (ship.getPosition().y * DRAWING_GRID_SIZE) + (DRAWING_GRID_SIZE / 4), SPRITE_SIZE, SPRITE_SIZE, null);
			
				//Draw the grid
				
				g.setColor(Color.BLACK);
				
				for (int y = 0; y < GRID_ROWS + 1; ++y)
				  {
				    g.drawLine(0, y * DRAWING_GRID_SIZE, GRID_ROWS * DRAWING_GRID_SIZE, y * DRAWING_GRID_SIZE);
				  }

				  for (int x = 0; x < GRID_COLUMNS + 1; ++x)
				  {
				    g.drawLine(x * DRAWING_GRID_SIZE, 0, x * DRAWING_GRID_SIZE, GRID_COLUMNS * DRAWING_GRID_SIZE);
				  }
			}
			catch(IOException ex)
			{
				System.out.println(ex.getMessage());
			}
		}
	}
	
	public void setSpaceships(ArrayList<SpaceShip> spaceShips)
	{
		this.spaceShips = spaceShips;
	}
}
