package control;

import thread.FighterThread;
import ui.BaseFrame;
import data.BaseData;

/**
 * 控制类
 * 
 * @author yjm
 */
public class BaseControl {

	public BaseControl() {
		BaseData baseData = new BaseData();
		new BaseFrame(baseData);
		Thread fighterThread = new Thread(new FighterThread(baseData));
		fighterThread.start();
	}

	/**
	 *  程序入口
	 */
	public static void main(String[] args) {
		new BaseControl();
	}

}
