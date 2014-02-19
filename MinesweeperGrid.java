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
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.io.File;;

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
	
	// Icon images
	Icon box;
	Icon flag;
	Icon mine;
	Icon questionMarkIcon;
	
	File topTenFile;
	
	
	/**------------------------------------------------------------------------
	 * Constructor for the Mineswepper window
	 * ------------------------------------------------------------------------*/
	public MinesweeperGrid() 
	{			
		// Set up the grid of 100 buttons
		grid = new JPanel(new GridLayout(10, 10));
		buttons = new JButton[100];
		
		box = new ImageIcon(getClass().getResource("box.png"));
		flag = new ImageIcon(getClass().getResource("flag.png"));
		mine = new ImageIcon(getClass().getResource("mine.png"));
		questionMarkIcon = new ImageIcon(getClass().getResource("qm.png"));
		
		// Initialize each button
		for (int i = 0; i < 100; i++)
		{			
			buttons[i] = new JButton(box);
			buttons[i].setBackground(Color.WHITE);
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
		topTen.addActionListener(this);
		
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
		
		// Add all of the elements to the top bar
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
			buttons[random[i]].setIcon(mine);	// For debugging so you can see mine locations
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
	 * Action listeners for various buttons
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
			// Rules obtained from: http://windows.microsoft.com/en-us/windows/minesweeper-how-to#1TC=windows-7
			String output = 
			"How to play:\n1. Uncover a mine, and the game ends.\n"
			+ "2. Uncover an empty square, and you keep playing.\n"
			+ "3. Uncover a number, and it tells you how many mines lay hidden in the eight "
			+ "surrounding squares.\n    Use this information to deduce which nearby squares are safe to click."
			+ "\n\nIf you suspect a square conceals a mine, right-click it. This puts a flag on the square.\n"
			+ "If you're not sure, right-click again to make it a question mark.";
			
			JOptionPane.showMessageDialog(null, output, "About", EXIT_ON_CLOSE);
		}
		
		// Action listener for restart button and for top bar restart otption
		if (e.getSource() == restart || e.getSource() == resetItem)
		{
			timeCount = 0;	// Reset Timer
			
			// Clear out the old mines
			for (int i=0; i<10; i++)
				buttons[mines[i]].setIcon(box);
		
			setMines();		// Set the new mines
		}
		
		// Action listener for top ten in top bar - shows top ten scores
		if (e.getSource() == topTen)
		{
			topTenFile = new File("topTen.txt");
			
			
			File a = new File("topTen.txt");
			
			
			if (a.exists())
				System.out.println("I exist");
			else 
				System.out.println("Nope...");
			
			
			try 
			{ 
				Scanner input = new Scanner(topTenFile); 
				String line = input.nextLine();
				int num = line.charAt(17);
				
				System.out.println(line + " and " + num);
				
				
				input.close();
			} 
			catch (FileNotFoundException e1) 
			{ 
				System.out.println("Failed to open topTen.txt"); 
			}
			
			
			
		}
	} // End public void actionPerformed(ActionEvent e) 
	
}

