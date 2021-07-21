package com.lzx;


import java.util.concurrent.*;

/**
 * @author lzx
 * @create 2021-07-08 19:28
 *
 *1.在创建了线程池后,开始等待请求.
 * 2.当调用execute()方法添加一个请求任务时,线程池会做出如下判断:
 * 		2.1如果正在运行的线程数量小于corePoolSize,那么马上创建线程运行这个任务;
 * 		2.2如果正在运行的线程数量大于或等于corePoolSize,那么这个任务会放到队列;
 * 		2.3如果队列满了且正在运行的线程数量还是小于maximumPoolSize,
 * 			那么还是要创建非核心线程立刻运行这个任务;
 * 		2.4如果队列满了且正在运行的线程数量大于或等于maximumPoolSize,
 * 			那么线程池会启动饱和拒绝策略.
 * 3.当一个线程完成任务时,它会从队列中取下一个任务来执行.
 * 4.当一个线程无事可做超过一定keepAliveTime时,线程会判断:
 * 		如果当前运行线程数大于corePoolSize,那么这个线程就会被停掉
 * 		所以线程池的所有任务完成后,它最终会收缩到corePoolSize的大小.
 *
 */
public class TestExecutors {
	private static final Integer NUM = 10;
	public static void main(String[] args) {
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
				//核心线程数
				5,
				//最大线程数
				9,
				//超时时间
				2,
				//超时时间单位
				TimeUnit.SECONDS,
				//等待队列
				new LinkedBlockingQueue<>(3),
				//Thread工厂
				Executors.defaultThreadFactory(),
				//拒绝策略
				new ThreadPoolExecutor.AbortPolicy());

		try {
			for (int i = 0; i < NUM; i++) {
				//execute()参数是一个Runnable接口
				poolExecutor.execute(()->System.out.println(Thread.currentThread().getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			poolExecutor.shutdown();
		}
	}

}
