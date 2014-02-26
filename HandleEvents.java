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
            // debugging System.out.println("Adjacent mines: " + getAdjacencies()[index]);

            switch(getAdjacencies()[index]){
                case 0: getButtons()[index].setIcon(zero); break;
                case 1: getButtons()[index].setIcon(one); break;
                case 2: getButtons()[index].setIcon(two); break;
                case 3: getButtons()[index].setIcon(three); break;
                case 4: getButtons()[index].setIcon(four); break;
                case 5: getButtons()[index].setIcon(five); break;
                case 6: getButtons()[index].setIcon(six); break;
                case 7: getButtons()[index].setIcon(seven); break;
                case 8: getButtons()[index].setIcon(eight); break;
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
