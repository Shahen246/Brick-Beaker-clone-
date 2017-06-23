package com.main.bricks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball {

	private double x, y, dx, dy;

	
	public Ball(Paddle paddle) {
		
		x = 350;
		y = 470;
	}
	
	public void tick() {
		x += dx;
		y += dy;
		
		if(x < 0) {
			dx =- dx;
		}
		
		if(y < 0) {
			dy =- dy;
		}
		
		if(x > Game.WIDTH - 16) {
			dx =- dx;
		}
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.YELLOW);
		g.fillOval((int)x, (int)y, 20, 20);
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 15, 15);
	}
	
	public double getDY() { return dy; }
	public void setDY(double dy) { this.dy = dy; }
	
	public double getDX() { return dx; }
	public void setDX(double dx) { this.dx = dx; }
	
	public double getX() { return x; }
	public void setX(double x) { this.x = x; } 
	
	public double getY() { return y; }
	public void setY(double y) { this.y = y; }
}
