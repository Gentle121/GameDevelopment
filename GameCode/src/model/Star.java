package model;

import java.awt.Color;
import java.awt.Graphics;

import util.PointUtil;

/**
 * 详见父类
 */
public class Star extends Fighter {

	private int[] starX = { 20, 25, 30, 40, 30, 25, 20, 10 };
	private int[] starY = { 20, 10, 20, 25, 30, 40, 30, 25 };

	public Star(int life, int price) {
		super(life, price);
	}

	public void draw(Graphics g) {
		if (ice) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.ORANGE);
		}
		g.fillPolygon(PointUtil.addToArray(y, starX), PointUtil.addToArray(x,
				starY), 8);
		drawLifeStatus(g, x, y);
	}

}
