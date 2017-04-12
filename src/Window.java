import javax.swing.JFrame;

public class Window extends JFrame{
	
	public static final int WIDTH = 700, HEIGHT = 600;
	
	public Window()
	{
		setTitle("Pong Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		GamePanel GP = new GamePanel();
		
		add(GP);
		addKeyListener(GP);
		setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		new Window();
	}
	
}
