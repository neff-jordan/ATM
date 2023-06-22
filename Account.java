import java.util.ArrayList;

public class Account {

	// name of account 
	private String name;
	
	//the account id number (different from the user id #)
	private String uuid;
	
	// the user object that holds this account
	private User holder;
	
	// the list of transactions of this account 
	private ArrayList<Transaction> transactions;


	/**
	 * 
	 * @param name
	 * @param holder
	 * @param theBank
	 */
	
	public Account(String name, User holder, Bank theBank) { 
		
		// set the amount name and holder 
		this.name = name;
		this.holder = holder;
		
		//get new account uuid 
		this.uuid = theBank.getNewAccountUUID();
		
		// initialize transactions 
		this.transactions = new ArrayList<Transaction>();
		
		
		
	}

	/**
	 * get the account id 
	 * @return the uuid
	 */
	public String getUUID() { 
		return this.uuid;
	}

	public String getSummaryLine() {
		
		// get balance
		double balance = this.getBalance();
		
		// format summary line depending if balance is negative 
		if(balance >= 0 ) { 
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		}else { 
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);

		}
		
	}
	
	public double getBalance() { 
		
		double balance = 0;
		for(Transaction t : this.transactions) {
			 balance += t.getAmount();
		}
		return balance;
		
	}
	
	public void printTransHistory() { 
		
		System.out.printf("\n Transaction histroy for account %s\n",
				         this.uuid);
		for(int t = this.transactions.size()-1; t>=0; t--) {
			System.out.printf(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}
	
	public void addTransaction(double amount, String memo) { 
		
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
		
	}
	
}










