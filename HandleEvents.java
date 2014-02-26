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
					// debugging System.out.println("Explosion!");
					gameover();
				}
			}
            // debugging System.out.println("Adjacent mines: " + getAdjacencies()[index]);

            switch(getAdjacencies()[index]){
                case 0: getButtons()[index].setIcon(zero);
                        zeroChecks(index);
                        break;
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

    public static void zeroChecks(int index){
        if( index % 10 == 0 ){
            checkUp(index);
            checkUpRight(index);
            checkRight(index);
            checkDown(index);
            checkDownRight(index);
        }else if ((index+1) % 10 == 0){
            checkUpLeft(index);
            checkUp(index);
            checkLeft(index);
            checkDownLeft(index);
            checkDown(index);
        }else{
            checkUpLeft(index);
            checkUp(index);
            checkUpRight(index);
            checkLeft(index);
            checkRight(index);
            checkDownLeft(index);
            checkDown(index);
            checkDownRight(index);
        }
    }

    public static void checkUp(int index){
        if(index-10 > -1){
            if(index < 0 || getButtons()[index-10].getIcon() == zero)
                return;
            if(  getAdjacencies()[index-10] == 0){
                getButtons()[index-10].setIcon(zero);
                zeroChecks(index-10);
            }else{
                switch(getAdjacencies()[index-10]){
                    case 1: getButtons()[index-10].setIcon(one); break;
                    case 2: getButtons()[index-10].setIcon(two); break;
                    case 3: getButtons()[index-10].setIcon(three); break;
                    case 4: getButtons()[index-10].setIcon(four); break;
                    case 5: getButtons()[index-10].setIcon(five); break;
                    case 6: getButtons()[index-10].setIcon(six); break;
                    case 7: getButtons()[index-10].setIcon(seven); break;
                    case 8: getButtons()[index-10].setIcon(eight); break;
                }
            }
        }
    }
    public static void checkUpLeft(int index){
        if(index-11 > -1){
            if(index < 0 || getButtons()[index-11].getIcon() == zero)
                return;
            if( getAdjacencies()[index-11] == 0){
                getButtons()[index-11].setIcon(zero);
                zeroChecks(index-11);
            }else{
                switch(getAdjacencies()[index-11]){
                    case 1: getButtons()[index-11].setIcon(one); break;
                    case 2: getButtons()[index-11].setIcon(two); break;
                    case 3: getButtons()[index-11].setIcon(three); break;
                    case 4: getButtons()[index-11].setIcon(four); break;
                    case 5: getButtons()[index-11].setIcon(five); break;
                    case 6: getButtons()[index-11].setIcon(six); break;
                    case 7: getButtons()[index-11].setIcon(seven); break;
                    case 8: getButtons()[index-11].setIcon(eight); break;
                }
            }
        }
    }
    public static void checkUpRight(int index){
        if(index-9 > -1){
            if(index < 0 || getButtons()[index-9].getIcon() == zero)
                return;
            if( getAdjacencies()[index-9] == 0){
                getButtons()[index-9].setIcon(zero);
                zeroChecks(index-9);
            }else{
                switch(getAdjacencies()[index-9]){
                    case 1: getButtons()[index-9].setIcon(one); break;
                    case 2: getButtons()[index-9].setIcon(two); break;
                    case 3: getButtons()[index-9].setIcon(three); break;
                    case 4: getButtons()[index-9].setIcon(four); break;
                    case 5: getButtons()[index-9].setIcon(five); break;
                    case 6: getButtons()[index-9].setIcon(six); break;
                    case 7: getButtons()[index-9].setIcon(seven); break;
                    case 8: getButtons()[index-9].setIcon(eight); break;
                }
            }
        }
    }
    public static void checkDown(int index){
        if(index+10 < 100){
            if(index > 99 || getButtons()[index+10].getIcon() == zero)
                return;
            if( getAdjacencies()[index+10] == 0){
                getButtons()[index+10].setIcon(zero);
                zeroChecks(index+10);
            }else{
                switch(getAdjacencies()[index+10]){
                    case 1: getButtons()[index+10].setIcon(one); break;
                    case 2: getButtons()[index+10].setIcon(two); break;
                    case 3: getButtons()[index+10].setIcon(three); break;
                    case 4: getButtons()[index+10].setIcon(four); break;
                    case 5: getButtons()[index+10].setIcon(five); break;
                    case 6: getButtons()[index+10].setIcon(six); break;
                    case 7: getButtons()[index+10].setIcon(seven); break;
                    case 8: getButtons()[index+10].setIcon(eight); break;
                }
            }
        }
    }
    public static void checkLeft(int index){
        if(index-1 > -1){
            if(index < 0 || getButtons()[index-1].getIcon() == zero)
                return;
            if( getAdjacencies()[index-1] == 0){
                getButtons()[index-1].setIcon(zero);
                zeroChecks(index-1);
            }else{
                switch(getAdjacencies()[index-1]){
                    case 1: getButtons()[index-1].setIcon(one); break;
                    case 2: getButtons()[index-1].setIcon(two); break;
                    case 3: getButtons()[index-1].setIcon(three); break;
                    case 4: getButtons()[index-1].setIcon(four); break;
                    case 5: getButtons()[index-1].setIcon(five); break;
                    case 6: getButtons()[index-1].setIcon(six); break;
                    case 7: getButtons()[index-1].setIcon(seven); break;
                    case 8: getButtons()[index-1].setIcon(eight); break;
                }
            }
        }
    }
    public static void checkRight(int index){
        if(index+1 < 100){
            if(index > 99 || getButtons()[index+1].getIcon() == zero)
                return;
            if( getAdjacencies()[index+1] == 0){
                getButtons()[index+1].setIcon(zero);
                zeroChecks(index+1);
            }else{
                switch(getAdjacencies()[index+1]){
                    case 1: getButtons()[index+1].setIcon(one); break;
                    case 2: getButtons()[index+1].setIcon(two); break;
                    case 3: getButtons()[index+1].setIcon(three); break;
                    case 4: getButtons()[index+1].setIcon(four); break;
                    case 5: getButtons()[index+1].setIcon(five); break;
                    case 6: getButtons()[index+1].setIcon(six); break;
                    case 7: getButtons()[index+1].setIcon(seven); break;
                    case 8: getButtons()[index+1].setIcon(eight); break;
                }
            }
        }
    }
    public static void checkDownRight(int index){
        if(index+11 < 100){
            if(index > 99 || getButtons()[index+11].getIcon() == zero)
                return;
            if( getAdjacencies()[index+11] == 0){
                getButtons()[index+11].setIcon(zero);
                zeroChecks(index+11);
            }else{
                switch(getAdjacencies()[index+11]){
                    case 1: getButtons()[index+11].setIcon(one); break;
                    case 2: getButtons()[index+11].setIcon(two); break;
                    case 3: getButtons()[index+11].setIcon(three); break;
                    case 4: getButtons()[index+11].setIcon(four); break;
                    case 5: getButtons()[index+11].setIcon(five); break;
                    case 6: getButtons()[index+11].setIcon(six); break;
                    case 7: getButtons()[index+11].setIcon(seven); break;
                    case 8: getButtons()[index+11].setIcon(eight); break;
                }
            }
        }
    }
    public static void checkDownLeft(int index){
        if(index+9 < 100){
            if( index > 99 || getButtons()[index+9].getIcon() == zero)
                return;
            if( getAdjacencies()[index+9] == 0){
                getButtons()[index+9].setIcon(zero);
                zeroChecks(index+9);
            }else{
                switch(getAdjacencies()[index+9]){
                    case 1: getButtons()[index+9].setIcon(one); break;
                    case 2: getButtons()[index+9].setIcon(two); break;
                    case 3: getButtons()[index+9].setIcon(three); break;
                    case 4: getButtons()[index+9].setIcon(four); break;
                    case 5: getButtons()[index+9].setIcon(five); break;
                    case 6: getButtons()[index+9].setIcon(six); break;
                    case 7: getButtons()[index+9].setIcon(seven); break;
                    case 8: getButtons()[index+9].setIcon(eight); break;
                }
            }
        }
    }
}
