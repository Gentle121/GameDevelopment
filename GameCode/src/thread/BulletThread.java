package thread;

import java.util.List;

import model.Bullet;
import data.BaseData;

/**
 * 子弹运动线程
 * 
 * @author yjm
 */
public class BulletThread implements Runnable {

	/**
	 * 子弹数组
	 */
	private List<Bullet> bulletList;

	/**
	 * BulletThread
	 */
	public BulletThread(BaseData data) {
		bulletList = data.getBulletList();
	}

	/**
	 * 子弹线程执行方法（调用子弹数组中元素的move方法）
	 */
	public void run() {
		try {
			while (true) {
				for (int i = 0; i < bulletList.size(); i++) {
					Bullet bullet = bulletList.get(i);
					if (bullet != null) {
						if (bullet.isDone()) {
							bulletList.remove(i);
							i--;
							continue;
						}
						bullet.move();
						if (bullet.getType() == 2) {
							bullet.setCanMove(false);
						}
					}
				}
				Thread.sleep(50);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
