package com.lzx;

/**
 * @author lzx
 * @create 2021-07-13 8:40
 */
public class MyQueue {

	private final Integer maxSize;
	/**
	 * 前面的指针
	 */
	private Integer front;
	/**
	 * 后面的指针
	 */
	private Integer rear;

	private final Object[] arr;

	/**
	 * 构造方法
	 * @param maxSize
	 */
	public MyQueue(int maxSize) {
		this.maxSize = maxSize;
		arr = new Object[maxSize];
		front=-1;
		rear=-1;
	}
	/**
	 * 判断队列是否已满
	 */
	public boolean ifFull(){
		return rear==maxSize-1;
	}
	/**
	 * 判断队列是否为空
	 */
	public boolean ifEmpty(){
		return rear.equals(front);
	}
	/**
	 * 添加数据
	 */
	public int addArr(Object o){
		if (ifFull()){
			throw new RuntimeException("队列已满");
		}
		rear++;
		this.arr[rear]= o;
		return 1;
	}
	/**
	 * 取数据
	 */
	public Object getQueue(){
		if (ifEmpty()){
			throw new RuntimeException("队列为空");
		}
		front++;
		return arr[front];
	}

}
