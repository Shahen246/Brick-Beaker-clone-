package com.main.bricks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paddle {

	private double x, y;
	private double velX = 0;
	private double velY = 0;
	
	//paddle size
	private int width, height; 
	
	public final int YPOS = Game.HEIGHT - 100;

	
	public Paddle() {
		
		width = 100;
		height = 15;
		x = Game.WIDTH / 2 - width / 2;
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		if(x < 0) {
			x = 0;
		}
		
		if(x > 535) {
			x = 535;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)x, YPOS, width, height);
	}
	
	public int getPaddleWidth() {
		return width;
	}
	
	public void setPaddleWidth(int width) {
		this.width = width;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public double getVelX() {
		return velX;
	}
	
	public void setVelX(int velX) {
		this.velX = velX;
	}
	
	public int getWidth() {
		return width;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, YPOS, width, height);
	}
}
