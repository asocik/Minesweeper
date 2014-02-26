/*----------------------------------------------------------------------------
 * Minesweeper
 *
 * Class: CS 342 Software Design 
 *
 * Created by Adam Socik
 * February 2013
 ----------------------------------------------------------------------------*/
/*
 * This class sets up the GUI for the entire game and contains any methods for 
 * manipulation of the GUI.
 */
import com.sun.org.apache.xpath.internal.SourceTree;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.Timer;

import java.util.*;

public class MinesweeperGrid extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel grid;			// Contains 10 x 10 grid of buttons
	private JPanel topBar;
	private static JButton buttons[];
	private static int mines[];			// Stores the index of mine locations in buttons[]
    private static int adjacencies[];
	private int numOfMines = 10;	// Number of mines not flagged - default is 10
	
	// Menu bar items
	private JMenuBar menuBar;
	private JMenu game;
	private JMenu help;
	private JMenu topTen;
	private JMenuItem viewTopTen;
	private JMenuItem clearTopTen;
	private JMenuItem resetItem;
	private JMenuItem exit;
	private JMenuItem helpItem;
	private JMenuItem about;
	
	// Top Bar times
	private JLabel timerLabel;
	private static Timer timer;
	private static int timeCount = 0; 
	private JButton restart;
	private JLabel mineLabel;
	
	// Icon images
	static Icon box;
	static Icon flag;
	static Icon mine;
	static Icon questionMarkIcon;
    static Icon zero;
    static Icon one;
    static Icon two;
    static Icon three;
    static Icon four;
    static Icon five;
    static Icon six;
    static Icon seven;
    static Icon eight;
	
	static TopTen list;	// Class that handles the top ten list
	
	public MinesweeperGrid() throws IOException 
	{		
		//---------------------------------------------------------
		// Set up image icons
		//---------------------------------------------------------
		box = new ImageIcon(getClass().getResource("images/box.png"));
		flag = new ImageIcon(getClass().getResource("images/flag.png"));
		mine = new ImageIcon(getClass().getResource("images/mine.png"));
		questionMarkIcon = new ImageIcon(getClass().getResource("images/qm.png"));
        zero = new ImageIcon(getClass().getResource("images/zero.png"));
        one = new ImageIcon(getClass().getResource("images/one.png"));
        two = new ImageIcon(getClass().getResource("images/two.png"));
        three = new ImageIcon(getClass().getResource("images/three.png"));
        four= new ImageIcon(getClass().getResource("images/four.png"));
        five = new ImageIcon(getClass().getResource("images/five.png"));
        six = new ImageIcon(getClass().getResource("images/six.png"));
        seven = new ImageIcon(getClass().getResource("images/seven.png"));
        eight = new ImageIcon(getClass().getResource("images/eight.png"));
		
		//---------------------------------------------------------
		// Set up the grid of 100 buttons
		//---------------------------------------------------------
		grid = new JPanel(new GridLayout(10, 10));
		setButtons(new JButton[100]);
		
		// Initialize each button
		for (int i = 0; i < 100; i++)
		{			
			getButtons()[i] = new JButton(box);
			getButtons()[i].addMouseListener(new ClickHandler(i));
			grid.add(getButtons()[i]);
		}
		setMines();	// Set 10 random mines in the grid
		add(grid, BorderLayout.CENTER);	// Add Panel to the frame
		
		//---------------------------------------------------------
		// Set up the menu bar
		//---------------------------------------------------------
		menuBar = new JMenuBar();
		
		// Game menu
		game = new JMenu("Game");
		resetItem = new JMenuItem("Restart");
		exit = new JMenuItem("Exit");
		
		// Help menu
		help = new JMenu("Help");
		helpItem = new JMenuItem("Help");
		about = new JMenuItem("About");
		
		// Top ten submenu 
		topTen = new JMenu("Top Ten");
		viewTopTen = new JMenuItem("View List");
		clearTopTen = new JMenuItem("Clear List");
		
		topTen.add(viewTopTen);
		topTen.add(clearTopTen);
		help.add(helpItem);
		help.add(about);
		game.add(resetItem);
		game.add(topTen);
		game.add(exit);
		menuBar.add(game);
		menuBar.add(help);
		
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
		clearTopTen.addActionListener(this);
		viewTopTen.addActionListener(this);
		
		//---------------------------------------------------------
		// Set up the timer in the top bar
		//---------------------------------------------------------
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
		
		//---------------------------------------------------------
		// Set up the restart button and mine counter for the top bar
		//---------------------------------------------------------
		// Set up the restart button
		restart = new JButton("Restart");
		restart.addActionListener(this);
		
		// Set up mine counter 
		mineLabel = new JLabel();
		mineLabel.setText("Mines: " + numOfMines);
		
		// Add all of the elements to the top bar
		topBar.add(timerLabel, BorderLayout.CENTER);
		topBar.add(mineLabel, BorderLayout.WEST);
		topBar.add(restart, BorderLayout.EAST);
		
		list = new TopTen();		
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
        int numAdjacentMines = 0;
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
        adjacencies = new int[100];
		// Reset all boxes to normal
		for( int i = 0; i < 100; i++){
			getButtons()[i].setIcon(box);
		}
		for (int i = 0; i < mines.length; i++)
		{
			//System.out.println(random[i]);		// For debugging so you can see mine locations
			getButtons()[random[i]].setIcon(mine);	// For debugging so you can see mine locations
			mines[i] = random[i];
		}
        for( int i = 0; i < 100; i++){
            for(int j = 0; j < getMines().length; j++){
                if( i % 10 == 0 ){
                    if( i-10 == getMines()[j])
                        numAdjacentMines++;
                    if( i-9 == getMines()[j])
                        numAdjacentMines++;
                    if( i+1 == getMines()[j])
                        numAdjacentMines++;
                    if( i+10 == getMines()[j])
                        numAdjacentMines++;
                    if( i+11 == getMines()[j])
                        numAdjacentMines++;
                }else if ((i+1) % 10 == 0){
                    if( i-11 == getMines()[j])
                        numAdjacentMines++;
                    if( i-10 == getMines()[j])
                        numAdjacentMines++;
                    if( i-1 == getMines()[j])
                        numAdjacentMines++;
                    if( i+9 == getMines()[j])
                        numAdjacentMines++;
                    if( i+10 == getMines()[j])
                        numAdjacentMines++;
                }else{
                    if( i-11 == getMines()[j])
                        numAdjacentMines++;
                    if( i-10 == getMines()[j])
                        numAdjacentMines++;
                    if( i-9 == getMines()[j])
                        numAdjacentMines++;
                    if( i-1 == getMines()[j])
                        numAdjacentMines++;
                    if( i+1 == getMines()[j])
                        numAdjacentMines++;
                    if( i+9 == getMines()[j])
                        numAdjacentMines++;
                    if( i+10 == getMines()[j])
                        numAdjacentMines++;
                    if( i+11 == getMines()[j])
                        numAdjacentMines++;
                }
            }
           adjacencies[i] = numAdjacentMines;
           numAdjacentMines = 0;
        }
        /* for debugging adjacent mines
        for(int i = 0; i < 100; i++){
                if( i % 10 == 0 ){
                    System.out.println("\n");
                }
                System.out.print(" " + adjacencies[i]);
        }
        */
	} // End private void setMines()

	/**------------------------------------------------------------------------
	 * If the game is over stop the timer, disable all buttons on the grid, and
	 * add check if the time belongs in the top ten
	 * 
	 *  @throws IOException
	 * ------------------------------------------------------------------------*/
	public static void gameover() throws IOException
	{
		for (int i = 0; i < 100; i++)
			getButtons()[i].setEnabled(false);
		timer.stop();
		list.qualifies(timeCount);
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
			
			JOptionPane.showMessageDialog(null, output, "Rules", EXIT_ON_CLOSE);
		}
		
		// Action listener for restart button and for top bar restart option
		if (e.getSource() == restart || e.getSource() == resetItem)
		{
			timeCount = 0;	// Reset Timer
			
			for (int i = 0; i < 100; i++)
				getButtons()[i].setEnabled(true);
			timer.start();
			
			// Clear out the old mines
			for (int i=0; i<10; i++)
				getButtons()[getMines()[i]].setIcon(box);
		
			setMines();		// Set the new mines
			
			numOfMines = 10;
			mineLabel.setText("Mines: " + numOfMines);	// Reset the mine counter
		}
		
		// Action listener for top ten in top bar - shows top ten scores
		if (e.getSource() == viewTopTen)
		{
			list.view();
		}
		
		if (e.getSource() == clearTopTen)
		{
			try 
			{
				list.clear();
			} catch (FileNotFoundException e1) 
			{
				System.out.println("ERROR: File not found when trying to clear it");
			} catch (IOException e1) 
			{
				System.out.println("ERROR: IOException when trying to clear topTen.txt");
			}
		}
	} // End public void actionPerformed(ActionEvent e) 

	/**
	 * @return the mines
	 */
	public static int[] getMines() {
		return mines;
	}

	/**
	 * @param mines the mines to set
	 */
	public void setMines(int mines[]) {
		MinesweeperGrid.mines = mines;
	}

	/**
	 * @return the buttons
	 */
	public static JButton[] getButtons() {
		return buttons;
	}

	/**
	 * @param buttons the buttons to set
	 */
	public void setButtons(JButton buttons[]) {
		MinesweeperGrid.buttons = buttons;
	}

    public static int[] getAdjacencies() {
        return adjacencies;
    }
}

