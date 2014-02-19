import javax.swing.JFrame;

public class Main extends JFrame
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) 
	{
		JFrame window = new MinesweeperGrid();
		window.setTitle("Mineswepper");
		window.setVisible(true);
		window.setSize(425, 500);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
