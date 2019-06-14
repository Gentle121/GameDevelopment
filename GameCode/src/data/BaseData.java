package data;

import java.util.ArrayList;
import java.util.List;

import model.Bullet;
import model.ElectricTower;
import model.Fighter;
import model.FireTower;
import model.IceTower;
import model.Tower;
import model.WoodTower;
import thread.AddFighterThread;
import thread.BulletThread;

/**
 * 数据控制类
 * @author yjm
 */
public class BaseData implements Runnable {

	/**
	 * 游戏区域距离窗体左侧的距离
	 */
	private int gameX;

	/**
	 * 游戏区域距离窗体顶部的距离
	 */
	private int gameY;

	/**
	 * 单位方格的边长
	 */
	private int squaresSize;

	/**
	 * 敌人数组
	 */
	private List<Fighter> fighterList;

	/**
	 * 塔数组
	 */
	private List<Tower> towerList;

	/**
	 * 子弹数组
	 */
	private List<Bullet> bulletList;

	/**
	 * 游戏总生命
	 */
	private int life;

	/**
	 * 游戏当前生命
	 */
	private int nowLife;

	/**
	 * 金钱
	 */
	private int money;

	/**
	 * 是否死亡
	 */
	private boolean dead;

	/**
	 * 当前波数
	 */
	private int round;

	/**
	 * 是否胜利
	 */
	private boolean win;

