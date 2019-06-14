package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import model.Bullet;
import model.Fighter;
import model.Tower;
import util.DrawBulletUtil;
import util.DrawTowerUtil;
import util.Point;
import util.TowerUtil;
import data.BaseData;
import data.Data;

/**
 * 窗体类
 * 
 * @author yjm
 */
public class BaseFrame extends JFrame implements MouseMotionListener,
		MouseListener, Runnable {

	private static final long serialVersionUID = 1L;

	/**
	 * 窗体宽
	 */
	private int w;

	/**
	 * 窗体高
	 */
	private int h;

	/**
	 * 游戏区域左上顶点x坐标
	 */
	private int gameX;

	/**
	 * 游戏区域左上顶点y坐标
	 */
	private int gameY;

	/**
	 * 游戏区域宽
	 */
	private int gameW;

	/**
	 * 游戏区域高
	 */
	private int gameH;

	/**
	 * 单位方格的大小
	 */
	private int squaresSize;

	/**
	 * 当前焦点单位方格x坐标
	 */
	private int focusX;

	/**
	 * 当前焦点单位方格y坐标
	 */
	private int focusY;

	/**
	 * .........
	 */
	public BaseData data;

	/**
	 * 鼠标是否在底部建塔工具栏上
	 */
	private boolean atTools;

	/**
	 * 是否为建塔模式（选择建塔工具栏后为true）
	 */
	private boolean drawTowerTools;

	/**
	 * 选中的塔的类型
	 */
	private int changeTowerType;

	/**
	 * 塔的类型
	 */
	private int towerType;

	/**
	 * 地图数组
	 */
	private int[][] squares;

	/**
	 * 建塔工具栏数组
	 */
	private List<Point> toolsList;

	/**
	 * 塔数组
	 */
	private List<Tower> towerList;

	/**
	 * 子弹数组
	 */
	private List<Bullet> bulletList;

	/**
	 * 升级工具栏x坐标
	 */
	private int upX;

	/**
	 * 升级工具栏y坐标
	 */
	private int upY;

	/**
	 * 是否升级塔
	 */
	private boolean up;

	/**
	 * 是否摧毁塔
	 */
	private boolean broken;

	/**
	 * 当前焦点塔
	 */
	private Tower focusTower;

	/**
	 * 是否绘制金钱（用以金钱不够时使金钱一闪一闪的）
	 */
	private boolean drawMoney;

	/**
	 * 鼠标坐标
	 */
	int x, y;

	/**
	 * BaseFrame
	 */
	public BaseFrame(BaseData data) {
		this.data = data;
		init();
		this.setBounds(100, 100, w, h);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.setTitle("YLB");
		this.setResizable(false);
	}

	private void init() {
		w = 800;
		h = 650;
		gameX = Data.gameX;
		gameY = Data.gameY;
		atTools = false;
		drawTowerTools = false;
		changeTowerType = -1;
		towerType = -1;
		up = false;
		broken = false;
		gameW = 700;
		gameH = 450;
		focusX = -100;
		focusY = -100;
		upX = -100;
		upY = -100;
		drawMoney = true;
		squaresSize = Data.squaresSize;
		squares = data.getSquares();
		towerList = data.getTowerList();
		bulletList = data.getBulletList();
		toolsList = new ArrayList<Point>();
		toolsList.add(new Point(gameX, gameY + squaresSize * 10));
		toolsList.add(new Point(gameX + squaresSize * 2, gameY + squaresSize
				* 10));
		toolsList.add(new Point(gameX + squaresSize * 4, gameY + squaresSize
				* 10));
		toolsList.add(new Point(gameX + squaresSize * 6, gameY + squaresSize
				* 10));
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * 绘制方法
	 */
	public void paint(Graphics gr) {
		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2 = image.getGraphics();
		drawBG1(g2);
		drawStreet(g2);
		// drawSquares(g2);
		drawFighter(g2);
		drawTowers(g2);
		drawBullet(g2);
		drawTools(g2);
		drawTowersTools(g2);
		if (focusY > gameY + 9 * squaresSize) {
			g2.setColor(Color.green);
			g2.drawRect(focusX, focusY, squaresSize, squaresSize);
		}
		drawBG2(g2);
		drawLife(g2);
		drawMoney(g2);
		drawUpOrDown(g2);
		drawDead(g2);
		gr.drawImage(image, 0, 0, this);
	}

	/**
	 * 绘制升级、摧毁工具栏
	 */
	private void drawUpOrDown(Graphics g2) {
		if (upX != -100 && upY != -100 && !drawTowerTools) {
			g2.setColor(Color.white);
			g2.fillRect(upX, upY, 50, 10);
			g2.setColor(Color.orange);
			if (up) {
				g2.fillRect(upX, upY, 25, 10);
			}
			if (broken) {
				g2.fillRect(upX + 25, upY, 25, 10);
			}
			g2.setColor(Color.black);
			g2.drawLine(upX + squaresSize / 2, upY, upX + squaresSize / 2,
					upY + 10);
			Font font = new Font("宋体", 5, 8);
			g2.setFont(font);
			if (focusTower.getLevel() < 6) {
				g2.drawString("up" + focusTower.getPrice(), upX, upY + 8);
			}
			g2.drawString(" down", upX + 25, upY + 8);
		}
	}

	/**
	 * 绘制你赢了或你输了标语
	 */
	private void drawDead(Graphics g) {
		if (data.isWin()) {
			Font font = new Font("宋体", 70, 70);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("you win!", 200, 200);
		} else if (data.isDead()) {
			Font font = new Font("宋体", 70, 70);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("you lose!", 200, 200);
		}
	}

	/**
	 * 绘制金钱
	 */
	private void drawMoney(Graphics g) {
		if (drawMoney) {
			Font font = new Font("宋体", 30, 30);
			g.setFont(font);
			g.setColor(Color.black);
			g.drawString("$" + data.getMoney(), gameX + 12 * squaresSize, gameY
					+ 11 * squaresSize - 10);
		}
		Font font = new Font("宋体", 30, 30);
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString("round" + data.getRound(), gameX + 9 * squaresSize - 25,
				gameY + 11 * squaresSize - 10);
	}

	/**
	 * 绘制总生命
	 */
	private void drawLife(Graphics g2) {
		int lifeLength = squaresSize * 14;
		g2.setColor(Color.green);
		g2.fillRect(gameX, gameY + squaresSize * 9 + 10, squaresSize * 14, 20);
		int nowLifeLength = (int) (lifeLength * ((double) (data.getLife() - data
				.getNowLife()) / data.getLife()));
		g2.setColor(Color.red);
		g2.fillRect(gameX + lifeLength - nowLifeLength, gameY + squaresSize * 9
				+ 10, nowLifeLength, 20);
	}

	/**
	 * 绘制背景
	 */
	private void drawBG2(Graphics g2) {
		g2.setColor(Color.white);
		g2.fillRect(0, gameY * 8, squaresSize, squaresSize);
		g2.fillRect(gameX * 15, gameY * 2, squaresSize, squaresSize);
		g2.setColor(Color.white);
		g2.drawRect(gameX, gameY, squaresSize * 14, squaresSize * 9);
	}

	/**
	 * 绘制背景
	 */
	private void drawBG1(Graphics g2) {
		g2.setColor(Color.white);
		g2.fillRect(0, 0, w, h);
		g2.setColor(Color.black);
		g2.fillRect(gameX, gameY, gameX + squaresSize * 13, gameY + squaresSize
				* 8);
	}

	/**
	 * 绘制子弹
	 */
	private void drawBullet(Graphics g2) {
		for (int i = 0; i < bulletList.size(); i++) {
			Bullet bullet = bulletList.get(i);
			if (bullet != null) {
				DrawBulletUtil.drawBulletByType(bulletList.get(i), g2);
			}
		}
	}

	/**
	 * 绘制建塔工具栏价格
	 */
	private void drawTowersTools(Graphics g2) {
		Font font = new Font("宋体", 5, 8);
		g2.setFont(font);
		if (drawTowerTools && focusX >= gameX && focusX < gameX + gameW
				&& focusY >= gameY && focusY < gameY + gameH) {
			DrawTowerUtil.drawTowerByType(towerType, 1, g2, focusX, focusY,
					squaresSize);
		}
	}

	/**
	 * 绘制防御塔
	 */
	private void drawTowers(Graphics g2) {
		for (int i = 0; i < towerList.size(); i++) {
			Tower tower = towerList.get(i);
			if (tower != null) {
				DrawTowerUtil.drawTowerByType(tower.getType(),
						tower.getLevel(), g2, tower.getX(), tower.getY(),
						squaresSize);
				int towerLevelX = tower.getX() + 42;
				g2.setColor(Color.yellow);
				for (int j = 0; j < tower.getLevel(); j++) {
					g2.fillRect(towerLevelX, tower.getY() + 4 * j, 6, 3);
				}
				if (!tower.isEnable()) {
					DrawTowerUtil.drawTowerLife(g2, tower);
				}
			}
		}
	}

	/**
	 * 绘制建塔工具栏
	 */
	private void drawTools(Graphics g2) {
		for (int i = 0; i < toolsList.size(); i++) {
			DrawTowerUtil.drawTowerByType(i, 1, g2, toolsList.get(i).getX(),
					toolsList.get(i).getY(), squaresSize);
			g2.drawString("" + TowerUtil.getPriceByTowerType(i), toolsList.get(
					i).getX() + 13, toolsList.get(i).getY() + 60);
		}
	}

	/**
	 * 绘制敌人
	 * 
	 * @param g2
	 */
	private void drawFighter(Graphics g2) {
		List<Fighter> fighterList = data.getFighterList();
		for (int i = 0; i < fighterList.size(); i++) {
			Fighter fighter = fighterList.get(i);
			if (fighter != null) {
				fighter.draw(g2);
			}
		}
	}

	/**
	 * 绘制敌人
	 */
	public void drawSquares(Graphics g2) {
		g2.setColor(Color.white);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 14; j++) {
				g2.drawRect(j * squaresSize + gameX, i * squaresSize + gameY,
						squaresSize, squaresSize);
			}
		}
	}

	/**
	 * 绘制敌人移动的道路
	 */
	private void drawStreet(Graphics g2) {
		g2.setColor(Color.gray);
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				if (squares[i][j] == 1) {
					g2.fillRect(j * squaresSize + gameX, i * squaresSize
							+ gameY, squaresSize, squaresSize);
				}
			}
		}
	}

	/**
	 * 升级塔
	 */
	private void upTower(final Tower tower) {
		data.setMoney(data.getMoney() - tower.getPrice());
		tower.levelUp();
	}

	/**
	 * 摧毁塔
	 */
	private void downTower(Tower tower) {
		data.setMoney(data.getMoney() + tower.getPrice() * tower.getLevel());
		tower.setLife(false);
		data.getTowerList().remove(tower);
	}

	public void mouseDragged(MouseEvent e) {

	}

	/**
	 * 刷新图片的线程（游戏能动起来全靠它了）
	 */
	public void run() {
		try {
			while (true) {
				repaint();
				Thread.sleep(50);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 金钱一闪一闪控制器
	 */
	private void setDrawMoney() {
		if (drawMoney) {
			drawMoney = false;
		} else {
			drawMoney = true;
		}
	}

	/**
	 * 金钱一闪一闪控制线程(这个有点奢侈了，其实可以在其他线程里将就一下)
	 */
	private void noMoneyThread() {
		new Thread() {
			public void run() {
				try {
					for (int i = 0; i < 4; i++) {
						setDrawMoney();
						Thread.sleep(100);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 鼠标移动的相关操作
	 */
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		boolean canDrawTowerTools = true;
		for (Tower tower : towerList) {
			if (getSquerasX(x) == getSquerasX(tower.getX())
					&& getSquerasY(y) == getSquerasX(tower.getY())) {
				canDrawTowerTools = false;
				if (tower.isEnable()) {
					upX = getSquerasX(x);
					upY = getSquerasY(y);
					focusTower = tower;
				} else {
					upX = -100;
					upY = -100;
				}
				break;
			}
		}
		if (canDrawTowerTools) {
			upX = -100;
			upY = -100;
			up = false;
			broken = false;
		} else {
			if (x > upX && x < upX + 25 && y > upY && y < upY + 10
					&& focusTower.getLevel() < 6) {
				up = true;
			} else {
				up = false;
			}
			if (x > upX + 25 && x < upX + 50 && y > upY && y < upY + 10) {
				broken = true;
			} else {
				broken = false;
			}
		}
		if (canDrawTowerTools
				&& x > gameX
				&& x < gameX + gameW
				&& y > gameY
				&& y < gameY + gameH
				&& squares[(y - gameY) / squaresSize][(x - gameX) / squaresSize] != 1) {
			focusX = getSquerasX(x);
			focusY = getSquerasX(y);
		} else {
			atTools = false;
			for (int i = 0; i < toolsList.size(); i++) {
				Point point = toolsList.get(i);
				if (x > point.getX() && x < point.getX() + squaresSize
						&& y > point.getY() && y < point.getY() + squaresSize) {
					changeTowerType = i;
					focusX = point.getX();
					focusY = point.getY();
					atTools = true;
				}
			}
			if (!atTools) {
				focusX = -100;
				focusY = -100;
				changeTowerType = -1;
			}
		}
	}

	private int getSquerasY(int y) {
		return (y - gameY) / squaresSize * squaresSize + squaresSize;
	}

	private int getSquerasX(int x) {
		return (x - gameX) / squaresSize * squaresSize + squaresSize;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 鼠标按下相关操作
	 */
	public void mousePressed(MouseEvent e) {
		if (data.isDead()) {
			data.restart();
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (drawTowerTools) {
				drawTowerTools = false;
				towerType = -1;
			}
		} else {
			if (changeTowerType != -1) {
				if (TowerUtil.getPriceByTowerType(changeTowerType) <= data
						.getMoney()) {
					drawTowerTools = true;
					towerType = changeTowerType;
				} else {
					noMoneyThread();
				}
			} else if (drawTowerTools && focusX != -100 && focusY != -100) {
				data.setMoney(data.getMoney()
						- TowerUtil.getPriceByTowerType(towerType));
				new Thread() {
					public void run() {
						data.addTower(towerType, focusX, focusY, squaresSize);
						drawTowerTools = false;
						towerType = -1;
					}
				}.start();
			}
			if (up && !drawTowerTools) {
				if (data.getMoney() >= TowerUtil.getPriceByTowerType(focusTower
						.getType())
						&& focusTower.getLevel() < 6) {
					upTower(focusTower);
					upX = -100;
					upY = -100;
					up = false;
				} else {
					if (focusTower.getLevel() == 6) {

					} else {
						noMoneyThread();
					}
				}
			}
			if (broken && !drawTowerTools) {
				downTower(focusTower);
				upX = -100;
				upY = -100;
				broken = false;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
