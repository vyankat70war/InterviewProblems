package com.vss.important.threading;

public class PrintInorder {

	public static int numberOfThreads = 8;
	private volatile Integer counter = 1;
	private volatile int nextThreadToRun = 1;
	private Object lock = new Object();
	private static int printUpto = 80;

	public static void main(String[] args) {

		PrintInorder printInorder = new PrintInorder();

		for (int i = 0; i < numberOfThreads; i++) {
			new Thread(printInorder.new PrinterThread(i + 1)).start();
		}

	}

	class PrinterThread implements Runnable {
		private int threadId;

		public PrinterThread(int threadId) {
			super();
			this.threadId = threadId;
		}

		public void run() {
			try {
				while (counter <= printUpto) {
					synchronized (lock) {
						int id = threadId % numberOfThreads;
						if(id ==0) {
							id = numberOfThreads;
						}
						if (id == nextThreadToRun) {
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
}


