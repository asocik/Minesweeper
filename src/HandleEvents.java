/*----------------------------------------------------------------------------
 * Minesweeper
 *
 * Class: CS 342 Software Design
 *
 * Created by Alex Schlake
 * February 2014
 ----------------------------------------------------------------------------*/
/*
 * This class handles the left and right mouse click events, as well as the
 * recursive zero tile checking.
 *
 */

import javax.swing.*;
import java.io.IOException;

public class HandleEvents extends MinesweeperGrid {

	private static final long serialVersionUID = 1L;

	public HandleEvents(int index) throws IOException {
		super();
	}

    /**------------------------------------------------------------------------
     * This method handles the events when the left mouse button is clicked.
     * First it checks if the game has not yet been started, if it
     * hasn't, the timer is started. If the tile is marked with a flag or a
     * question mark, nothing happens. Otherwise, it checks if the tile
     * contains a mine and ends the game if it does. If it is not a mine,
     * then it is a tile with the numerical mine adjacency value, and the
     * proper value is set on the tile. If it is a zero, a method is called
     * to find all adjacent zero tiles and clear them. Finally if after
     * left clicking, the number of total tiles cleared is 90, the game
     * is over and the user wins.
     * ------------------------------------------------------------------------*/
	public static void leftClick(int index) throws IOException{

        if( getStartToggle() == 0){
            getTimer().start();
        }
		if( (getButtons()[index].getIcon() == flag) || getButtons()[index].getIcon() == questionMarkIcon){
			// do nothing
		}else{
			// check if the tile clicked is one of the mines
			for(int i = 0; i < getMines().length; i++){
				if( index == getMines()[i]){
                    getTimer().stop();
                    JOptionPane.showMessageDialog(null, "BOOM! You lost!", "Game Over", EXIT_ON_CLOSE);
                    showMines();
                    gameover(false);
                    return;
				}
			}
            if( getButtons()[index].getIcon() == box){
                incTotalCleared();
            }
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
            if(getTotalCleared() == 90){
                getTimer().stop();
                zeroMines();
                JOptionPane.showMessageDialog(null, "You win!", "Game Over", EXIT_ON_CLOSE);
                gameover(true);
            }
        }
	}

    /**------------------------------------------------------------------------
     * This method handles the events when the right mouse button is clicked.
     * If the icon is the blank box, it changes it to the flag and decrements
     * the displayed number of mines. If it is a flag, it changes it to a
     * question mark and increments the displayed number of mines. If
     * the icon is the question mark, it changes it back to the blank box.
     * ------------------------------------------------------------------------*/
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

    /**------------------------------------------------------------------------
     * This method calls various methods to check the positions around the
     * clicked tile for other tiles able to be cleared. The tile's position
     * on the board (far left columns, middle columns, far right column) determines which surrounding tiles will be checked.
     * ------------------------------------------------------------------------*/
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

    /**------------------------------------------------------------------------
     * This method checks the tile position above the initial tile.
     * ------------------------------------------------------------------------*/
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

    /**------------------------------------------------------------------------
     * This method checks the tile position above and left of the initial tile.
     * ------------------------------------------------------------------------*/
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

    /**------------------------------------------------------------------------
     * This method checks the tile position above and right of the initial tile.
     * ------------------------------------------------------------------------*/
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

    /**------------------------------------------------------------------------
     * This method checks the tile position below the initial tile.
     * ------------------------------------------------------------------------*/
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

    /**------------------------------------------------------------------------
     * This method checks the tile position to the left of the initial tile.
     * ------------------------------------------------------------------------*/
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

    /**------------------------------------------------------------------------
     * This method checks the tile position to the right of the initial tile.
     * ------------------------------------------------------------------------*/
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

    /**------------------------------------------------------------------------
     * This method checks the tile position below and right of the initial tile.
     * ------------------------------------------------------------------------*/
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

    /**------------------------------------------------------------------------
     * This method checks the tile position below and left of the initial tile.
     * ------------------------------------------------------------------------*/
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

    /**------------------------------------------------------------------------
     * This changes the icon of the tile based upon it's index's value in
     * the mine adjacency array. It also increments the total number of
     * tiles cleared.
     * ------------------------------------------------------------------------*/
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

