import javax.swing.JFrame;

public class Main extends JFrame
{
	public static void main(String[] args) 
	{
		JFrame window = new MinesweeperGrid();
		window.setTitle("Mineswepper");
		window.setVisible(true);
		window.setSize(500, 500);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
