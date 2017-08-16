package com.xinghen.multiThreads;

public class ThreadTest {
	public static void main(String[] args) throws InterruptedException {
//		CalTask tk1 = new CalTask();
//		CalTask tk2 = new CalTask();
//		Thread t1 = new Thread(tk1);
//		Thread t2 = new Thread(tk1);
//		int i = 0;
//		i++;
//		t1.start();
//		t2.start();
		
		
//		CalThread ct = new CalThread();
//		ct.start();
//		ct.run();
//		new Thread(ct).start();
//		Thread.sleep(1000);
//		ct.interrupt();
//		System.out.println("is this thread interrupte : " + Thread.interrupted());
		Thread.currentThread().interrupt();
		
		System.out.println("is this thread interrupte : " + Thread.interrupted());
		Thread.sleep(2000);
		System.out.println("1111");
//		System.out.println("is this thread interrupte : " + Thread.interrupted());
	}
}

class CalTask implements Runnable {
	private int count = 5;

	@Override
	public void run() {
		while (count >0) {
			count--;
			System.out.println("thread name is :" + Thread.currentThread().getName() + " and count is : " + count);
		}
	}
}

class CalThread extends Thread{
	
	public CalThread() {
		System.out.println("constrastor :" + Thread.currentThread().getName());
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(i);
		}
	}
}



