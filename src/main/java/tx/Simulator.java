package tx;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulator {

	public static void main(String[] args) {
		
		//Perform test
		
		UserAccount userAccount = new UserAccount(50);
		ExecutorService threadsExecutor = Executors.newFixedThreadPool(10);
		
		for(int i = 0; i < 10; i++) {
			
			int randomAmount = (new Random().nextInt(10)) + 1;
			threadsExecutor.execute(new UserTransaction(userAccount, randomAmount));
		}
		
		threadsExecutor.shutdown();
	}
}
