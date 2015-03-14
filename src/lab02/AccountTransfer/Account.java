package lab02.AccountTransfer;// Template for Excercise 2

public class Account {

	private int id;
	private int saldo = 0;

	public Account(int id, int initialSaldo) {
		this.id = id; this.saldo = initialSaldo;
	}

	public int getId() {
	   return id;
	}

	public synchronized int getSaldo () {
	   return saldo;
	}

	public synchronized void setSaldo(int amount) {
		this.saldo=amount;
	}

	public   synchronized void changeSaldo (int delta) {
		this.saldo += delta;
	}
}
