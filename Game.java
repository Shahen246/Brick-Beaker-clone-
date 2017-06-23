package com.main.bricks;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 700;
	
	private boolean running = false;
	
	private Thread thread;
	
	//entities
	private Ball ball;
	private Paddle paddle;
	private Map theMap;
	private HUD hud;
	private PowerUp powerUp;
	
	public Game() {
		new Window(WIDTH, HEIGHT, "Bricks", this);
		
		init();
	}
	
	public void init() {
		setFocusable(true);
		addKeyListener(new KeyInput(this));
		ball = new Ball(paddle);
		paddle = new Paddle();
		theMap = new Map(6, 10);
		hud = new HUD();
		powerUp = new PowerUp();
	}
	
	public synchronized void start() {
		if(running)
			return;
			
			running = true;
			thread = new Thread(this);
			thread.start();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
			
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				updates++;
				delta--;
				frames++;
				
				if(System.currentTimeMillis() - timer > 1000) {
					timer += 1000;
					System.out.println(updates + " Ticks, FPS " + frames);
					updates = 0;
					frames = 0;
				}
			}
			render();
		}
		stop();
	}
	
	public void checkCollision() {
		
		Rectangle ballRect = ball.getBounds();
		Rectangle paddleRect = paddle.getBounds();
		
		if(ballRect.intersects(paddleRect)) {
			ball.setDY(-ball.getDY());
			
			if(ball.getX() < paddle.getX() + paddle.getWidth() / 4) {
				ball.setDX(ball.getDX() + .5);
			}
			
			if(ball.getY() < paddle.getX() + paddle.getWidth() && ball.getX() > paddle.getX() + paddle.getWidth()) {
				ball.setDY(ball.getDX() - .5);
			}
		}
		
		A: for(int row = 0; row < theMap.getMapArray().length; row++) {
			for(int col = 0; col < theMap.getMapArray()[0].length; col++) {
				
				if(theMap.getMapArray()[row][col] > 0 ) {
					int brickx = col * theMap.getBrickWidth() + theMap.HOR_PAD;
					int bricky = row * theMap.getBrickHeight() + theMap.VERT_PAD;
					int brickWidth = theMap.getBrickWidth();
					int brickHeight = theMap.getBrickHeight();
					
					Rectangle brickRect = new Rectangle(brickx, bricky, brickWidth, brickHeight);
					
					if(ballRect.intersects(brickRect)) {
						theMap.setBrick(row, col, 0);
						hud.addScore(5);
						
						//powerups
						powerUp.setDY(3);
						
						if(ball.getX() + 2 <= brickRect.x || ball.getX() + 5 >= brickRect.x + brickRect.width) {
						
							ball.setDX(-ball.getDX());
						
						} else {
						
							ball.setDY(-ball.getDY());
							
						}
						
						break A;
					}
				}
				
			}
		}
	}
		
	public void tick() {
		ball.tick();
		paddle.tick();
		powerUp.tick();
		
		checkCollision();
	}
	
	public void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		
		//background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//entites 
		ball.render(g);
		paddle.render(g);
		theMap.render((Graphics2D) g);
		hud.render(g);
		powerUp.render(g);
		
		//winner
		if(theMap.Winner() == true) {
			drawWin(g);
		}
		
		//loser
		if(ball.getY() > 700) {
			ball.setDY(0);
			ball.setDX(0);
			
			g.setColor(Color.black);
			g.setFont(new Font("Courier New", Font.BOLD, 60));
			g.drawString("You Lose!", 150, 200);
			
			g.setFont(new Font("Courier New", Font.BOLD, 20));
			g.drawString("Score: " + hud.getScore() , 250, 250);
			
			g.setFont(new Font("Courier New", Font.BOLD, 20));
			g.drawString("Press Enter to Restart!", 170, 280);
		}
		
		g.dispose();
		bs.show();
	}
	
	public void drawWin(Graphics g) {
		ball.setDX(0);
		ball.setDY(0);
		ball.setX(-25);
		ball.setY(-25);
		
		g.setColor(Color.white);
		g.setFont(new Font("Courier New", Font.BOLD, 60));
		g.drawString("You Win!", 200, 200);
		
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.drawString("Score: " + hud.getScore() , 250, 250);
		
	}	
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT) {
			paddle.setVelX(-5);
		}
		
		if(keyCode == KeyEvent.VK_RIGHT) {
			paddle.setVelX(5);
		}
		
		if(keyCode == KeyEvent.VK_SPACE) {
			ball.setDX(-3);
			ball.setDY(-3);
		}
		
		if(keyCode == KeyEvent.VK_ENTER) {
			ball.setX(5);
			ball.setY(5);
			ball.setDX(5);
			ball.setDY(5);
			hud.setScore(0);
			theMap = new Map(6, 10);
			
			repaint();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT) {
			paddle.setVelX(0);
		}
		
		if(keyCode == KeyEvent.VK_RIGHT) {
			paddle.setVelX(0);
		}
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