	/**
	 * 地图数组
	 */
	private int[][] squares = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1 },
			{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0 },
			{ 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	/**
	 * BaseData
	 */
	public BaseData() {
		init();
	}

	/**
	 * 初始化基本信息
	 */
	public void init() {
		fighterList = new ArrayList<Fighter>();
		towerList = new ArrayList<Tower>();
		bulletList = new ArrayList<Bullet>();
		gameX = Data.gameX;
		gameY = Data.gameY;
		life = 100;
		nowLife = 100;
		money = 300;
		round = 1;
		dead = false;
		squaresSize = Data.squaresSize;
		win = false;
		Thread addFighterThread = new Thread(new AddFighterThread(this));
		addFighterThread.start();
		Thread bulletThread = new Thread(new BulletThread(this));
		bulletThread.start();
		Thread lifeThread = new Thread(this);
		lifeThread.start();
	}

	/**
	 * 监控胜利与死亡的线程
	 */
	public void run() {
		try {
			while (true) {
				if (nowLife <= 0) {
					dead();
				}
				if (round == 100 && fighterList.size() == 0 && nowLife > 0) {
					win();
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  胜利后的清理工作
	 */
	private void win() {
		dead();
		win = true;
	}

	/**
	 * 死亡后的清理工作
	 */
	private void dead() {
		for (Tower tower : towerList) {
			tower.setLife(false);
		}
		towerList.clear();
		bulletList.clear();
		fighterList.clear();
		dead = true;
	}

	/**
	 * 重新开始
	 */
	public void restart() {
		nowLife = 100;
		money = 300;
		dead = false;
		win = false;
		round = 1;
	}

	/**
	 * 添加塔
	 * @param type 塔的类型
	 * @param x x坐标
	 * @param y y坐标
	 * @param size 居然没用到！！！忘了干什的了
	 */
	public void addTower(int type, int x, int y, int size) {
		Tower tower = null;
		if (type == 0) {
			tower = new IceTower(x, y, size, this);
		}
		if (type == 1) {
			tower = new FireTower(x, y, size, this);
		}
		if (type == 2) {
			tower = new ElectricTower(x, y, size, this);
		}
		if (type == 3) {
			tower = new WoodTower(x, y, size, this);
		}
		towerList.add(tower);
	}

	/**
	 * 添加子弹
	 */
	public void addBullet(Bullet bullet) {
		bulletList.add(bullet);
	}

	/**
	 * 敌人移动一次(放在这里是个失误)
	 * @param f 敌人
	 */
	public void getFuturePoint(Fighter f) {
		int x = (f.getX() - gameX) / squaresSize;
		int y = (f.getY() - gameY) / squaresSize;
		int dir = f.getDirection();
		if (dir == 1) {
			if (x - 1 >= 0 && y >= 0 && x - 1 < 8 && y < 14) {
				if (squares[x - 1][y] == 1) {
					f.setFutureX((x - 1) * squaresSize + gameX);
					f.setFutureY(y * squaresSize + gameY);
					f.setDirection(1);
				}
			}
			if (x >= 0 && y - 1 >= 0 && x < 8 && y - 1 < 14) {
				if (squares[x][y - 1] == 1) {
					f.setFutureX(x * squaresSize + gameX);
					f.setFutureY((y - 1) * squaresSize + gameY);
					f.setDirection(3);
				}
			}
			if (x >= 0 && y + 1 >= 0 && x < 8 && y + 1 < 14) {
				if (squares[x][y + 1] == 1) {
					f.setFutureX(x * squaresSize + gameX);
					f.setFutureY((y + 1) * squaresSize + gameY);
					f.setDirection(4);
				}
			}
		} else if (dir == 2) {
			if (x + 1 >= 0 && y >= 0 && x + 1 < 8 && y < 14) {
				if (squares[x + 1][y] == 1) {
					f.setFutureX((x + 1) * squaresSize + gameX);
					f.setFutureY(y * squaresSize + gameY);
					f.setDirection(2);
				}
			}
			if (x >= 0 && y - 1 >= 0 && x < 8 && y - 1 < 14) {
				if (squares[x][y - 1] == 1) {
					f.setFutureX(x * squaresSize + gameX);
					f.setFutureY((y - 1) * squaresSize + gameY);
					f.setDirection(3);
				}
			}
			if (x >= 0 && y + 1 >= 0 && x < 8 && y + 1 < 14) {
				if (squares[x][y + 1] == 1) {
					f.setFutureX(x * squaresSize + gameX);
					f.setFutureY((y + 1) * squaresSize + gameY);
					f.setDirection(4);
				}
			}
		} else if (dir == 3) {
			if (x - 1 >= 0 && y >= 0 && x - 1 < 8 && y < 14) {
				if (squares[x - 1][y] == 1) {
					f.setFutureX((x - 1) * squaresSize + gameX);
					f.setFutureY(y * squaresSize + gameY);
					f.setDirection(1);
				}
			}
			if (x + 1 >= 0 && y >= 0 && x + 1 < 8 && y < 14) {
				if (squares[x + 1][y] == 1) {
					f.setFutureX((x + 1) * squaresSize + gameX);
					f.setFutureY(y * squaresSize + gameY);
					f.setDirection(2);
				}
			}
			if (x >= 0 && y - 1 >= 0 && x < 8 && y - 1 < 14) {
				if (squares[x][y - 1] == 1) {
					f.setFutureX(x * squaresSize + gameX);
					f.setFutureY((y - 1) * squaresSize + gameY);
					f.setDirection(3);
				}
			}
		} else if (dir == 4) {
			if (x - 1 >= 0 && y >= 0 && x - 1 < 8 && y < 14) {
				if (squares[x - 1][y] == 1) {
					f.setFutureX((x - 1) * squaresSize + gameX);
					f.setFutureY(y * squaresSize + gameY);
					f.setDirection(1);
				}
			}
			if (x + 1 >= 0 && y >= 0 && x + 1 < 8 && y < 14) {
				if (squares[x + 1][y] == 1) {
					f.setFutureX((x + 1) * squaresSize + gameX);
					f.setFutureY(y * squaresSize + gameY);
					f.setDirection(2);
				}
			}
			if (x >= 0 && y + 1 >= 0 && x < 8 && y + 1 < 14) {
				if (squares[x][y + 1] == 1) {
					f.setFutureX(x * squaresSize + gameX);
					f.setFutureY((y + 1) * squaresSize + gameY);
					f.setDirection(4);
				}
			}
		}
	}

	/**
	 * @return the fighterList
	 */
	public List<Fighter> getFighterList() {
		return fighterList;
	}

	/**
	 * @param fighterList
	 *            the fighterList to set
	 */
	public void setFighterList(List<Fighter> fighterList) {
		this.fighterList = fighterList;
	}

	/**
	 * @return the squares
	 */
	public int[][] getSquares() {
		return squares;
	}

	/**
	 * @param squares
	 *            the squares to set
	 */
	public void setSquares(int[][] squares) {
		this.squares = squares;
	}

	/**
	 * @return the towerList
	 */
	public List<Tower> getTowerList() {
		return towerList;
	}

	/**
	 * @param towerList
	 *            the towerList to set
	 */
	public void setTowerList(List<Tower> towerList) {
		this.towerList = towerList;
	}

	/**
	 * @return the bulletList
	 */
	public List<Bullet> getBulletList() {
		return bulletList;
	}

	/**
	 * @param bulletList
	 *            the bulletList to set
	 */
	public void setBulletList(List<Bullet> bulletList) {
		this.bulletList = bulletList;
	}

	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @param life
	 *            the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * @return the nowLife
	 */
	public int getNowLife() {
		return nowLife;
	}

	/**
	 * @param nowLife
	 *            the nowLife to set
	 */
	public void setNowLife(int nowLife) {
		this.nowLife = nowLife;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * @return the dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * @param dead
	 *            the dead to set
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * @return the round
	 */
	public int getRound() {
		return round;
	}

	/**
	 * @param round
	 *            the round to set
	 */
	public void setRound(int round) {
		this.round = round;
	}

	/**
	 * @return the win
	 */
	public boolean isWin() {
		return win;
	}

	/**
	 * @param win
	 *            the win to set
	 */
	public void setWin(boolean win) {
		this.win = win;
	}

}
