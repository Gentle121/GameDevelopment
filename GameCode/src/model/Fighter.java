package model;

import java.awt.Color;
import java.awt.Graphics;

import data.Data;

/**
 * 敌人类
 * @author yjm
 */
public abstract class Fighter implements Cloneable {

	/**
	 * x坐标
	 */
	public int x = 0;

	/**
	 * y坐标
	 */
	public int y = 0;

	/**
	 * 下一次移动到的表格X坐标
	 */
	public int futureX;

	/**
	 * 下一次移动到的表格Y坐标
	 */
	public int futureY;

	/**
	 * 生命
	 */
	public int life;

	/**
	 * 当前生命
	 */
	public int nowLife;

	/**
	 * 移动速度
	 */
	public int speed;

	/**
	 * 是否减速状态
	 */
	public boolean ice;

	/**
	 * 减速状态剩余时间
	 */
	public int iceTime;

	/**
	 * 是否静止状态
	 */
	private boolean wood;

	/**
	 * 静止状态剩余时间
	 */
	private int woodTime;

	/**
	 * 是否持续伤害状态
	 */
	private boolean fire;

	/**
	 * 持续伤害状态剩余时间
	 */
	private int fireTime;

	/**
	 * 持续伤害状态伤害值
	 */
	private int firePower;

	/**
	 * 是否是boss（boss不受静止状态影响）
	 */
	private boolean boss;

	/**
	 * 用于绘制静止状态的多个绿色小三角
	 */
	private int[] woodStatusX = { 10, 15, 20, 25, 30, 35, 40 };

	/**
	 * 用于绘制静止状态的多个绿色小三角
	 */
	private int[] woodStatusY = { 40, 30, 40, 30, 40, 30, 40 };

	/**
	 * 移动方向
	 */
	public int direction = 4; // 上1下2左3右4

	/**
	 * 用于控制敌人图像变换的参数
	 */
	public int changeNum = 0;

	/**
	 * 用于控制绘制持续伤害红色小球变化的参数
	 */
	public int fireChangeNum = 0;

	/**
	 * 杀敌奖金
	 */
	private int price;

	/**
	 * Fighter
	 */
	public Fighter(int life, int price) {
		this.x = 7 * Data.squaresSize + Data.gameX;
		this.y = -1 * Data.squaresSize + Data.gameY;
		this.futureX = this.x;
		this.futureY = this.y;
		ice = false;
		iceTime = 0;
		wood = false;
		woodTime = 0;
		fire = false;
		fireTime = 0;
		firePower = 0;
		speed = 1;
		boss = false;
		this.life = life;
		this.nowLife = life;
		this.price = price;
	}

	/**
	 * 敌人绘画方法，由子类实现
	 * @param g
	 */
	public abstract void draw(Graphics g);

	/**
	 * 绘画敌人状态
	 */
	public void drawLifeStatus(Graphics g, int x, int y) {
		if (nowLife < 0) {
			nowLife = 0;
		}
		int redLength = (int) ((double) (life - nowLife) / life * 40);
		redLength = redLength > 0 ? redLength : 0;
		g.setColor(Color.green);
		g.fillRect(y + 5, x, Data.squaresSize - 10, 5);
		g.setColor(Color.red);
		g.fillRect(y + Data.squaresSize - redLength - 5, x, redLength, 5);
		if (wood) {
			g.setColor(Color.green);
			g.fillPolygon(getWoodStatusX(), getWoodStatusY(), 7);
		}
		if (fire) {
			g.setColor(Color.red);
			int fireNum = fireChangeNum / 10;
			if (fireNum == 1) {
				g.fillOval(y + 10, x + 10, 10, 10);
			} else {
				g.fillOval(y + 10, x + 10, 12, 12);
			}
		}
	}

	/**
	 * 根据的人坐标获取静止状态的绘画坐标
	 */
	private int[] getWoodStatusX() {
		int[] statusx = new int[7];
		for (int i = 0; i < 7; i++) {
			statusx[i] = woodStatusX[i] + y;
		}
		return statusx;
	}

	/**
	 * 根据的人坐标获取静止状态的绘画坐标
	 */
	private int[] getWoodStatusY() {
		int[] statusy = new int[7];
		for (int i = 0; i < 7; i++) {
			statusy[i] = woodStatusY[i] + x;
		}
		return statusy;
	}

