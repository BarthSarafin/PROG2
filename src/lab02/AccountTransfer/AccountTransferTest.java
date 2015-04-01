package lab02.AccountTransfer;

class AccountTransferThread extends Thread {

	private Account fromAccount;
	private Account toAccount;
	private int amount;
	private int maxIter = 5000;

	public AccountTransferThread (String name, Account fromAccount, Account toAccount, int amount) {
		super(name);
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
	}


	/*  Transfer amount from fromAccount to toAccount */
	public void accountTransfer() {
        fromAccount.accountTransfer(toAccount,amount);
	}


	public void run() {

		for (int i=0; i<maxIter; i++) {
			accountTransfer();
			try	{ // simulation of work time
				Thread.sleep((int)(Math.random() * 10));
			} catch(InterruptedException e) {;}
		}
		System.out.println("DONE! " + getName());
	}
}

public class AccountTransferTest {
	public static void main (String[] args) {
		Account account1 = new Account (1, 10);
		Account account2 = new Account (2, 10);
		Account account3 = new Account (3, 999999);

		System.out.println ("Start of Transaction");
		System.out.println ( "Saldo account1: " + account1.getSaldo() );
		System.out.println ( "Saldo account2: " + account2.getSaldo() );
		System.out.println ( "Saldo account3: " + account3.getSaldo() );

		System.out.println("Total of account1 and account2 and account3: " +
				(account1.getSaldo() + account2.getSaldo() + account3.getSaldo()) );

		AccountTransferThread t1 = new AccountTransferThread("Worker 1", account1, account2, 1);
		AccountTransferThread t2 = new AccountTransferThread("Worker 2", account1, account3, 1);
		AccountTransferThread t3 = new AccountTransferThread("Worker 3", account3, account1, 2);

		t3.start();
		t2.start();
		t1.start();

		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {}


		System.out.println ("End of Transaction");
		System.out.println ( "Saldo account1: " + account1.getSaldo() );
		System.out.println ( "Saldo account2: " + account2.getSaldo() );
		System.out.println ( "Saldo account3: " + account3.getSaldo() );

		System.out.println("Total of account1 and account2 and account3: " +
				(account1.getSaldo() + account2.getSaldo() + account3.getSaldo()) );

	}
}