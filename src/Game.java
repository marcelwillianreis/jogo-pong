import java.awt.Canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{

	
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 160;
	public static int HEIGHT = 120;
	public static int SCALE = 3;
	private boolean isRunning = true;
	public static Player player;
	public static Enemy enemy;
	public static Ball ball;
	
	public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public Game(){
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		this.addKeyListener(this);
		player  = new Player(100, HEIGHT-5);
		enemy = new Enemy(100, 0);
		ball = new Ball(100,HEIGHT/2-1);
	}
		
	public static void main(String[] args) {
		Game game = new Game();
		game.start();		
		JFrame frame = new JFrame("Pong");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		new Thread(game).start();		
	}
	
	public void tick () 
	{
		player.tick();
		enemy.tick();
		ball.tick();
	}
	
	public void render () {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = layer.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		player.render(g);
		enemy.render(g);
		ball.render(g);
		
		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
			
		bs.show();
	}
	
	public synchronized void start() {
		
	}
	
	public synchronized void stop() {
		
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		int Frames = 0;
		double delta = 0;
		long timer = System.currentTimeMillis();		
		while(isRunning)
		{	
			long now = System.nanoTime();
			delta+=(now-lastTime)/ns;
			lastTime = now;			

			if(delta >= 1) 
			{
				System.out.println("teste");
				tick();
				render();
				Frames++;
				delta--;
			}
			
			//if (System.currentTimeMillis() - timer >= 1000)
			//{
			//	Frames = 0;
			//	timer += 1000;
			//}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			player.left = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			player.left = false;
		}		
	}

}
