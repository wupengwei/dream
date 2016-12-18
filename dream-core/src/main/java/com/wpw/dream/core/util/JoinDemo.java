package com.wpw.dream.core.util;

public class JoinDemo implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 70; i++) {
			System.out.println(Thread.currentThread().getName() + "----" + i);
			
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		JoinDemo joinDemo = new JoinDemo();
		Thread t1 = new Thread(joinDemo);
		Thread t2 = new Thread(joinDemo);
		t1.start();
		t2.start();
		t1.join();
		
		for (int i = 0; i < 70; i++) {
			System.out.println(Thread.currentThread().getName() + "----" + i);
		}
	}
	

}
