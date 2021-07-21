package test;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author lzx
 * @create 2021-07-04 16:36
 * 1.java.util.ConcurrentModificationException并发修改异常,在多个线程同时对ArrayList进行读写操作时可能会发生
 * 可以使用Vector和CopyOnWriteArrayList代替,也可以使用Collections.synchronizedList()其底层使用synchronizedList内部锁
 * 		在中等或者更高负荷下，ReentrantLock有更好的性能
 * 	但是这两个并发容器并不能保证实时数据的一致性
 *
 * 		CopyOnWrite容器即写时复制的容器.往一个容器中添加元素的时候,不直接往当前容器Object[]添加,而是先将当前容器Object进行数组扩容
 * 	 	Arrays.copyOf复制出一个新的容器Object[]newElements,然后新的容器Object[]newElements里添加元素,添加完元素之后,再将原容器
 * 	 	的引用指向新的容器 setArray(newElement);.这样做的好处是可以对CopyOnWrite容器进行并发的读,而不需要加锁,因为当前容器不会添加
 * 	 	新的元素.所以CopyOnWrite也是一种读写分离的思想,读和写不同的容器
 *
 * 	2.HashSet其底层数据结构是HashMap,在调用add方法时会在map的value上放一个常量,这个常量是一个Object对象
 * 	所以它也是线程不安全的,可以使用CopyOnWriteArraySet,CopyOnWriteArraySet其底层也是一个CopyOnWriteArrayList
 *
 * 	3.多线程的横向通信调用一定用的是while,防止虚假唤醒
 */
public class TestConcurrent {
	public static void main(String[] args) throws InterruptedException {
		List<String> list = new CopyOnWriteArrayList<>();
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
				//核心线程数
				5,
				//最大线程数
				9,
				//空闲线程最大存活时间
				2,
				//时间单位
				TimeUnit.SECONDS,
				//等待队列
				new LinkedBlockingQueue<>(3),
				//使用默认的线程工厂
				Executors.defaultThreadFactory(),
				//使用当线程池满的时候,会将当前线程任务回抛给调用者的线程
				new ThreadPoolExecutor.CallerRunsPolicy());
		try {
			for (int i = 0;i < 10;i++) {
				poolExecutor.execute(()->list.add(Thread.currentThread().getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			poolExecutor.shutdown();
		}
		Thread.sleep(2000);
		System.out.println(list);
	}
}
