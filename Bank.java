import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String name;
	
	private ArrayList<User> users;
	
	private ArrayList<Account> accounts; 
	
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	/**
	 * generate a new uuid for user 
	 * @return the uuid
	 */
	
	public String getNewUserUUID() {
		
		// inits 
		String uuid;
		
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		// continue looping until we get a unique id 
		do { 
			// generate the number 
			uuid = "";
			for(int c = 0; c < len; c++) { 
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			// check to see if unique 
			nonUnique = false;
			for(User u : this.users) { 
				if(uuid.compareTo(u.getUUID())==0) {
					nonUnique = true;
					break;
				}
			}
			
		}while(nonUnique);
		
		return uuid;
		
	}


	public String getNewAccountUUID() { 
		
		// inits 
		String uuid;
		
		Random rng = new Random();
		int len = 10;
		boolean nonUnique;
		
		// continue looping until we get a unique id 
		do { 
			// generate the number 
			uuid = "";
			for(int c = 0; c < len; c++) { 
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			// check to see if unique 
			nonUnique = false;
			for(Account a : this.accounts) { 
				if(uuid.compareTo(a.getUUID())==0) {
					nonUnique = true;
					break;
				}
			}
			
		}while(nonUnique);
		
		return uuid;
				
			
		
	}

	public void addAccount(Account account) { 
		this.accounts.add(account);
	}
	
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param pin
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	
	public User addUser(String firstName, String lastName, String pin) throws NoSuchAlgorithmException { 
		
		// create a new User object and add it to our list 
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		// create savings account 
		Account newAccount = new Account("Savings", newUser, this);
		
        //add holder to bank list 
		newUser.addAccount(newAccount); 
		this.accounts.add(newAccount);
		
		return newUser;
		
	}
	
	public User userLogin(String userID, String pin) { 
		
		// serach through list of users 
		for(User u : this.users) { 
			
			// check user id 
			if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
			
		}
		// if user has not been found or have an incorrect pin 
		return null;
		
	}
	
	public String getName() { 
		
		return this.name;
		
	}
	
}











