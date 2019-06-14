package model;

import data.BaseData;

/**
 * 详见父类
 */
public class ElectricTower extends Tower {
	
	public ElectricTower(int x, int y, int size,BaseData data) {
		int[] powers = { 40, 80, 120, 160, 200, 240 };
		int[] fightNums = { 1, 1, 2, 2, 3, 3 };
		this.setPowers(powers);
		this.setFightNums(fightNums);
		this.setLevel(1);
		this.setType(2);
		this.setX(x);
		this.setY(y);
		this.setSpeed(1000);
		this.setData(data);
		this.setFighterList(data.getFighterList());
		this.setPrice(160);
		setPowerAndFinghtNum();
	}

}
