package util;

/**
 * 防御塔工具类
 * @author yjm
 */
public class TowerUtil {

	/**
	 * 根据防御塔类型获取价格
	 * 
	 * 一上午的时间过去了。。。
	 * 要不是昨天伦哥出了两首新歌，生化危机5出了高清我真没有勇气一口气写这么多注释
	 * 代码写的有点乱，设计是我的弱项，看代码的哥们受累了
	 */
	public static int getPriceByTowerType(int type) {
		int price = 0;
		if (type == 0) {
			price = 100;
		}
		if (type == 1) {
			price = 140;
		}
		if (type == 2) {
			price = 160;
		}
		if (type == 3) {
			price = 150;
		}
		return price;
	}

}
