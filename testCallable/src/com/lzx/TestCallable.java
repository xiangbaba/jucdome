package com.lzx;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * @author lzx
 * @create 2021-07-07 8:46
 * 创建线程的第三种方式:实现Callable接口,
 * 在创建线程时,new Thread其构造方法并没有直接传Callable接口的方法,RunnableFuture接口继承Runnable接口,
 * 	而FutureTask类又实现了RunnableFuture,futureTask有带Callable接口的构造方法
 */
class MyThread implements Callable {

	@Override
	public Object call() throws Exception {
		return 111;
	}
}
public class TestCallable {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyThread());
		new Thread(futureTask).start();
		System.out.println(futureTask.get());


	}
}
