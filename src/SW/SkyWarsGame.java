package SW;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import Observer.IObserver;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

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

public class SkyWarsGame implements IObserver
{
	private static final String TITLE                       = "SkyWars" ;
	private static final int    WIDTH                       = 800       ;
	private static final int    HEIGHT		                = 800       ;
	private static final int    VERTICAL_DIVIDER_LOCATION   = 550       ;
	private static final int    HORIZONTAL_DIVIDER_LOCATION = 580       ;
	private static final int    DIVIDER_SIZE 	            = 0         ;

	private JFrame frame;
	private JScrollPane scrollPane;
	private JTextArea textPane;
	SkyWarsCore game;
	
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
				} 
				catch (Exception e)
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
		frame.repaint();
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(new EmptyBorder(10, 0, 10, 0));
		splitPane.setDividerLocation(VERTICAL_DIVIDER_LOCATION);
		splitPane.setDividerSize(DIVIDER_SIZE);
		frame.getContentPane().add(splitPane, "name_565518731957572");
		
		JSplitPane splitPane2 = new JSplitPane();
		splitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane2.setBorder(new EmptyBorder(10, 10, 10, 10));
		splitPane2.setLeftComponent(splitPane);
		splitPane2.setDividerLocation(HORIZONTAL_DIVIDER_LOCATION);
		splitPane2.setDividerSize(DIVIDER_SIZE);
		frame.getContentPane().add(splitPane2, "splitpane2");
		
		textPane = new JTextArea();
		textPane.setAutoscrolls(true);
		textPane.setEditable(false);
		textPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		scrollPane = new JScrollPane (textPane, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		splitPane2.setRightComponent(scrollPane);
		
		game = new SkyWarsCore(60);
		game.setBorder(new EmptyBorder(10,10,10,10));
		splitPane.setLeftComponent(game);
		game.setLayout(new CardLayout(0, 0));
		game.register(this);
		
		JPanel rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		rightPanel.setLayout(new GridLayout(5, 1, 0, 10));

		JButton btnStart = new JButton("Start Game");
		btnStart.addActionListener(new ActionListener() 
		{
	        public void actionPerformed(ActionEvent ae)
	        {
	            game.start();
	        }
		});
		
		rightPanel.add(btnStart);
		
		JButton btnMove = new JButton("Move");
		btnMove.addActionListener(new ActionListener() 
		{
	        public void actionPerformed(ActionEvent ae)
	        {
	            game.move();
	        }
		});
		       
		rightPanel.add(btnMove);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				game.undo();
			}
		});
		rightPanel.add(btnUndo);
		
		JButton btnRedo = new JButton("Redo");
		btnRedo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				game.redo();
			}
		});
		rightPanel.add(btnRedo);
		
		JButton btnEnd = new JButton("End Game");
		rightPanel.add(btnEnd);
	}

	@Override
	public void update(String updateText)
	{
		this.textPane.append(updateText + "\n");
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}
}
