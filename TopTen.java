/*----------------------------------------------------------------------------
 * Minesweeper
 *
 * Class: CS 342 Software Design 
 *
 * Created by Adam Socik
 * February 2014
 ----------------------------------------------------------------------------*/
/*
 * This class controls any modifications and lookups to the Ten Ten list. 
 * 
 * Methods in the class:
 * view:		Displays the top ten list in the GUI
 * clear:		Clears all data in the top ten list
 * qualifies:	Checks to see if a user score qualifies for the top ten list. If
 * 				it does then the user is prompted to enter a name and they are 
 * 				added to the list.
 * update:		Updates the variables in the class with any new data
 * verify: 		Verifies that topTen.txt exists, if not then it is created.
 */
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class TopTen 
{
	String listEntry;				// Stores entire file for printing
	ArrayList<String> textList;		// Stores each line of the file at an index
	ArrayList<String> scoreList;	// Stores the score of each line an an index
	
	public TopTen() throws IOException
	{
		textList = new ArrayList<String>();
		scoreList = new ArrayList<String>();
		update();	// Updates all variables to current scores in top ten list
	}
	
	/**------------------------------------------------------------------------
	 * Shows the current list of top ten scores in GUI
	 * ------------------------------------------------------------------------*/
	public void view()
	{
		JOptionPane.showMessageDialog(null, listEntry, "Top Ten", JFrame.EXIT_ON_CLOSE);
	}
	
	/**------------------------------------------------------------------------
	 * Clears all data in the top ten list
	 * 
	 * @throws IOException
	 * ------------------------------------------------------------------------*/
	public void clear() throws IOException
	{
		PrintWriter writer = new PrintWriter("topTen.txt");
		writer.print("");	// Clear everything
		writer.close();
		update();			// Update variables in class
		JOptionPane.showMessageDialog
		(null, "Top ten list has been cleared.", "Clear Top Ten", JFrame.EXIT_ON_CLOSE);
	}
	
	/**------------------------------------------------------------------------
	 * Checks to see if a user score qualifies for the top ten list. If it does 
	 * then the user is prompted to enter a name and they are added to the list.
	 * topTen.txt is also updated.
	 * 
	 * @param userScore			// User time for completing the game
	 * @throws IOException
	 * ------------------------------------------------------------------------*/
	public void qualifies(int userScore) throws IOException
	{
		int position = 0;
		if (scoreList.size() != 0)	// At least one score in the list
		{
			// If there are ten scores then remove the last/worst one
			if (textList.size() == 10)
			{
				// If the user score is larger than the worst top ten score then they
				// did not qualify
				if (userScore > Integer.parseInt(scoreList.get(scoreList.size()-1)))
					return;
				
				textList.remove(textList.size()-1);
			}
				
			position = scoreList.size();
			// Find out which position they qualify for
			for (int i = scoreList.size()-1; i >= 0; i--) 
			{
				if (userScore < Integer.parseInt(scoreList.get(i)))
					position = i;
			}
		}
		
		// Prompt the user for their name
		String name = JOptionPane.showInputDialog
		("Congradulations, you made the top ten list.\nEnter your name: ");
		
		// If cancel was clicked just use default name
		if (name == null || name.length() == 0)
			name = "Player";
		
		// Remove any numbers from the name that may cause problems with 
		// evaluation in the future
		name = name.replaceAll("[0-9]","");
		
		// Add the new score to the appropriate position
		textList.add(position, name + " " + (userScore-1));	// -1 to reflect what is on GUI
		
		// Update topTen.txt
		PrintWriter writer = new PrintWriter("topTen.txt");
		writer.print("");	// Clear everything
		
		// Write the updated list
		for (int i = 0; i < textList.size(); i++) 
			writer.println(textList.get(i));
		
		writer.close();

		update(); // Update class variables with the updated list
	} // End public void qualifies(int userScore) throws IOException
	
	/**------------------------------------------------------------------------
	 *  Updates the variables in the class with any new data
	 * 
	 * @throws IOException
	 * ------------------------------------------------------------------------*/
	private void update() throws IOException
	{
		verify();	// Make sure topTen.txt exists (if not then it is created)
		
		// Clear out old data
		listEntry = null;
		scoreList.clear();
		textList.clear();
		
		// Read the entire list from the file and store into variables
		BufferedReader reader = new BufferedReader(new FileReader("topTen.txt"));		
		StringBuilder sb = new StringBuilder();
		listEntry = reader.readLine();
		int count = 1;
		
		while (listEntry != null)
		{
			sb.append(count + ". " + listEntry + "\n");
			count++;
			textList.add(listEntry);
			scoreList.add(listEntry);
			listEntry = reader.readLine();
		}
		
		listEntry = sb.toString();	// Store entire list into String list
		reader.close();
		
		// Filter out all text but the time for scoreList. Example: "Adam 1234" -> "1234"
		for (int i = 0; i < scoreList.size(); i++) 
		{
			// Remove all non digit characters
			String temp = scoreList.get(i).replaceAll("[^0-9]+", "");
			
			// Replace index with digit only string
			scoreList.remove(i);
			scoreList.add(i, temp);
		}
	} // End private void update() throws IOException
	
	/**------------------------------------------------------------------------
	 *  Verifies that topTen.txt exists, if not then topTen.txt is created
	 * ------------------------------------------------------------------------*/
	private void verify()
	{
		File test = new File("topTen.txt");
		if (!test.exists())
		{
			try 
			{
				test.createNewFile();
			} catch (IOException e) {
				System.out.println("Failed to create topTen.txt");
			}
		}
	}
}

/*
 * Test data for topTen.txt
player 1
player 22
player 333
player 4444
player 55555
player 666666
player 7777777
player 88888888
player 999999999
player 1000000000
 */
