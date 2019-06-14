package model;

import data.BaseData;

/**
 * 详见父类
 */
public class FireTower extends Tower {
	
	public FireTower(int x, int y, int size,BaseData data) {
		int[] powers = { 30, 60, 90, 120, 150, 180 };
		int[] fightNums = { 1, 1, 2, 2, 3, 3 };
		this.setPowers(powers);
		this.setFightNums(fightNums);
		this.setLevel(1);
		this.setType(1);
		this.setX(x);
		this.setY(y);
		this.setSpeed(1000);
		this.setData(data);
		this.setFighterList(data.getFighterList());
		this.setPrice(140);
		setPowerAndFinghtNum();
	}

}
