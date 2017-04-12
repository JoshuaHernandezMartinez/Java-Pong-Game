import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener{
	
	// paddles
	
	private int paddleWidth = 120, paddleHeight = 10;
	private int paddleX = Window.WIDTH/2 - paddleWidth/2, paddleY = 550;
	private int deltaX = 0;
	private int paddleSpeed = 15;
	
	// IA paddle
	
	private int IAx = paddleX, IAy = 10;
	
	
	// ball
	
	private int ballRadius = 10;
	private int ballX = Window.WIDTH/2 - ballRadius, ballY = Window.HEIGHT/2 - ballRadius;
	private boolean ballMoving = false;
	private double velX, velY;
	private double angle;
	private double ballSpeed = 15;
	
	// score
	
	private int playerScore = 0, IAScore = 0;
	
	// repaint
	
	private Timer looper = new Timer(1000/60, new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			update();
			repaint();
		}
		
	});
	
	public GamePanel()
	{
		
		setIInitialDir();
		
		
		looper.start();
	}
	
	private void setIInitialDir()
	{
		angle = Math.PI/2;
		
		velX = (Math.cos(angle)*ballSpeed);
		velY = (Math.sin(angle)*ballSpeed);
	}
	
	private void update()
	{
		paddleX += deltaX;
		
		if(ballMoving)
		{
			ballX += velX;
			ballY += velY;
			
			if(IAx + paddleWidth/2 < ballX + ballRadius)
				IAx += paddleSpeed - 5;
			if(IAx + paddleWidth/2 > ballX + ballRadius) // I keep hardcoding 
				IAx -= paddleSpeed - 5;
			
		}else
		{
			ballX = Window.WIDTH/2 - ballRadius;
			ballY = Window.HEIGHT/2 - ballRadius;
			
			setIInitialDir();
			
		}
		
		//player collision
		
		if(ballY  + ballRadius*2> paddleY && (ballX > paddleX && ballX < paddleX + paddleWidth))
		{
			angle = -(paddleWidth + paddleX - (ballX + ballRadius))*Math.toRadians(150)/paddleWidth;
			
			velX = Math.cos(angle)*ballSpeed;
			velY = Math.sin(angle)*ballSpeed;	
		}
		
		// IA collision
		
		if(ballY  < IAy + paddleHeight && (ballX > IAx && ballX < IAx + paddleWidth))
		{
			angle = (paddleWidth + IAx - (ballX + ballRadius))*Math.toRadians(150)/paddleWidth;
			
			velX = Math.cos(angle)*ballSpeed;
			velY = Math.sin(angle)*ballSpeed;	
		}
		
		// Game Over
		
		if(ballY  < 0)
		{
			ballMoving = false;
			playerScore ++;
		}
		if(ballY - ballRadius*2 > Window.HEIGHT)
		{
			ballMoving = false;
			IAScore ++;
		}
		
		if(ballX + ballRadius*2 > Window.WIDTH || ballX < 0)
			velX *= -1;
		
		
		if(paddleX + paddleWidth > Window.WIDTH)
			paddleX = Window.WIDTH - paddleWidth;
		if(paddleX < 0)
			paddleX = 0;
		
	}
	private Font font = new Font("Georgia", Font.BOLD, 20);
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		
		g.setColor(Color.WHITE);
		
		g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
		g.fillRect(IAx, IAy, paddleWidth, paddleHeight);
		
		g.fillOval(ballX, ballY, ballRadius*2, ballRadius*2);
		
		g.setFont(font);
		
		if(!ballMoving)
			g.drawString("Press Space To Play", 250, Window.HEIGHT/2 - 100); // hard code values 
		
		g.drawString(""+IAScore, Window.WIDTH/2 - 10, Window.HEIGHT/2 - 200);
		
		g.drawString(""+playerScore, Window.WIDTH/2 - 10, Window.HEIGHT/2 + 200);
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			deltaX = -paddleSpeed;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			deltaX = paddleSpeed;
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			ballMoving = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			deltaX = 0;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			deltaX = 0;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
