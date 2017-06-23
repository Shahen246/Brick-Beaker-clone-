package com.main.bricks;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class HUD {

	private int score = 0;
	
	public HUD() {
	
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.white);
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.drawString("Score: " + score, 10, 15);
	}
	
	public int getScore() { return score; }
	public void setScore(int score) { this.score = score; }
	
	public void addScore(int add) {
		score += add;
	}
}

