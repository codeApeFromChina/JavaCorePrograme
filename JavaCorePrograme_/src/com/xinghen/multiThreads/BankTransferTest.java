package com.xinghen.multiThreads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankTransferTest {
	private static final int NACCOUNTS = 10;
	private static final double INITIAL_BALANCE = 1000;

	public static void main(String[] args) {
		Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
		for (int i = 0; i < NACCOUNTS; i++) {
			TransferRunnable t = new TransferRunnable(b, i, INITIAL_BALANCE * 2);
			Thread th = new Thread(t);
			th.start();
		}

	}
}

class Bank {
	private final double account[];
	private Lock bankLock = new ReentrantLock();
	private Condition sufficientFunds;

	public Bank(int amount, double initicalBalance) {
		sufficientFunds = bankLock.newCondition();
		account = new double[amount];
		for (int i = 0; i < account.length; i++) {
			account[i] = initicalBalance;
		}
	}

	// lock/condition version
	// public void transfer(int from, int to, double amount){
	//
	// bankLock.lock();
	// try {
	// while(account[from] < amount){
	// sufficientFunds.await();
	// }
	//
	// System.out.println(Thread.currentThread());
	// account[from] -= amount;
	// System.out.printf("%10.2f, from %d to %d ", amount, from, to);
	// account[from] += amount;
	// sufficientFunds.signalAll();
	// System.out.printf("total Balance: %10.2f%n", getBalance());
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// finally{
	// bankLock.unlock();
	// }
	// }

	// synchronized version
	public synchronized void transfer(int from, int to, double amount) throws InterruptedException {

		while (account[from] < amount) {
			wait();
		}

		System.out.println(Thread.currentThread());
		account[from] -= amount;
		System.out.printf("%10.2f, from %d to %d ", amount, from, to);
		account[from] += amount;
		notifyAll();
		System.out.printf("total Balance: %10.2f%n", getBalance());
	}

	// lock/condition version don't need synchronized
	public synchronized double getBalance() {
		double sum = 0;
		for (double d : account) {
			sum += d;
		}
		return sum;
	}

	public int size() {
		return this.account.length;
	}

}

class TransferRunnable implements Runnable {
	private Bank b;
	private int fromAccount;
	private double maxAmount;
	private int DELAY = 10;

	public TransferRunnable(Bank b, int from, double max) {
		this.b = b;
		fromAccount = from;
		maxAmount = max ;
	}

	@Override
	public void run() {
		while (true) {
			int toAccount = (int) (b.size() * Math.random());
			double amount = Math.random() * maxAmount;
			try {
				this.b.transfer(this.fromAccount, toAccount, amount);
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
