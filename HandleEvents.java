import java.io.IOException;

public class HandleEvents extends MinesweeperGrid {

	private static final long serialVersionUID = 1L;

	public HandleEvents(int index) throws IOException {
		super();
	}
	
	public static void leftClick(int index) throws IOException{
		if( (getButtons()[index].getIcon() == flag) || getButtons()[index].getIcon() == questionMarkIcon){
			// do nothing
		}else{
			// check if the button clicked is one of the mines
			for(int i = 0; i < getMines().length; i++){
				if( index == getMines()[i]){
					System.out.println("Explosion!");
					gameover();
				}
			}
		}
	}
	
	public static void rightClick(int index){
		
		if( getButtons()[index].getIcon() == box){
			getButtons()[index].setIcon(flag);
		}else if( getButtons()[index].getIcon() == flag){
			getButtons()[index].setIcon(questionMarkIcon);
		}else if( getButtons()[index].getIcon() == questionMarkIcon){
			getButtons()[index].setIcon(box);
		}
	}
}
