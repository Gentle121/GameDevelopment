package model;

import data.BaseData;

/**
 * 详见父类
 */
public class IceTower extends Tower {

	public IceTower(int x, int y, int size,BaseData data) {
		int[] powers = { 25, 50, 75, 100, 125, 150 };
		int[] fightNums = { 1, 1, 2, 2, 3, 3 };
		this.setPowers(powers);
		this.setFightNums(fightNums);
		this.setLevel(1);
		this.setType(0);
		this.setX(x);
		this.setY(y);
		this.setSpeed(1000);
		this.setData(data);
		this.setFighterList(data.getFighterList());
		this.setPrice(100);
		setPowerAndFinghtNum();
	}

}
