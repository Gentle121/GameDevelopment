package model;

import java.awt.Color;
import java.awt.Graphics;

import util.PointUtil;

/**
 * 详见父类
 */
public class Blat extends Fighter {

	public Blat(int life, int price) {
		super(life, price);
		speed = 2;
	}

	private int blatX[] = { 10, 20, 20, 22, 28, 30, 30, 40, 35, 28, 25, 22, 15 };
	private int blatY[] = { 10, 20, 10, 15, 15, 10, 20, 10, 35, 30, 40, 30, 35 };
	private int blatX1[] = { 5, 20, 20, 22, 28, 30, 30, 45, 30, 28, 25, 22, 20 };
	private int blatY1[] = { 15, 20, 10, 15, 15, 10, 20, 15, 35, 30, 40, 30, 35 };

	public void draw(Graphics g) {
		if (ice) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.black);
		}
		if (changeNum / 10 > 0) {
			g.fillPolygon(PointUtil.addToArray(y, blatX), PointUtil.addToArray(
					x, blatY), 13);
		} else {
			g.fillPolygon(PointUtil.addToArray(y, blatX1), PointUtil
					.addToArray(x, blatY1), 13);
		}
		drawLifeStatus(g, x, y);
	}

}
