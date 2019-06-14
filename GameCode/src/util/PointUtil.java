package util;

/**
 * 点操作工具类
 * 
 * @author yjm
 */
public class PointUtil {

	/**
	 * 这个是将一个数组中每个数都加上x（根据敌人坐标绘制敌人是用到）
	 */
	public static int[] addToArray(int x, int[] xs) {
		int[] xss = new int[xs.length];
		for (int i = 0; i < xs.length; i++) {
			xss[i] = xs[i] + x;
		}
		return xss;
	}

}
