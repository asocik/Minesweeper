/*----------------------------------------------------------------------------
 * Minesweeper
 *
 * Class: CS 342 Software Design
 *
 * Created by Alex Schlake
 * February 2013
 ----------------------------------------------------------------------------*/
/*
 * This class captures the left and right mouse clicks and invokes different
 * methods in the HandleEvents class depending on left or right clicks.
 *
 */

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class ClickHandler extends MouseAdapter{
	private int index;
    private static int activeToggle;

	public ClickHandler(int index) {
    	this.index = index;
        this.activeToggle = 1;
	}

    /**------------------------------------------------------------------------
     * This method handles the left and right mouse clicks
     * ------------------------------------------------------------------------*/
	public void mouseClicked (MouseEvent e){
            if( activeToggle == 0){
                // do nothing
            }else if (SwingUtilities.isLeftMouseButton(e)){
	    		// debugging System.out.println("Left Mouse Click at " + index);
	    		try {
					HandleEvents.leftClick(index);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	    	}
	    	else if (SwingUtilities.isRightMouseButton(e)){
	    		// debugging System.out.println("Right Mouse Click at " + index);
	    		HandleEvents.rightClick(index);
	    	}	
	   }

    public static void setToggle(int in) { activeToggle = in; }
}
