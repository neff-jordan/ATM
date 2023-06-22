import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

	// user first name
	private String firstName;
	
	// user last name 
	private String lastName;
	
	// id of the user 
	private String uuid;
	
	// MD5 hash of users pin number 
	private byte pinHash[];
	
	// the list of accounts for this user
	private ArrayList<Account> accounts;
	
	/** 
	 * 
	 * @param firstName  the user's first name
	 * @param lastName   user's last name 
	 * @param pin		 account pin number 
	 * @param theBank	 the bank object that the user is a customer of 
	 * @throws NoSuchAlgorithmException
	 */

	public User(String firstName, String lastName, String pin, Bank theBank)  {
		
		// set user's name
		this.firstName = firstName;
		this.lastName = lastName;
		
		// hash using md5 algorithm 
		// store the md5 hash, rather then the original value for security 
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		this.pinHash = md.digest(pin.getBytes());
		
		
		// get a new, unique universal id for the user 
		this.uuid = theBank.getNewUserUUID();
		
		// create empty list of accounts 
		this.accounts = new ArrayList<Account>();
		
		// print log message 
		System.out.printf("New user: %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
		
		
	}

	public void addAccount(Account account) {

		this.accounts.add(account);
		
	}

	public String getUUID() { 
		return this.uuid;
	}
	/**
	 * 
	 * @param aPin
	 * @return
	 */
	public boolean validatePin(String aPin) { 
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}
	
	public String getFirstName() { 
		return this.firstName;
	}
	
	public void printAccountSummary() { 
		
		System.out.printf("\n\n%s's accounts summary:\n", this.firstName);
		for(int a =0; a< this.accounts.size(); a++ ) { 
			System.out.printf("%d) %s\n", a+1,
					this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
		
	}

	public int numAccounts() { 
		
		return this.accounts.size();
		
	}
	
	public void printAcctTransHistory(int acctIdx) { 
		this.accounts.get(acctIdx).printTransHistory();
	}
	
	public double getAcctBalance(int acctIdx) { 
		return this.accounts.get(acctIdx).getBalance();
	}
	
	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}
	
	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
	
}








