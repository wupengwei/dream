package com.wpw.dream.core.util;

class ATask implements Runnable {

	private double d = 0.0;

	public void run() {

		// 检查程序是否发生中断
		while (!Thread.interrupted()) {
			System.out.println("I am running!");

			for (int i = 0; i < 900000; i++) {
				d = d + (Math.PI + Math.E) / d;
			}
		}

		System.out.println("ATask.run() interrupted!");
	}

	public static void main(String[] args) throws Exception {
		// 将任务交给一个线程执行
		Thread t = new Thread(new ATask());
		t.start();

		// 运行一断时间中断线程
		Thread.sleep(100);
		System.out.println("****************************");
		System.out.println("Interrupted Thread!");
		System.out.println("****************************");
		//t.interrupt();
	}

}
