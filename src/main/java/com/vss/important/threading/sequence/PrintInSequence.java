package com.vss.important.threading.sequence;

public class PrintInSequence {


	private static Object lock = new Object();

	public static void main(String[] args) {

		for (int i = 0; i < 3; i++) {
			new Thread(new WriteThread(i + 1,lock)).start();
		}
	}
}

class WriteThread extends Thread {
	private static final int numberOfThreads = 3;
	private static int counter = 1;
	private int threadId;
	private Object lock;
	private static int nextThreadToRun = 1;
	private static int printUpto = 10;

	public WriteThread(int threadId, Object lock) {
		super();
		this.threadId = threadId;
		this.lock = lock;
	}

	public void run() {
		try {
			while (counter <= printUpto) {
				int id = threadId % numberOfThreads;
				if(id ==0) {
					id = numberOfThreads;
				}
				synchronized (lock ) {
					if (id == nextThreadToRun ) {
						System.out.println("Thread " + threadId + " : " + counter);
						counter++;
						nextThreadToRun = id % numberOfThreads + 1;
						lock.notifyAll();
					} else {
						lock.wait();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}