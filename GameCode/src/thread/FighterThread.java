package thread;

import java.util.List;

import model.Fighter;
import data.BaseData;
import data.Data;

/**
 * 敌人移动线程
 * 
 * @author yjm
 */
public class FighterThread implements Runnable {

	/**
	 * 数据控制类引用
	 */
	private BaseData data;

	/**
	 * 敌人数组
	 */
	private List<Fighter> fighterList;

	/**
	 * FighterThread
	 */
	public FighterThread(BaseData data) {
		this.data = data;
		fighterList = data.getFighterList();
	}

	/**
	 * 敌人移动线程执行方法（调用每个敌人的移动方法）
	 */
	public void run() {
		try {
			while (true) {
				for (int i = 0; i < fighterList.size(); i++) {
					Fighter fighter = fighterList.get(i);
					if (fighter == null) {
						break;
					}
					if (fighter.isNeedMove()) {
						fighter.move();
					} else {
						data.getFuturePoint(fighter);
						fighter.move();
					}
					if (fighter.getX() == Data.gameX + Data.squaresSize
							&& fighter.getY() == Data.gameY + Data.squaresSize
									* 14) {
						if (data.getNowLife() > 0) {
							data.setNowLife(data.getNowLife() - 5);
						}
						fighterList.remove(i);
						i--;
					} else if (fighter.nowLife <= 0) {
						data.setMoney(data.getMoney() + fighter.getPrice());
						fighterList.remove(i);
						i--;
					}
				}
				Thread.sleep(20);
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

}
