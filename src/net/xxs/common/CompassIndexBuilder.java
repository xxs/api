package net.xxs.common;
import org.compass.gps.CompassGps;
import org.springframework.beans.factory.InitializingBean;

/**
 * 通过quartz调度定时重建索引或自动随Spring ApplicationContext启动而重建索引
 */

public class CompassIndexBuilder implements InitializingBean {

	private int delayTime = 60;// 索引操作线程延时,单位: 秒
	private CompassGps compassGps;

	private Thread indexThread = new Thread() {
		public void run() {
			try {
				Thread.sleep(delayTime * 1000);
				compassGps.index();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	public void afterPropertiesSet() throws Exception {
		indexThread.setDaemon(true);
		indexThread.setName("Compass Indexer");
		indexThread.start();
	}

	public void index() {
		compassGps.index();
	}

	public int getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

	public CompassGps getCompassGps() {
		return compassGps;
	}

	public void setCompassGps(CompassGps compassGps) {
		this.compassGps = compassGps;
	}

}