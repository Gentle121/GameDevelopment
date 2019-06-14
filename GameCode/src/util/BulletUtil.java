package util;

import model.Bullet;
import model.ElectricBullet;
import model.Fighter;
import model.FireBullet;
import model.IceBullet;
import model.Tower;
import model.WoodBullet;

/**
 * 子弹工具类
 * 
 * @author yjm
 */
public class BulletUtil {

	/**
	 * 根据防御塔（决定类型），敌人（目标），杀伤力创建子弹
	 */
	public static Bullet getBulletByType(Tower tower, Fighter fighter,
			int powers) {
		Bullet bullet = null;
		int x = tower.getX() + 25;
		int y = tower.getY() + 25;
		int type = tower.getType();
		int power = powers;
		if (type == 0) {
			bullet = new IceBullet(x, y, power, fighter);
		}
		if (type == 1) {
			bullet = new FireBullet(x, y, power, fighter);
		}
		if (type == 2) {
			bullet = new ElectricBullet(x, y, power, fighter);
		}
		if (type == 3) {
			bullet = new WoodBullet(x, y, power, fighter);
		}
		return bullet;
	}

}
