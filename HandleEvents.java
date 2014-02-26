import java.io.IOException;

public class HandleEvents extends MinesweeperGrid {

	private static final long serialVersionUID = 1L;

	public HandleEvents(int index) throws IOException {
		super();
	}
	
	public static void leftClick(int index) throws IOException{

        if( getStartToggle() == 0){
            getTimer().start();
        }
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
            incTotalCleared();
            // debugging System.out.println(getTotalCleared());
            if(getTotalCleared() == 90){
                System.out.println("Game over.");
                gameover();
            }
        }
	}
	
	public static void rightClick(int index){
		if( getButtons()[index].getIcon() == box){
			getButtons()[index].setIcon(flag);
            decMines();
		}else if( getButtons()[index].getIcon() == flag){
			getButtons()[index].setIcon(questionMarkIcon);
            incMines();
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
                incTotalCleared();
                zeroChecks(index-10);
            }else{
                changeNumericTile(index-10);
            }
        }
    }
    public static void checkUpLeft(int index){
        if(index-11 > -1){
            if(index < 0 || getButtons()[index-11].getIcon() == zero)
                return;
            if( getAdjacencies()[index-11] == 0){
                getButtons()[index-11].setIcon(zero);
                incTotalCleared();
                zeroChecks(index-11);
            }else{
                changeNumericTile(index-11);
            }
        }
    }
    public static void checkUpRight(int index){
        if(index-9 > -1){
            if(index < 0 || getButtons()[index-9].getIcon() == zero)
                return;
            if( getAdjacencies()[index-9] == 0){
                getButtons()[index-9].setIcon(zero);
                incTotalCleared();
                zeroChecks(index-9);
            }else{
                changeNumericTile(index-9);
            }
        }
    }
    public static void checkDown(int index){
        if(index+10 < 100){
            if(index > 99 || getButtons()[index+10].getIcon() == zero)
                return;
            if( getAdjacencies()[index+10] == 0){
                getButtons()[index+10].setIcon(zero);
                incTotalCleared();
                zeroChecks(index+10);
            }else{
                changeNumericTile(index+10);
            }
        }
    }
    public static void checkLeft(int index){
        if(index-1 > -1){
            if(index < 0 || getButtons()[index-1].getIcon() == zero)
                return;
            if( getAdjacencies()[index-1] == 0){
                getButtons()[index-1].setIcon(zero);
                incTotalCleared();
                zeroChecks(index-1);
            }else{
                changeNumericTile(index-1);
            }
        }
    }
    public static void checkRight(int index){
        if(index+1 < 100){
            if(index > 99 || getButtons()[index+1].getIcon() == zero)
                return;
            if( getAdjacencies()[index+1] == 0){
                getButtons()[index+1].setIcon(zero);
                incTotalCleared();
                zeroChecks(index+1);
            }else{
               changeNumericTile(index+1);
            }
        }
    }
    public static void checkDownRight(int index){
        if(index+11 < 100){
            if(index > 99 || getButtons()[index+11].getIcon() == zero)
                return;
            if( getAdjacencies()[index+11] == 0){
                getButtons()[index+11].setIcon(zero);
                incTotalCleared();
                zeroChecks(index+11);
            }else{
                changeNumericTile(index+11);
            }
        }
    }
    public static void checkDownLeft(int index){
        if(index+9 < 100){
            if( index > 99 || getButtons()[index+9].getIcon() == zero)
                return;
            if( getAdjacencies()[index+9] == 0){
                getButtons()[index+9].setIcon(zero);
                incTotalCleared();
                zeroChecks(index+9);
            }else{
                changeNumericTile(index+9);
            }
        }
    }

    public static void changeNumericTile(int index){
        switch(getAdjacencies()[index]){
            case 1:
                if(getButtons()[index].getIcon() != one){
                    getButtons()[index].setIcon(one);
                    incTotalCleared();
                }
                break;
            case 2:
                if(getButtons()[index].getIcon() != two){
                    getButtons()[index].setIcon(two);
                    incTotalCleared();
                }
                break;
            case 3:
                if(getButtons()[index].getIcon() != three){
                    getButtons()[index].setIcon(three);
                    incTotalCleared();
                }
                break;
            case 4:
                if(getButtons()[index].getIcon() != four){
                    getButtons()[index].setIcon(four);
                    incTotalCleared();
                }
                break;
            case 5:
                if(getButtons()[index].getIcon() != five){
                    getButtons()[index].setIcon(five);
                    incTotalCleared();
                }
                break;
            case 6:
                if(getButtons()[index].getIcon() != six){
                    getButtons()[index].setIcon(six);
                    incTotalCleared();
                }
                break;
            case 7:
                if(getButtons()[index].getIcon() != seven){
                    getButtons()[index].setIcon(seven);
                    incTotalCleared();
                }
                break;
            case 8:
                if(getButtons()[index].getIcon() != eight){
                    getButtons()[index].setIcon(eight);
                    incTotalCleared();
                }
                break;
        }
    }
}
