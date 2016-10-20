import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class SkyWarsGame
{
	private static final String TITLE             = "SkyWars" ;
	private static final int    WIDTH             = 800       ;
	private static final int    HEIGHT		      = 600       ;
	private static final int    DIVIDER_LOCATION  = 580       ;
	private static final int    DIVIDER_SIZE 	  = 0         ;

	private JFrame frame;
	Painter leftPanel;
	private ArrayList<SpaceShip> spaceShips = new ArrayList<SpaceShip>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					SkyWarsGame window = new SkyWarsGame();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SkyWarsGame()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame(TITLE);
		frame.setSize(WIDTH, HEIGHT);;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		splitPane.setDividerLocation(DIVIDER_LOCATION);
		splitPane.setDividerSize(DIVIDER_SIZE);
		frame.getContentPane().add(splitPane, "name_565518731957572");
		
		leftPanel = new Painter();
		splitPane.setLeftComponent(leftPanel);
		leftPanel.setLayout(new CardLayout(0, 0));
		
		JPanel rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		rightPanel.setLayout(new GridLayout(5, 1, 0, 10));
		
		JButton btnStart = new JButton("Start Game");
		rightPanel.add(btnStart);
		
		JButton btnMove = new JButton("Move");
		rightPanel.add(btnMove);
		
		JButton btnUndo = new JButton("Undo");
		rightPanel.add(btnUndo);
		
		JButton btnRedo = new JButton("Redo");
		rightPanel.add(btnRedo);
		
		JButton btnEnd = new JButton("End Game");
		rightPanel.add(btnEnd);
		
		spaceShips.add(new MasterSpaceShip("Hello", (new Point(1,1))));
		draw();
	}

	public void draw()
	{
		leftPanel.setSpaceships(this.spaceShips);
		leftPanel.repaint();
	}
}
