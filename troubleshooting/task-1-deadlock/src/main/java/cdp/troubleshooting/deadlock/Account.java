package cdp.troubleshooting.deadlock;

import java.util.concurrent.CyclicBarrier;

public class Account {
	
	final static CyclicBarrier barrier = new CyclicBarrier(2);
	
	String name;

	double balance;

	public Account(String name, double balance) {
		this.name = name;
		this.balance = balance;
	}

	void withdraw(double amount) {
		balance -= amount;
	}

	void deposit(double amount) {
		balance += amount;
	}

	static void transfer(Account from, Account to, double amount) throws Exception {
		synchronized (from) {   //lock from account
			barrier.await();    //simulate simultaneous thread arrival to this point of code
			synchronized (to) { //lock to account
				System.out.println("Transfer money from " + from.name + " to " + to.name);
				from.withdraw(amount);
				to.deposit(amount);
			}
		}
	}
	
	
}
