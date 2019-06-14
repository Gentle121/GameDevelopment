package thread;

import model.Ball;
import model.Blat;
import model.Bug;
import model.Fighter;
import model.Person;
import model.Robot;
import model.Spider;
import model.Square;
import model.Star;
import model.Starfish;
import data.BaseData;

/**
 * 添加敌人线程(游戏的主要数值控制)
 * 
 * @author yjm
 */
public class AddFighterThread implements Runnable {

	/**
	 * 数据控制类引用
	 */
	private BaseData data;

	/**
	 * 当前波数
	 */
	private int round;

	/**
	 * 当前总生命
	 */
	private int life;

	/**
	 * 当前波敌人 的数量
	 */
	private int num;

	/**
	 * 当前波每个敌人的奖金
	 */
	private int money;

	/**
	 * 当前波的总奖金
	 */
	private int allMoney;

	/**
	 * 当前波敌人的总生命
	 */
	private int allLife;

	/**
	 * AddFighterThread
	 */
	public AddFighterThread(BaseData data) {
		this.data = data;
		init();
	}

	private void init() {
		life = 180;
		allLife = 1800;
		num = 10;
		money = 15;
		allMoney = 300;
	}

	/**
	 * 下一波
	 */
	private void nextRound() {
		if ((round + 2) % 10 == 0 && round != 0) {
			num = 1;
			money = 15 + round * 5;
		} else {
			if (round / 70 > 0) {
				num = 20;
			} else if (round / 40 > 0) {
				num = 15;
			} else {
				num = 10;
			}
			money = 15 + round / 10 * 5;
		}
		allMoney = allMoney + num * money;
		allLife = allMoney / 4 * 25;
		if (num == 1) {
			life = allLife / 2;
		} else {
			life = allLife / num;
		}
	}

	/**
	 * 添加敌人线程
	 */
	public void run() {
		try {
			Thread.sleep(5000);
			while (round < 100) {
				while (data.isDead()) {
					Thread.sleep(1000);
					round = 0;
				}
				data.setRound(round + 1);
				if (round % 9 == 0) {
					addFighter(new Ball(life, money), num);
				} else if (round % 9 == 1) {
					addFighter(new Square(life, money), num);
				} else if (round % 9 == 2) {
					addFighter(new Star(life, money), num);
				} else if (round % 9 == 3) {
					addFighter(new Person(life, money), num);
				} else if (round % 9 == 4) {
					addFighter(new Bug(life, money), num);
				} else if (round % 9 == 5) {
					addFighter(new Spider(life, money), num);
				} else if (round % 9 == 6) {
					addFighter(new Starfish(life, money), num);
				} else if (round % 9 == 7) {
					addFighter(new Robot(life, money), num);
				} else if (round % 9 == 8) {
					addFighter(new Blat(life, money), num);
				}
				nextRound();
				while (true) {
					if (data.getFighterList().size() == 0 || data.isDead()) {
						if (data.isDead()) {
							init();
							round = -1;
						}
						break;
					}
					Thread.sleep(1000);
				}
				if (!data.isDead()) {
					Thread.sleep(5000);
				}
				round++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加敌人
	 */
	private void addFighter(Fighter fighter, int num) {
		try {
			if (num == 1) {
				fighter.setBoss(true);
				data.getFighterList().add(fighter.clone());
			} else {
				for (int i = 0; i < num; i++) {
					if (data.isDead()) {
						round = -1;
						break;
					}
					data.getFighterList().add(fighter.clone());
					Thread.sleep(2000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the data
	 */
	public BaseData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(BaseData data) {
		this.data = data;
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

}
