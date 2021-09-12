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
	
	public ArrayList<User> customerInterface(int i, ArrayList<User> userList) {
		//Scanner scan = new Scanner(System.in);
		
		int userIn = 0;
		do
		{
			if(!userList.get(i).isApproved())
			{
				System.out.println("Your account is still pending approval please try again later.");
				break;
			}
			System.out.println("* * * * * *  Current Balance: $" + userList.get(i).getAccountBalance() + "  * * * * * *\n");
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
			case 1: userList = withdrawl(i, userList);
			break;
			case 2: userList = deposit(i, userList);
			break;
			case 3: userList = transfer(i, userList);
			break;
			case 5: System.out.println("\t\t|  Thanks for banking " + userList.get(i).getFirstName() + " " + userList.get(i).getLastName() + "  |");
			break;
			default: System.out.println("Invalid Selection! Try Again!\n");
			}
		}while(userIn != 5);
		
		return userList;
		//Consider writing to file here!
		//scan2.close();
	}

	public ArrayList<User> transfer(int i, ArrayList<User> userList)
	{
		int userIn;
		double amount = 0;
		String cont = "no";
		
		do
		{
			if(Double.parseDouble(userList.get(i).getAccountBalance()) == 0)
			{
				System.out.println("Can not transfer. Account Balance is 0");
				break;
			}
			
			System.out.print("\t\tEnter 10 digit account number to transfer to : ");
			userIn = scan.nextInt();
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
			if(amount > Double.parseDouble(userList.get(i).getAccountBalance()))
			{
				System.out.println("\t\tInsufficent Funds!");
				System.out.println("To enter a new value enter 'y' or enter anything else to return to the menu...\n\t\tSelection: ");
				cont = scan.next();
				scan.nextLine();
			}
			else
			{
				for(int x = 0; x < userList.size(); x++)
				{
					if(userIn == userList.get(x).getAccountNumber())
					{
						userList.get(i).setAccountBalance(Double.parseDouble(userList.get(i).getAccountBalance()) - amount);
					
						userList.get(x).setAccountBalance(Double.parseDouble(userList.get(x).getAccountBalance()) + amount);
						System.out.println("You transfered $" + userIn + " to account number " + userList.get(x).getAccountNumber() 
								+ " belonging to user " + userList.get(x).getFirstName() + " " + userList.get(x).getLastName());
						
						//writeToFile(userList);
					}
				}
			}	
			//writeToFile(userList);
			
		}while(cont.equals("y") || cont.equals("Y"));
		
		return userList;
		//scan2.close();
	}
	
	public ArrayList<User> deposit(int i, ArrayList<User> userList)
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
			
			userList.get(i).setAccountBalance(Double.parseDouble(userList.get(i).getAccountBalance()) + userIn);
			System.out.println("You deposited $" + String.format("%.2f", userIn) + ". Your new balance is $" + userList.get(i).getAccountBalance());
			//writeToFile(userList);
			
		}while(cont.equals("y") || cont.equals("Y"));
		
		return userList;
		//scan2.close();
	}
	
	public ArrayList<User> withdrawl(int i, ArrayList<User> userList)
	{
		double userIn = 0;
		String cont = "no";
		
		do
		{
			if(Double.parseDouble(userList.get(i).getAccountBalance()) == 0)
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
			else if(userIn > Double.parseDouble(userList.get(i).getAccountBalance()))
			{
				System.out.println("\t\tInsufficent Funds!");
				System.out.println("To enter a new value enter 'y' or enter anything else to return to the menu...\n\t\tSelection: ");
				cont = scan.next();
				scan.nextLine();
			}
			
			userList.get(i).setAccountBalance(Double.parseDouble(userList.get(i).getAccountBalance()) - userIn);
			System.out.println("You withdrew $" + String.format("%.2f", userIn) + ". Your new balance is $" + userList.get(i).getAccountBalance());
			//writeToFile(userList);
			
		}while(cont.equals("y") || cont.equals("Y"));
		
		return userList;
		//scan2.close();
	}
	
	public User newCustomer()
	{
		//Scanner scan = new Scanner(System.in);
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
		
		return newUser;
		
		//WRITE THIS CUSTOMER TO DB CUSTOMER TABLE AND MASTER TABLE
		//save user obj onto customer table and master table on DB
	}
}
