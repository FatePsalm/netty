package club.pinea.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
	
	private ThreadPoolExecutor threadPool;
	
	private static class ThreadPoolUtilCreater {
		private static ThreadPoolUtil threadPool = new ThreadPoolUtil(20, 30, 60, 20000);
	}
	
	public static ThreadPoolUtil getInstance() {
		return ThreadPoolUtilCreater.threadPool;
	}
	
	public ThreadPoolUtil(int minPool, int maxPool, int keepaliveSecond, int queueSize) {
		threadPool = new ThreadPoolExecutor(minPool, maxPool, keepaliveSecond, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize), new ThreadPoolExecutor.DiscardOldestPolicy());
	}

	public void execute(Runnable task) {
		threadPool.execute(task);
	}

	public void closeNow(){
		if(threadPool != null && !threadPool.isShutdown()){
			threadPool.shutdownNow(); // 线程池的状态立刻变成STOP状态，试图停止所有正在执行的线程，不再处理还在池队列中等待的任务
		}
	}

}
