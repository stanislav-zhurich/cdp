package cdp.troubleshooting.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {

	public static void main(String[] args) {
		
		ExecutorService service = Executors.newFixedThreadPool(2);
		Account account1 = new Account("Account 1", 1000);
		Account account2 = new Account("Account 2", 2000);
		
		service.submit(new AccountOperation(account1, account2, 100));  //transfer from account 2 to account 1
		service.submit(new AccountOperation(account2, account1, 120));  //transfer from account 1 to account 2
		service.shutdown();
	}

	static class AccountOperation implements Runnable{

		private final Account to;
		private final Account from;
		private final double amount;
		
		public AccountOperation(Account to, Account from, double amount) {
			this.to = to;
			this.from = from;
			this.amount = amount;
		}
		
		@Override
		public void run() {
			try {
				Account.transfer(from, to, amount);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
