package com.lzx;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lzx
 * @create 2021-07-04 12:45
 * ReentrantLock 可重入的互斥锁,它具有与使用synchronized方法和语句所访问的饮食监视器锁相同的一些基本行为和语义,但功能更加强大.
 * ReentrantLock将有最近成功获得锁,并且还没有释放该锁的线程所拥有.当锁没有被另一个线程所拥有时,调用lock的线程将成功获取该锁并返回.如果当前线程已经拥有该锁,此方法将立即返回
 */
public class ReentranlockTest {

	public static void main(String[] args) {
		final TestLock testLock = new TestLock();
		new Thread(()->{
			while (true) {
				testLock.lock(Thread.currentThread().getName());
			}
		}).start();
		new Thread(()->{
			while (true){
				testLock.lock(Thread.currentThread().getName());
			}
		}).start();
	}


}
class TestLock{
	Lock lock = new ReentrantLock();
	Integer num = 100;
	public void lock(String arg){
		lock.lock();
		try {
			Thread.sleep(100);
			System.out.println(arg+"当前数:"+(num--)+"剩余数:"+num);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

}
