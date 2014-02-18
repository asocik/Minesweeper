/*----------------------------------------------------------------------------
 * Mineswepper
 *
 * Class: CS 342 Software Design 
 *
 * Created by Adam Socik
 * February 2013
 ----------------------------------------------------------------------------*/
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;

import java.util.*;

public class MinesweeperGrid extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel grid;
	private JPanel topBar;
	private JButton buttons[];
	private int mines[];			// Stores the index of mine locations in buttons[]
	private int numOfMines = 10;	// Number of mines not flagged - default is 10
	
	// Menu bar items
	private JMenuBar menuBar;
	private JMenu game;
	private JMenu help;
	private JMenuItem resetItem;
	private JMenuItem topTen;
	private JMenuItem exit;
	private JMenuItem helpItem;
	private JMenuItem about;
	
	// Top Bar times
	private JLabel timerLabel;
	private Timer timer;
	private int timeCount = 0; 
	private JButton restart;
	private JLabel mineLabel;
	
	/**------------------------------------------------------------------------
	 * Constructor for the Mineswepper window
	 * ------------------------------------------------------------------------*/
	public MinesweeperGrid() 
	{			
		// Set up the grid of 100 buttons
		grid = new JPanel(new GridLayout(10, 10));
		buttons = new JButton[100];
		
		
		// Initialize each button
		for (int i = 0; i < 100; i++)
		{
			buttons[i] = new JButton();
			buttons[i].setIcon(new ImageIcon("box.png"));
			validate();
			grid.add(buttons[i]);
		}
		setMines();	// Set 10 random mines in the grid
		add(grid, BorderLayout.CENTER);	// Add Panel to the frame
		
		// Set up the menu bar
		menuBar = new JMenuBar();
		game = new JMenu("Game");
		help = new JMenu("Help");
		resetItem = new JMenuItem("Restart");
		topTen = new JMenuItem("Top Ten");
		exit = new JMenuItem("Exit");
		helpItem = new JMenuItem("Help");
		about = new JMenuItem("About");
		menuBar.add(game);
		menuBar.add(help);
		game.add(resetItem);
		game.add(topTen);
		game.add(exit);
		help.add(helpItem);
		help.add(about);
		
		// Change some of the colors
		game.setBackground(Color.DARK_GRAY);
		help.setBackground(Color.DARK_GRAY);
		menuBar.setBackground(Color.DARK_GRAY);
		
		this.setJMenuBar(menuBar);	// Add menuBar to the frame
		
		// Set up action listeners for when menu items are clicked
		exit.addActionListener(this);
		resetItem.addActionListener(this);
		about.addActionListener(this);
		helpItem.addActionListener(this);
		
		// Set up the timer in the top bar
		topBar = new JPanel(new BorderLayout());
		timerLabel = new JLabel();
		add(topBar,BorderLayout.NORTH);
		
		ActionListener timerAction = new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				timerLabel.setText("Timer: " + timeCount);
				timeCount++;
				timerLabel.setHorizontalAlignment(JLabel.CENTER);
			}
		};
		
		timer = new Timer(1000, timerAction);
		timer.setInitialDelay(0);
		timer.start();
		
		// Set up the restart button for the top bar
		restart = new JButton("Restart");
		restart.addActionListener(this);
		
		// Set up counter to show how many mines are left
		mineLabel = new JLabel();
		mineLabel.setText("Mines: " + numOfMines);
				
		topBar.add(timerLabel, BorderLayout.CENTER);
		topBar.add(mineLabel, BorderLayout.WEST);
		topBar.add(restart, BorderLayout.EAST);
	} // End public MinesweeperGrid() 
	
	/**------------------------------------------------------------------------
	 * This method randomly sets 10 mines on the grid. Each mine location has to
	 * be unique so the method creates and initializes an array of size 100,
	 * randomly shuffles the array, and the picks the first 10 values for mines. 
	 * ------------------------------------------------------------------------*/
	private void setMines()
	{
		// Create and initialize random array
		int random[] = new int[100];
		for (int i = 0; i < random.length; i++) 
			random[i] = i;
		
		// Shuffle the array
		Random r = new Random();
		for (int i = 0; i < random.length; i++) 
		{
			int index = r.nextInt(i+1);
			int temp = random[index];
			random[index] = random[i];
			random[i]= temp; 
		}
		
		// Assign mines first 10 values in random array
		mines = new int[10];
		for (int i = 0; i < mines.length; i++)
		{
			System.out.println(random[i]);		// For debugging so you can see mine locations
			buttons[random[i]].setText("M");	// For debugging so you can see mine locations
			mines[i] = random[i];
		}
	} // End private void setMines()

	/**------------------------------------------------------------------------
	 * If the game is over stop the timer, disable all buttons on the grid, and
	 * add check if the time belongs in the top ten
	 * ------------------------------------------------------------------------*/
	public void gameover()
	{
		for (int i = 0; i < 100; i++)
			buttons[i].setEnabled(false);
		timer.stop();
	}
	
	/**------------------------------------------------------------------------
	 * Increment the number of flagged mines and update the GUI
	 * ------------------------------------------------------------------------*/
	public void incMines()
	{
		numOfMines++;
		mineLabel.setText("Mines: " + numOfMines);
	}
	
	/**------------------------------------------------------------------------
	 * Decrement the number of flagged mines and update the GUI
	 * ------------------------------------------------------------------------*/
	public void decMines()
	{
		numOfMines--;
		mineLabel.setText("Mines: " + numOfMines);
	}
	
	/**------------------------------------------------------------------------
	 * Zero out the number of flagged mines and update the GUI
	 * ------------------------------------------------------------------------*/
	public void zeroMines() 
	{
		numOfMines = 0;
		mineLabel.setText("Mines: " + numOfMines);
	}
	
	/**------------------------------------------------------------------------
	 * Action listeners for the menu items
	 * ------------------------------------------------------------------------*/
	@Override	// Override inherited methods
	public void actionPerformed(ActionEvent e) 
	{
		// Action listener for Exit menu item
		if (e.getSource() == exit)
			System.exit(0);
		
		// Action listener for About menu item
		if (e.getSource() == about)
		{
			String output = "Programmers: Adam Socik and Alexander Schlake\n"
						  + "CS 342 Software Design Spring 2014";
			JOptionPane.showMessageDialog(null, output, "About", EXIT_ON_CLOSE);
		}
		
		// Action listener for Help menu item
		if (e.getSource() == helpItem)
		{
			String output = "Will place rules here";
			JOptionPane.showMessageDialog(null, output, "About", EXIT_ON_CLOSE);
		}
		
		// Action listener for restart button for top bar
		if (e.getSource() == restart || e.getSource() == resetItem)
		{
			timeCount = 0;
			
			// Clear out the old mines
			for (int i=0; i<10; i++)
			{
				buttons[mines[i]].setText("");
			}
			
			// Set the new mines
			setMines();

		}
	} // End public void actionPerformed(ActionEvent e) 
	
}

