package com.main.bricks;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Map {

	private int[][] theMap;
	private int brickHeight, brickWidth;
	
	public final int HOR_PAD = 70, VERT_PAD = 70;
	
	
	public Map(int row, int col) {
		
		initMap(row, col);
		
		brickWidth = (Game.WIDTH - 2 * HOR_PAD) / col;
		brickHeight = (Game.HEIGHT / 4 - VERT_PAD) / row;
		
	}
	
	public boolean Winner() {
		boolean isWinner = false;
		
		int bricksRemaining = 0;
		
		for(int row = 0; row < theMap.length; row++) {
			for(int col = 0; col < theMap[0].length; col++) {
				bricksRemaining += theMap[row][col];
			}
		}
		
		if(bricksRemaining == 0) {
			isWinner = true;
		}
		return isWinner;
	}
	
	public void initMap(int row, int col) {
		
		theMap = new int[row][col];
		
		for(int i = 0; i < theMap.length; i++) {
			for(int j = 0; j < theMap[0].length; j++) {
				theMap[i][j] = 1;
			}
		}
		
	}
	
	public void render(Graphics2D g) {
		
		for(int row = 0; row < theMap.length; row++){
			for(int col = 0; col < theMap[0].length; col++){
				
				if(theMap[row][col] > 0) {
					g.setColor(Color.WHITE);
					g.fillRect(col * brickWidth + HOR_PAD, row * brickHeight + VERT_PAD, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.GREEN);
					g.drawRect(col * brickWidth + HOR_PAD, row * brickHeight + VERT_PAD, brickWidth, brickHeight);
				}
			}
		}
	}
	
	public int[][] getMapArray() { return theMap; }
	
	public void setBrick(int row, int col, int value) {
		theMap[row][col] = value;
	}
	
	public int getBrickWidth() { return brickWidth; }
	public int getBrickHeight() { return brickHeight; }
}
