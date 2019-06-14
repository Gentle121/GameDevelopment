package model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 详见父类
 */
public class Robot extends Fighter {

	public Robot(int life, int price) {
		super(life, price);
	}

	public void draw(Graphics g) {
		if (ice) {
			g.setColor(Color.blue);
		} else {
			g.setColor(new Color(149, 127, 206));
		}
		g.fillOval(y + 15, x + 10, 20, 20);
		g.setColor(new Color(26, 155, 138));
		g.fillRect(y + 15, x + 20, 20, 10);
		if (changeNum / 10 > 0) {
			g.fillRect(y + 18, x + 30, 2, 10);
			g.fillRect(y + 30, x + 30, 2, 10);
		} else {
			g.fillRect(y + 21, x + 30, 2, 10);
			g.fillRect(y + 27, x + 30, 2, 10);
		}
		drawLifeStatus(g, x, y);
	}

}
