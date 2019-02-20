package tx;

public class UserAccount {

	private int walletBalance;
	private int accountBalance;
	private boolean isTxActive = false;
	
	public UserAccount(int initialAmount) {
		walletBalance = initialAmount;
	}
	
	public void withdraw(int amount) throws InterruptedException {
		
		long threadId = Thread.currentThread().getId();
		
		synchronized(this) {
			
			while(isTxActive) {
				System.out.printf("[Thread %d] User Account under active transaction, "
						+ "waiting transaction to finish.\n", threadId);
				wait();
			}
			
			isTxActive = true;
			
			System.out.printf("[Thread %d] Transaction processing with amount [%d]\n", 
					threadId, amount);
			
			//Intentionally let active thread wait for one second to simulate double-pay.
			wait(1000);
			
			if(amount > walletBalance) {
				System.out.printf("[Thread %d] Insufficient funds, will stop transaction.\n", threadId);
			} else {
				walletBalance -= amount;
				accountBalance += amount;
			}
			
			isTxActive = false;
			
			notifyAll();
		}
		

		
		System.out.printf("[Thread %d] Account balance  : %d\n", threadId, accountBalance);
	}
}
