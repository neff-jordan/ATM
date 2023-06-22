import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ATM {

	public static void main(String[] args) throws NoSuchAlgorithmException {

		Scanner scan = new Scanner(System.in);
		
		Bank theBank = new Bank("BlackRock");
		
		// add a user - also creates a savigns acocunts 
		
		User aUser = theBank.addUser("Jordan", "Neff", "1234");
		
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while(true) { 
			
			// stay in the login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank, scan);
				
			//stay in menu until user quits
			ATM.printUserMenu(curUser, scan);
				
			}
			
		}
		
		public static User mainMenuPrompt(Bank theBank, Scanner scan) { 
			
			//intits 
			String userID;
			String pin;
			User authUser;
			
			//prompt user for id and pin until correct 
			do {
				
				System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
				System.out.print("Enter user ID: ");
				userID = scan.nextLine();
				System.out.print("Enter pin: ");
				pin = scan.nextLine();

				// try to get user object corresponding to id and pin combo
				
				authUser = theBank.userLogin(userID, pin);
				if (authUser == null) { 
					System.out.println("Incorect ID/Pin: ");

				}
				
			}while(authUser == null); // continue loop until correct login
			
			return authUser;
		
		
	}

		public static void printUserMenu(User theUser, Scanner scan) { 
			
			// print a summary of user accounts 
			theUser.printAccountSummary();
			
			// init 
			int choice;
			
			//user menu
			do {
				
			System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
				
			System.out.println("\n 1) Show account transaction history");
			System.out.println(" 2) Withdrawl");
			System.out.println(" 3) Deposit");
			System.out.println(" 4) Transfer");
			System.out.println(" 5) Quit");
			System.out.println();
			System.out.println("Enter choice: ");
			choice = scan.nextInt();

			if(choice < 1 || choice > 5) { 
				System.out.println("Incorrect choice, choose 1-5");
			}

			
			}while(choice < 1 || choice > 5);
			
			//process choice 
			
			switch(choice) { 
			case 1: 
				ATM.showTransHistory(theUser, scan);
				break;
			case 2: 
				ATM.withdrawl(theUser, scan);
				break;
			case 3: 
				ATM.depositFunds(theUser, scan);
				break;
			case 4: 
				ATM.transferFunds(theUser, scan);
				break;
			case 5:
				// gobble up rest of previous input
				scan.nextLine();
				break;
			
			}
			//
			// redisplay menu if user wants to quit 
			//
			// Revisit recursion 
			if(choice != 5) {
				ATM.printUserMenu(theUser, scan);
			}
			
		}
		
		public static void showTransHistory(User theUser, Scanner scan) {
			
			int theAcct;
			
			do {
				System.out.printf("Enter the number (1-%d) of the account\n" +
								  " whose transactions you want to see: ",
								  theUser.numAccounts());
				theAcct = scan.nextInt()-1;
				if(theAcct < 0 || theAcct >= theUser.numAccounts()) {
					System.out.println("Invalid account, please try again");
				} while(theAcct < 0 || theAcct >= theUser.numAccounts());
				
				
			}while(theAcct < 0 || theAcct >= theUser.numAccounts());
			
			// print transaction history 
			theUser.printAcctTransHistory(theAcct);
			
		}
		
		public static void transferFunds(User theUser, Scanner scan) { 
			
			//inits
			int fromAcct;
			int toAcct;
			double amount;
			double acctBalance;
			
			// get amount to transfer from
			do { 
				
				System.out.printf("Enter the number (1-%d) of the account\n" +
									"to transfer from: ", theUser.numAccounts());
				fromAcct = scan.nextInt()-1;
				if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
					System.out.println("Invalid account, please try again");

				}
				
			}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
			
			acctBalance = theUser.getAcctBalance(fromAcct);
			
			//get account to transfer to
			do { 
				
				System.out.printf("Enter the number (1-%d) of the account\n" +
									"to transfer to: ",theUser.numAccounts());
				toAcct = scan.nextInt()-1;
				if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
					System.out.println("Invalid account, please try again");

				}
				
			}while(toAcct < 0 || toAcct >= theUser.numAccounts());
			
			// get amount to transfer 
			do { 
				
				System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBalance);
				amount = scan.nextDouble();
				if(amount < 0) {
					System.out.println("Amount must be greater than zero.");
				

				}
				
			} while(amount < 0);
			
			// finally do the transfer 
			theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
					"Transfer to account %s", theUser.getAcctUUID(toAcct)));
			theUser.addAcctTransaction(toAcct, amount, String.format(
					"Transfer to account %s", theUser.getAcctUUID(fromAcct)));
			
		}
		
		public static void withdrawl(User theUser, Scanner scan) {
			
			//inits
			int fromAcct;
			int toAcct;
			double amount;
			double acctBalance;
			String memo;
			
			// get amount to transfer from
			do { 
				
				System.out.printf("Enter the number (1-%d) of the account\n" +
									"to withdraw from: ", theUser.numAccounts());
				fromAcct = scan.nextInt()-1;
				if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
					System.out.println("Invalid account, please try again");

				}
				
			}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
			
			acctBalance = theUser.getAcctBalance(fromAcct);
			
			// get amount to transfer 
			do { 
				
				System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBalance);
				amount = scan.nextDouble();
				if(amount < 0) {
					System.out.println("Amount must be greater than zero.");
				}else if(amount > acctBalance) { 
					System.out.printf("Amount must not be greater than\n"
							+ "balance of $%.02f\n", acctBalance);

				}
				
			} while(amount < 0 || amount > acctBalance);

			// gobble up rest of previous input
			scan.nextLine();
			
			System.out.println("Enter a memo: ");
			memo = scan.nextLine();
			
			// do withdrawl 
			theUser.addAcctTransaction(fromAcct, -1*amount, memo);
			
		}
		
		public static void depositFunds(User theUser, Scanner scan) { 
			
			//inits
			int toAcct;
			double amount;
			double acctBalance;
			String memo;
			
			// get amount to transfer from
			do { 
				
				System.out.printf("Enter the number (1-%d) of the account\n" +
									"to deposit in: ", theUser.numAccounts());
				toAcct = scan.nextInt()-1;
				if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
					System.out.println("Invalid account, please try again");

				}
				
			}while(toAcct < 0 || toAcct >= theUser.numAccounts());
			
			acctBalance = theUser.getAcctBalance(toAcct);
			
			// get amount to transfer 
			do { 
				
				System.out.printf("Enter the amount to deposit (max $%.02f): $", acctBalance);
				amount = scan.nextDouble();
				if(amount < 0) {
					System.out.println("Amount must be greater than zero.");
				}

				
				
			} while(amount < 0);

			// gobble up rest of previous input
			scan.nextLine();
			
			System.out.println("Enter a memo: ");
			memo = scan.nextLine();
			
			// do withdrawl 
			theUser.addAcctTransaction(toAcct, amount, memo);
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	




