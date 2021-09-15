package bank;

import java.util.*;

public class CustomerHandler extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Scanner scan = new Scanner(System.in);

	public CustomerHandler() {
		//master list is in the database
		//customer list is in the database access customer list in database
		
		//ArrayList<User> cstList = new ArrayList<User>();
	}
	
	public User customerInterface(String entireUser, JDBCBank ref) {
		//Scanner scan = new Scanner(System.in);
		int userIn = 0;
		
		//SOME SORT OF SPLIT METHOD TO REPOPULATE A USER OBJ
		User u = parseToUser(entireUser);
		ref.addTransaction(u, "Login");
		
		do
		{
			if(!u.isApproved())
			{
				System.out.println("Your account is still pending approval please try again later...");
				break;
			}
			System.out.println("* * * * * *  Current Balance: $" + u.getAccountBalance() + "  * * * * * *\n");
			System.out.println("\t[1] Withdraw\t\t[2] Deposit\n\t[3] Transfer Funds\t[5] Log out (Save Changes)");
			
			System.out.print("\t\tSelection: ");
			do
			{
				try
				{
					userIn = scan.nextInt();
				} catch(InputMismatchException e)
				{
					System.out.print("\t\tSelection: ");
					scan.nextLine();
				}
			}while(userIn == 0);
			
			switch(userIn)
			{
			case 1: u = withdrawl(u);
			ref.addTransaction(u, "Withdrawl");
			break;
			case 2: u = deposit(u);
			ref.addTransaction(u, "Deposit");
			break;
			case 3: u = transfer(u, ref);
			ref.addTransaction(u, "Transfer OUT");
			break;
			case 5: System.out.println("\t\t|  Thanks for banking " + u.getFirstName() + " " + u.getLastName() + "  |");
			break;
			default: System.out.println("Invalid Selection! Try Again!\n");
			}
		}while(userIn != 5);
		
		return u;
		//Consider writing to file here!
		//scan2.close();
	}

	public User transfer(User u, JDBCBank ref)
	{
		long userIn;
		double amount = 0;
		String cont = "no";
		
		do
		{
			if(Double.parseDouble(u.getAccountBalance()) == 0)
			{
				System.out.println("Can not transfer. Account Balance is 0");
				break;
			}
			
			System.out.print("\t\tEnter 10 digit account number to transfer to : ");
			userIn = scan.nextLong();
			System.out.print("Enter Amount: $");
			
			do
			{
				try
				{
					amount = scan.nextDouble();
				} catch(InputMismatchException e)
				{
					System.out.print("\t\tEnter Amount: ");
					scan.nextLine();
				}
			}while(userIn == 0);
			
			if(amount <= 0)
			{
					System.out.println("\t\tInaccurate value!");
					System.out.println("To enter a new value enter 'y' or enter anything else to return to the menu...\n\t\tSelection: ");
					cont = scan.next();
					scan.nextLine();
			}
			if(amount > Double.parseDouble(u.getAccountBalance()))
			{
				System.out.println("\t\tInsufficent Funds!");
				System.out.println("To enter a new value enter 'y' or enter anything else to return to the menu...\n\t\tSelection: ");
				cont = scan.next();
				scan.nextLine();
			}
			else
			{	
				String str = ref.findInCustomerTable(userIn + "");
				if(str.equals("INVALID"))
					System.out.println("Sorry could not find that customer ID! Try again...");
				else
				{
					u.setAccountBalance(Double.parseDouble(u.getAccountBalance()) - amount);
					ref.updateCustomer(u);
					User temp = parseToUser(str);
					
					
					temp.setAccountBalance(Double.parseDouble(temp.getAccountBalance()) + amount);
					System.out.println("You transfered $" + amount + " to account number " + userIn 
							+ " belonging to user " + temp.getFirstName() + " " + temp.getLastName());
					
					ref.updateCustomer(temp);
					ref.addTransaction(temp, "Transfer IN");
					return u;
				}
			}	
			
		}while(cont.equals("y") || cont.equals("Y"));
		
		return u;
	}
	
	public User deposit(User u)
	{
		double userIn = 0;
		String cont = "no";
		
		do
		{
			System.out.print("\t\tDeposit Amount: ");
			do
			{
				try
				{
					userIn = scan.nextDouble();
				} catch(InputMismatchException e)
				{
					System.out.print("\t\tDeposit Amount: ");
					scan.nextLine();
				}
			}while(userIn == 0);
			
			if(userIn <= 0)
			{
				System.out.println("\t\tInaccurate value!");
				System.out.println("To enter a new value enter 'y' or enter anything else to return to the menu...\n\t\tSelection: ");
				cont = scan.next();
				scan.nextLine();
			}
			
			u.setAccountBalance(Double.parseDouble(u.getAccountBalance()) + userIn);
			System.out.println("You deposited $" + String.format("%.2f", userIn) + ". Your new balance is $" + u.getAccountBalance());
			
		}while(cont.equals("y") || cont.equals("Y"));
		
		return u;
	}
	
	public User withdrawl(User u)
	{
		double userIn = 0;
		String cont = "no";
		
		do
		{
			if(Double.parseDouble(u.getAccountBalance()) == 0)
			{
				System.out.println("Can not withdraw. Account Balance is 0");
				break;
			}
			
			System.out.print("\t\tWithdrawl Amount: ");
			do
			{
				try
				{
					userIn = scan.nextDouble();
				} catch(InputMismatchException e)
				{
					System.out.print("\t\tWithdrawl Amount: ");
					scan.nextLine();
				}
			}while(userIn == 0);
			
			if(userIn <= 0)
			{
				System.out.println("\t\tInaccurate value!");
				System.out.println("To enter a new value enter 'y' or enter anything else to return to the menu...\n\t\tSelection: ");
				cont = scan.next();
				scan.nextLine();
			}
			else if(userIn > Double.parseDouble(u.getAccountBalance()))
			{
				System.out.println("\t\tInsufficent Funds!");
				System.out.println("To enter a new value enter 'y' or enter anything else to return to the menu...\n\t\tSelection: ");
				cont = scan.next();
				scan.nextLine();
			}
			else
			{
				u.setAccountBalance(Double.parseDouble(u.getAccountBalance()) - userIn);
				System.out.println("You withdrew $" + String.format("%.2f", userIn) + ". Your new balance is $" + u.getAccountBalance());
				return u;
			}
		}while(cont.equals("y") || cont.equals("Y"));
		
		return u;
	}
	
	public User newCustomer()
	{
		User newUser = new User();
		
		String temp;
			
		System.out.println("Thanks for choosing The Bank! Please fill out all information on this application\n\tYour Application is subject to pending approval...\n");
		
		System.out.print("First Name: ");
		newUser.setFirstName(scan.nextLine());
		System.out.print("Last Name: ");
		newUser.setLastName(scan.nextLine());
		
		try
		{
			System.out.print("Phone Number(Only Numerics Accepted): ");
			newUser.setPhoneNumber(scan.nextLong());
			scan.nextLine();
		} catch(InputMismatchException e)
		{
			System.out.println("\n*** Invalid input format an employee will help you correct this later. ***\n***                         Please Continue!                           ***");
			scan.nextLine();
		}
		
		System.out.print("Email Address: ");
		newUser.setEmail(scan.nextLine());
		
		try
		{
			System.out.print("Last 4 of SSN(Only Numerics Accepted): ");
			newUser.setSsn(scan.nextInt());
			scan.nextLine();
		} catch(InputMismatchException e)
		{
			System.out.println("\n*** Invalid input format an employee will help you correct this later. ***\n***                         Please Continue!                           ***");
			scan.nextLine();
		}
		
		System.out.print("Username: ");
		newUser.setUsername(scan.nextLine());
		System.out.print("Password: ");
		newUser.setPassword(scan.nextLine());
		
		newUser.setAccountNumber((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
		
		System.out.print("Will this be a joint account? Please enter 'y' for yes or anything else for no\nSelection: ");
		temp = scan.nextLine();
		if(temp.equals("y") || temp.equals("Y"))
		{
			newUser.setJointAccount(true);
			System.out.print("Enter existing account number: ");  //need to validate if this really is an existing account
			newUser.setJointAccNo(scan.nextLong());
		}
		else
		{
			newUser.setJointAccount(false);
			newUser.setJointAccNo(0);
		}
		
		newUser.setApproved(false);
		newUser.setAccountBalance(0);
		newUser.setEmployee(false);
		newUser.setAdmin(false);
		
		newUser.setDailyLimit(1000);
		
		return newUser;
	}
}
