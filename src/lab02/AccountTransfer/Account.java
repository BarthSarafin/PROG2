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

    // WORKING FUNCTION
/*    synchronized public static boolean accountTransfer(Account fromAccount, Account toAccount,int amount){
        if (fromAccount.getSaldo() >= amount) { // Account must not be overdrawn
            fromAccount.changeSaldo(-amount);
            toAccount.changeSaldo(amount);
        }
        return true;
    }*/

    //DEADLOCK FUNCTION
/*    public static boolean accountTransfer(Account fromAccount, Account toAccount,int amount){
        synchronized (fromAccount){
            synchronized (toAccount){
                if (fromAccount.getSaldo() >= amount) { // Account must not be overdrawn
                    fromAccount.changeSaldo(-amount);
                    toAccount.changeSaldo(amount);
                }
                System.out.println("accounttransfer from "+fromAccount.getId()+" to "+toAccount.getId());
                return true;
            }
        }
    }*/
    public static boolean accountTransfer(Account fromAccount, Account toAccount,int amount){
        if(fromAccount.getId() < toAccount.getId()) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    return coreAccountTransfer(fromAccount, toAccount, amount);
                }
            }
        } else{
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    return coreAccountTransfer(fromAccount, toAccount, amount);
                }
            }
        }
    }

    private static boolean coreAccountTransfer(Account fromAccount, Account toAccount, int amount) {
        if (fromAccount.getSaldo() >= amount) { // Account must not be overdrawn
            fromAccount.changeSaldo(-amount);
            toAccount.changeSaldo(amount);
        }
        System.out.println("accounttransfer from " + fromAccount.getId() + " to " + toAccount.getId());
        return true;
    }

    //Synchronzied nicht mehr notwendig.
	public int getSaldo () {
	   return saldo;
	}

	public void setSaldo(int amount) {
            this.saldo=amount;
	}

	public void changeSaldo (int delta) {
		this.saldo += delta;
	}
}
