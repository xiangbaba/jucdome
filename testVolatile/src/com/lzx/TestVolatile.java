package com.lzx;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

/**
 * @author lzx
 * @create 2021-07-07 11:41
 */
class MyData{
	AtomicInteger integer = new AtomicInteger();

	public int addNum(){
		return integer.getAndIncrement();
	}
}
public class TestVolatile {
	public static void main(String[] args) {
		MyData d  = new MyData();
		new Thread(()->{
			while (true){

				System.out.println(Thread.currentThread().getName()+"---"+d.addNum());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(()->{
			while (true){
				System.out.println(Thread.currentThread().getName()+"---"+d.addNum());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
