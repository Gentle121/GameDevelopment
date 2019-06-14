package model;

import java.awt.Color;
import java.awt.Graphics;

import util.PointUtil;

/**
 * 详见父类
 */
public class Spider extends Fighter {

	public Spider(int life, int price) {
		super(life, price);
		speed = 2;
	}

	private int[] headX = { 20, 22, 28, 30 };
	private int[] headY = { 27, 22, 22, 27 };
	private int[] spiderX = { 10, 15, 20, 30, 35, 40, 35, 30, 20, 15 };
	private int[] spiderY = { 30, 17, 27, 27, 17, 30, 20, 30, 30, 20 };
	private int[] spiderX1 = { 0, 10, 20, 30, 40, 50, 40, 30, 20, 10 };
	private int[] spiderY1 = { 30, 22, 27, 27, 22, 30, 25, 30, 30, 25 };

	public void draw(Graphics g) {
		if (ice) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.pink);
		}
		g.fillPolygon(PointUtil.addToArray(y, headX), PointUtil.addToArray(x,
				headY), 4);
		g.setColor(Color.white);
		if (changeNum / 10 > 0) {
			g.fillPolygon(PointUtil.addToArray(y, spiderX), PointUtil
					.addToArray(x, spiderY), 10);
		} else {
			g.fillPolygon(PointUtil.addToArray(y, spiderX1), PointUtil
					.addToArray(x, spiderY1), 10);
		}
		drawLifeStatus(g, x, y);
	}

}
