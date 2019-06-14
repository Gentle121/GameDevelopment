package model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 详见父类
 */
public class Person extends Fighter {

	public Person(int life,int price) {
		super(life,price);
	}

	public void draw(Graphics g) {
		if (ice) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.black);
		}
		if (changeNum / 10 > 0) {
			g.fillOval(20 + y, 10 + x, 10, 10);
			g.drawLine(25 + y, 20 + x, 25 + y, 30 + x);
			g.drawLine(25 + y, 30 + x, 20 + y, 40 + x);
			g.drawLine(25 + y, 30 + x, 30 + y, 40 + x);
			g.drawLine(17 + y, 24 + x, 33 + y, 24 + x);
		} else {
			g.fillOval(20 + y, 10 + x, 10, 10);
			g.drawLine(25 + y, 20 + x, 25 + y, 30 + x);
			g.drawLine(25 + y, 30 + x, 17 + y, 40 + x);
			g.drawLine(25 + y, 30 + x, 33 + y, 40 + x);
			g.drawLine(17 + y, 27 + x, 25 + y, 24 + x);
			g.drawLine(33 + y, 27 + x, 25 + y, 24 + x);
		}
		drawLifeStatus(g, x, y);
	}

}
