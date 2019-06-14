package util;

import java.awt.Color;
import java.awt.Graphics;

import model.Bullet;

/**
 * 绘制子弹工具类
 * 
 * @author yjm
 */
public class DrawBulletUtil {

	/**
	 * 绘制子弹by类型
	 */
	public static void drawBulletByType(Bullet bullet, Graphics g2) {
		int type = bullet.getType();
		int x = (int) bullet.getX();
		int y = (int) bullet.getY();
		if (type == 0) {
			g2.setColor(Color.blue);
			g2.fillOval(x, y, 5, 5);
		}
		if (type == 1) {
			g2.setColor(Color.red);
			g2.fillOval(x, y, 5, 5);
		}
		if (type == 2) {
			g2.setColor(Color.yellow);
			drawElectricLines(bullet.getX(), bullet.getY(), bullet.getFighter()
					.getY() + 25, bullet.getFighter().getX() + 25, g2);
			g2.setColor(Color.white);
			drawElectricLines(bullet.getX(), bullet.getY(), bullet.getFighter()
					.getY() + 25, bullet.getFighter().getX() + 25, g2);
		}
		if (type == 3) {
			g2.setColor(Color.green);
			g2.fillOval(x, y, 5, 5);
		}
	}

	/**
	 * 绘制闪电
	 */
	private static void drawElectricLines(int x1, int y1, int x2, int y2,
			Graphics g) {
		double num1 = (double) 1 / 6;
		double num2 = (double) 2 / 6;
		double num3 = (double) 3 / 6;
		double num4 = (double) 4 / 6;
		double num5 = (double) 5 / 6;
		int randomNum = 10;
		int x3 = (int) (x1 + (x2 - x1) * num1)
				+ (int) (Math.random() * randomNum);
		int y3 = (int) (y1 + (y2 - y1) * num1)
				+ (int) (Math.random() * randomNum);
		int x4 = (int) (x1 + (x2 - x1) * num2)
				+ (int) (Math.random() * randomNum);
		int y4 = (int) (y1 + (y2 - y1) * num2)
				+ (int) (Math.random() * randomNum);
		int x5 = (int) (x1 + (x2 - x1) * num3)
				+ (int) (Math.random() * randomNum);
		int y5 = (int) (y1 + (y2 - y1) * num3)
				+ (int) (Math.random() * randomNum);
		int x6 = (int) (x1 + (x2 - x1) * num4)
				+ (int) (Math.random() * randomNum);
		int y6 = (int) (y1 + (y2 - y1) * num4)
				+ (int) (Math.random() * randomNum);
		int x7 = (int) (x1 + (x2 - x1) * num5)
				+ (int) (Math.random() * randomNum);
		int y7 = (int) (y1 + (y2 - y1) * num5)
				+ (int) (Math.random() * randomNum);
		g.drawLine(x1, y1, x3, y3);
		g.drawLine(x3, y3, x4, y4);
		g.drawLine(x4, y4, x5, y5);
		g.drawLine(x5, y5, x6, y6);
		g.drawLine(x6, y6, x7, y7);
		g.drawLine(x7, y7, x2, y2);
	}

}
