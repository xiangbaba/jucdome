package com.lzx;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lzx
 * @create 2021-07-06 8:29
 * 多线程之间按顺序调用,实现A-B-C
 * 三个线程启动,要求如下:
 *
 * AA打印5次,BB打印10次,CC打印15次
 * 接着按顺序依次打印十轮
 */
class MyData{

	public void print(Integer num) throws InterruptedException {
		Lock lock = new ReentrantLock();
		Condition c1 = lock.newCondition();
		Condition c2 = lock.newCondition();
		Condition c3 = lock.newCondition();
		int flag = 1;
		lock.lock();
		try {
			while (flag!=1){
				c1.await();
			}
			if (flag==num) {
				for (int i = 0; i < 5; i++) {
					System.out.println(Thread.currentThread().getName()+i);
				}
			}

			flag=2;
			c2.signalAll();
			while (flag!=2){
				c2.await();
			}
			if (flag==num) {
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName()+i);
				}
			}
			flag=3;
			c3.signalAll();
			while (flag!=3){
				c3.await();
			}
			if (flag==num) {
				for (int i = 0; i < 15; i++) {
					System.out.println(Thread.currentThread().getName()+i);
				}
			}
			flag=1;
			c1.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}


	}
}

public class MyTest {
	public static void main(String[] args) throws InterruptedException {
		MyData data = new MyData();
		new Thread(()->{
			try {
				data.print(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		},"A").start();
		Thread.sleep(100);
		new Thread(()->{
			try {
				data.print(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		},"B").start();
		Thread.sleep(100);
		new Thread(()->{
			try {
				data.print(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		},"C").start();
	}
}