	/**
	 * 移动方法
	 */
	public void move() {
		if (!wood || boss) {
			changeNum++;
			if (changeNum > 20) {
				changeNum = 0;
			}
		}
		fireChangeNum++;
		if (fireChangeNum > 20) {
			fireChangeNum = 0;
		}
		if (fire) {
			if (fireTime % 20 == 0) {
				nowLife -= firePower / 5;
			}
			fireTime--;
			if (fireTime <= 0) {
				fire = false;
				fireTime = 0;
				firePower = 0;
			}
		}
		if (wood && !boss) {
			woodTime--;
			if (woodTime <= 0) {
				woodTime = 0;
				wood = false;
			}
		} else if (ice) {
			if (iceTime % 2 == 0) {
				if (direction == 1) {
					x -= speed;
				}
				if (direction == 2) {
					x += speed;
				}
				if (direction == 3) {
					y -= speed;
				}
				if (direction == 4) {
					y += speed;
				}
			}
			iceTime--;
			if (iceTime <= 0) {
				iceTime = 0;
				ice = false;
			}
		} else {
			if (direction == 1) {
				x -= speed;
			}
			if (direction == 2) {
				x += speed;
			}
			if (direction == 3) {
				y -= speed;
			}
			if (direction == 4) {
				y += speed;
			}
		}
	}

	/**
	 * 获取敌人到指定坐标的距离，用于判定是否在塔的射程之内
	 */
	public int getDistance(int x, int y) {
		return (int) (Math.sqrt((this.x - y) * (this.x - y) + (this.y - x)
				* (this.y - x)));
	}

	/**
	 * 是否需要移动
	 */
	public boolean isNeedMove() {
		if (x != futureX || y != futureY) {
			return true;
		}
		return false;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
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
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @return the futureX
	 */
	public int getFutureX() {
		return futureX;
	}

	/**
	 * @param futureX
	 *            the futureX to set
	 */
	public void setFutureX(int futureX) {
		this.futureX = futureX;
	}

	/**
	 * @return the futureY
	 */
	public int getFutureY() {
		return futureY;
	}

	/**
	 * @param futureY
	 *            the futureY to set
	 */
	public void setFutureY(int futureY) {
		this.futureY = futureY;
	}

	/**
	 * @return the ice
	 */
	public boolean isIce() {
		return ice;
	}

	/**
	 * @param ice
	 *            the ice to set
	 */
	public void setIce(boolean ice) {
		this.ice = ice;
	}

	/**
	 * @return the iceTime
	 */
	public int getIceTime() {
		return iceTime;
	}

	/**
	 * @param iceTime
	 *            the iceTime to set
	 */
	public void setIceTime(int iceTime) {
		this.iceTime = iceTime;
	}

	/**
	 * @return the wood
	 */
	public boolean isWood() {
		return wood;
	}

	/**
	 * @param wood
	 *            the wood to set
	 */
	public void setWood(boolean wood) {
		this.wood = wood;
	}

	/**
	 * @return the woodTime
	 */
	public int getWoodTime() {
		return woodTime;
	}

	/**
	 * @param woodTime
	 *            the woodTime to set
	 */
	public void setWoodTime(int woodTime) {
		this.woodTime = woodTime;
	}

	/**
	 * @return the fire
	 */
	public boolean isFire() {
		return fire;
	}

	/**
	 * @param fire
	 *            the fire to set
	 */
	public void setFire(boolean fire) {
		this.fire = fire;
	}

	/**
	 * @return the fireTime
	 */
	public int getFireTime() {
		return fireTime;
	}

	/**
	 * @param fireTime
	 *            the fireTime to set
	 */
	public void setFireTime(int fireTime) {
		this.fireTime = fireTime;
	}

	/**
	 * @return the firePower
	 */
	public int getFirePower() {
		return firePower;
	}

	/**
	 * @param firePower
	 *            the firePower to set
	 */
	public void setFirePower(int firePower) {
		this.firePower = firePower;
	}

	public Fighter clone() throws CloneNotSupportedException {
		return (Fighter) super.clone();
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the boss
	 */
	public boolean isBoss() {
		return boss;
	}

	/**
	 * @param boss
	 *            the boss to set
	 */
	public void setBoss(boolean boss) {
		this.boss = boss;
		this.speed = 1;
	}

}
