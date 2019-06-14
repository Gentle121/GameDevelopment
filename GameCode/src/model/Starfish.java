package model;

import java.awt.Color;
import java.awt.Graphics;

import util.PointUtil;

/**
 * 详见父类
 */
public class Starfish extends Fighter {

	private int[] starX = { 20, 25, 30, 40, 30, 25, 20, 10 };
	private int[] starY = { 20, 10, 20, 25, 30, 40, 30, 25 };
	private int[] starX1 = { 10, 25, 40, 30, 40, 25, 10, 20 };
	private int[] starY1 = { 10, 20, 10, 25, 40, 30, 40, 25 };

	public Starfish(int life, int price) {
		super(life, price);
	}

	public void draw(Graphics g) {
		if (ice) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.ORANGE);
		}

		if (changeNum / 10 > 0) {
			g.fillPolygon(PointUtil.addToArray(y, starX), PointUtil.addToArray(
					x, starY), 8);
			g.fillPolygon(PointUtil.addToArray(y, starX1), PointUtil
					.addToArray(x, starY1), 8);
		} else {
			g.fillPolygon(PointUtil.addToArray(y, starX), PointUtil.addToArray(
					x, starY), 8);
		}
		drawLifeStatus(g, x, y);
	}
}
