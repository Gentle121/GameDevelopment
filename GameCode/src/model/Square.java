package model;

import java.awt.Color;
import java.awt.Graphics;

import data.Data;

/**
 * 详见父类
 */
public class Square extends Fighter {

	public Square(int life, int price) {
		super(life, price);
	}

	public void draw(Graphics g) {
		if (ice) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.ORANGE);
		}
		g
				.fillRect(y + 10, x + 10, Data.squaresSize - 20,
						Data.squaresSize - 20);
		drawLifeStatus(g, x, y);
	}

}
