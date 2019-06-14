package util;

import java.awt.Color;
import java.awt.Graphics;

import model.Tower;

/**
 * 绘制防御塔工具类
 * 
 * @author yjm
 */
public class DrawTowerUtil {

	/**
	 * 绘制防御塔，你懂的
	 */
	public static void drawTowerByType(int type, int level, Graphics g2, int x,
			int y, int size) {
		if (type == 0) {
			int[] x1 = { 10, 10, 40, 40 };
			int[] y1 = { 40, 35, 35, 40 };
			int[] x2 = { 15, 15, 35, 35 };
			int[] y2 = { 35, 10, 10, 35 };
			int[] x3 = { 25, 20, 25, 30, 25 };
			int[] y3 = { 10, 5, 0, 5, 10 };
			g2.setColor(Color.cyan);
			g2.fillPolygon(PointUtil.addToArray(x, x1), PointUtil.addToArray(y,
					y1), 4);
			g2.fillPolygon(PointUtil.addToArray(x, x3), PointUtil.addToArray(y,
					y3), 4);
			g2.setColor(Color.white);
			g2.fillPolygon(PointUtil.addToArray(x, x2), PointUtil.addToArray(y,
					y2), 4);
			g2.setColor(Color.black);
			g2.drawPolygon(PointUtil.addToArray(x, x1), PointUtil.addToArray(y,
					y1), 4);
			g2.drawPolygon(PointUtil.addToArray(x, x2), PointUtil.addToArray(y,
					y2), 4);
			g2.drawPolygon(PointUtil.addToArray(x, x3), PointUtil.addToArray(y,
					y3), 4);
		}
		if (type == 1) {
			int[] x1 = { 20, 10, 10, 20, 30, 40, 40, 30 };
			int[] y1 = { 30, 20, 10, 0, 0, 10, 20, 30 };
			g2.setColor(Color.white);
			g2.fillPolygon(PointUtil.addToArray(x, x1), PointUtil.addToArray(y,
					y1), 8);
			g2.fillRect(x + 20, y + 30, 10, 5);

			g2.setColor(Color.red);
			g2.fillRect(x + 15, y + 35, 20, 5);
			g2.fillRect(x + 20, y, 10, 10);
			g2.fillRect(x + 10, y + 10, 10, 10);
			g2.fillRect(x + 30, y + 10, 10, 10);
			g2.fillRect(x + 20, y + 20, 10, 10);
			g2.setColor(Color.black);
			g2.drawPolygon(PointUtil.addToArray(x, x1), PointUtil.addToArray(y,
					y1), 8);
			g2.drawRect(x + 20, y + 30, 10, 5);
			g2.drawRect(x + 15, y + 35, 20, 5);
			g2.drawRect(x + 20, y, 10, 10);
			g2.drawRect(x + 10, y + 10, 10, 10);
			g2.drawRect(x + 30, y + 10, 10, 10);
			g2.drawRect(x + 20, y + 20, 10, 10);
		}
		if (type == 2) {
			int[] x1 = { 10, 15, 35, 40 };
			int[] y1 = { 40, 35, 35, 40 };
			g2.setColor(Color.white);
			g2.fillRect(x + 24, y + 5, 3, 35);
			g2.setColor(Color.black);
			g2.drawRect(x + 24, y + 5, 3, 35);
			g2.setColor(Color.yellow);
			g2.fillPolygon(PointUtil.addToArray(x, x1), PointUtil.addToArray(y,
					y1), 4);
			g2.fillOval(x + 20, y, 10, 10);
			g2.setColor(Color.black);
			g2.drawPolygon(PointUtil.addToArray(x, x1), PointUtil.addToArray(y,
					y1), 4);
			g2.drawOval(x + 20, y, 10, 10);
		}
		if (type == 3) {
			int[] x1 = { 20, 10, 10, 20, 25, 30, 40, 40, 30 };
			int[] y1 = { 20, 10, 0, 10, 0, 10, 0, 10, 20 };
			g2.setColor(Color.white);
			g2.fillRect(x + 23, y + 20, 4, 16);
			g2.setColor(Color.green);
			g2.fillRect(x + 15, y + 36, 20, 4);
			g2.fillPolygon(PointUtil.addToArray(x, x1), PointUtil.addToArray(y,
					y1), 9);
			g2.setColor(Color.black);
			g2.drawPolygon(PointUtil.addToArray(x, x1), PointUtil.addToArray(y,
					y1), 9);
			g2.drawRect(x + 23, y + 20, 4, 16);
			g2.drawRect(x + 15, y + 36, 20, 4);
		}
	}

	/**
	 * 绘制防御塔的生命
	 */
	public static void drawTowerLife(Graphics g, Tower tower) {
		if (!tower.isEnable()) {
			g.setColor(Color.red);
			g.fillRect(tower.getX(), tower.getY(), 50, 5);
			g.setColor(Color.green);
			g.fillRect(tower.getX(), tower.getY(), tower.getBuildTime() / 2, 5);
		}
	}

}
