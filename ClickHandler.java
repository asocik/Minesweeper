import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class ClickHandler extends MouseAdapter{
	private int index;

	public ClickHandler(int index) {
    	this.index = index;
	}

	public void mouseClicked (MouseEvent e)
	   {
	    	if (SwingUtilities.isLeftMouseButton(e)){
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
}
