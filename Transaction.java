import java.util.Date;

public class Transaction {

	// the transaction amount
	private double amount;
	
	// the date 
	private Date timestamp;

	// a memo for the transaction 
	private String memo;
	
	// which account the transaction happened in 
	private Account inAccount;

	/**
	 * 
	 * @param amount
	 * @param inAccount
	 */
	public Transaction(double amount, Account inAccount) { 
		
		this.amount = amount; 
		this.inAccount = inAccount; 
		this.timestamp = new Date(); 
		this.memo = "";
		
	}

	/**
	 * 
	 * @param amount
	 * @param memo
	 * @param inAccount
	 */
	public Transaction(double amount, String memo, Account inAccount) { 
		
		// call the two arg constructor first 
		this(amount, inAccount);
		
		// set the memo
		this.memo = memo;
		
	}
	
	public double getAmount() { 
		return this.amount;
	}
	
	public String getSummaryLine() {
		if(this.amount >= 0 ) {
			return String.format("%s : $%.02f : %s", this.timestamp.toString(),
					              this.amount, this.memo);
		}else {
			return String.format("%s : $(%.02f) : %s",
					this.timestamp.toString(), this.amount, this.memo);
		}
	}
	
}





