package model;

import java.awt.Color;
import java.awt.Graphics;

import util.PointUtil;

/**
 * 详见父类
 */
public class Bug extends Fighter {

	public Bug(int life, int price) {
		super(life, price);
	}

	int[] bugX = { 15, 20, 30, 40, 30, 30, 35, 30, 20, 10, 20, 20 };
	int[] bugY = { 15, 20, 20, 20, 23, 27, 35, 30, 30, 30, 27, 23 };
	int[] bugX1 = { 10, 20, 30, 35, 30, 30, 40, 30, 20, 15, 20, 20 };
	int[] bugY1 = { 20, 20, 20, 15, 23, 27, 30, 30, 30, 35, 27, 23 };

	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		if (changeNum / 10 > 0) {
			g.fillPolygon(PointUtil.addToArray(y, bugX), PointUtil.addToArray(
					x, bugY), 12);
		} else {
			g.fillPolygon(PointUtil.addToArray(y, bugX1), PointUtil.addToArray(
					x, bugY1), 12);
		}
		if (ice) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.green);
		}
		g.fillRect(20 + y, 20 + x, 10, 10);
		g.fillOval(20 + y, 25 + x, 10, 10);
		g.fillOval(20 + y, 15 + x, 10, 10);
		drawLifeStatus(g, x, y);
	}

}
