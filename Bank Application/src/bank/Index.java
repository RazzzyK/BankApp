package bank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Index{
	static Scanner scan = new Scanner(System.in);
	
	static int userInput;
	static ArrayList<User> userList = new ArrayList<User>();
	
	static int[] temp = new int[] {1, 2, 3};


	public static void main(String[] args) throws IOException {
		
		if(!readFromFile())
		{
			createNewEmployee();
			createAdmin();
		}
		
		do
		{
			System.out.print("\n\t\tWelcome to the Bank!\n\tPlease choose from the following menu\n\n[1] Login\t[2] Create New Account\t[-99] Quit\n\t\tSelection: ");
			do
			{
				try
				{
					userInput = scan.nextInt();
					scan.nextLine();
				} catch(InputMismatchException e)
				{
					System.out.print("\t\tSelection: ");
					scan.nextLine();
				}
			}while(userInput == 0);
			
			System.out.println();
		
			if(userInput == 1)
				login(scan);
			else if(userInput == 2)
			{
				userList.add(new User());
				writeToFile(userList);
			}
			else if (userInput == -99)
				System.out.println("********** Thanks for using The Bank! **********");
			else
				System.out.println("Please limit your input to only 1, 2 or 3");
			
		}while(userInput != -99);
		
		scan.close();
	}
	
	private static void login(Scanner scan) {
		boolean found = false;
		int counter = 0;
		String userN = "", passW = "";
		do
		{
			System.out.println("\tPlease enter username and password. Case Sensitive.");
			System.out.print("Username: ");
			userN = scan.nextLine();
			System.out.print("Password: ");
			passW = scan.nextLine();
			
			//System.out.println(customerList.get(0).getUsername());
			
			for(int i = 0; i < userList.size(); i++)
			{
				if(userN.equals(userList.get(i).getUsername()))
				{
					if(passW.equals(userList.get(i).getPassword()))
					{
						System.out.println("\n--Access Granted. Welcome " + userList.get(i).getFirstName() + " " +userList.get(i).getLastName() + "--");
						
						if(userList.get(i).isAdmin())
						{
							adminInterface(scan);
							found = true;
							break;
						}
						if(userList.get(i).isEmployee())
						{
							employeeInterface(i, scan);
							found = true;
							break;
						}
						else
							customerInterface(i, scan);
						
						found = true;
						break;
					}
//					else
//						System.out.println("---------Username or Password may be inccorrect2.---------");
				}
			}
			
			if(!found)
			{
				System.out.println("---------Username or Password may be inccorrect.----------\n");
				counter++;
			}
			if(counter > 3)
			{
				System.out.println("---------Too Many Failed Attempts. Returning to Menu.---------");
				break;
			}
			
		}while(!found);
	}

//---------------------------------------------------------------------------------ALL METHODS PRETAINING TO CUSTOMERS-------------------------------------------------------------------------------------------------------------
	
	public static void customerInterface(int i, Scanner scan) {
		int userIn = 0;
		do
		{
			if(!userList.get(i).isApproved())
			{
				System.out.println("Your account is still pending approval please try again later.");
				break;
			}
			System.out.println("* * * * * *  Current Balance: $" + userList.get(i).getAccountBalance() + "  * * * * * *\n");
			System.out.println("\t[1] Withdraw\t\t[2] Deposit\n\t[3] Transfer Funds\t[5] Log out");
			
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
			case 1: withdrawl(i, scan);
			break;
			case 2: deposit(i, scan);
			break;
			case 3: transfer(i, scan);
			break;
			case 5: System.out.println("\t\t|  Thanks for banking " + userList.get(i).getFirstName() + " " + userList.get(i).getLastName() + "  |");
			break;
			default: System.out.println("Invalid Selection! Try Again!\n");
			}
		}while(userIn != 5);
		
		//scan2.close();
	}
	
	public static void transfer(int i, Scanner scan)
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
						
						writeToFile(userList);
					}
				}
			}	
			//writeToFile(userList);
			
		}while(cont.equals("y") || cont.equals("Y"));
		
		//scan2.close();
	}
	
	public static void deposit(int i, Scanner scan)
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
			writeToFile(userList);
			
		}while(cont.equals("y") || cont.equals("Y"));
		
		//scan2.close();
	}
	
	public static void withdrawl(int i, Scanner scan)
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
			writeToFile(userList);
			
		}while(cont.equals("y") || cont.equals("Y"));
		
		//scan2.close();
	}
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//----------------------------------------------------------------------------------ALL METHODS PRETAINING TO EMPLOYEES------------------------------------------------------------------------------------------------------------
	
	public static void employeeInterface(int x, Scanner scan)
	{
		ArrayList<User> pendingApps = new ArrayList<User>();
		
		int index = 0, pendingIndex = 0, status = 0, i = 0;
		
		for(int z = 0; z < userList.size(); z++)
		{
			
			if(!userList.get(z).isApproved())
				pendingApps.add(userList.get(z));
		}
		
		while(pendingApps.size() > 0)
		{
			System.out.println("** ATTENTION THERE ARE " + pendingApps.size() + " PENDING APPLICATIONS THAT NEED(S) TO BE APPROVED/DENIED **\n**          PLEASE APPROVE/DENY THESE APPLICATIONS BEFORE CONTINUING           **\n");
			System.out.println("\tList of Pending Applications");
			System.out.println("\t----------------------------");
			printCustomers(pendingApps);
			System.out.println("\n\t[-99] Skip Pending Applications");
			
			System.out.print("\n\tIndex: ");
			
			do
			{
				try
				{
					pendingIndex = scan.nextInt();
				} catch(InputMismatchException e)
				{
					System.out.print("\tIndex: ");
					scan.nextLine();
				}
			}while(pendingIndex == 0);

			if(pendingIndex == -99)
				break;
			
			System.out.print("[1] Approve OR [2] Deny " + pendingApps.get(pendingIndex - 1).getFirstName() + " | Selection: ");
			
			do
			{
				try
				{
					status = scan.nextInt();
				} catch(InputMismatchException e)
				{
					System.out.print("\t\tSelection: ");
					scan.nextLine();
				}
			}while(status == 0);
			
			if(status == 1)
			{
				System.out.println("Name: " + pendingApps.get(pendingIndex - 1).getFirstName() + " Acc No: " + pendingApps.get(pendingIndex - 1).getAccountNumber());
				approveApp(pendingApps.get(pendingIndex - 1).getAccountNumber(), true);
				pendingApps.remove(pendingIndex - 1);
			}
			
			if(status == 2)
			{
				approveApp(pendingApps.get(pendingIndex - 1).getAccountNumber(), false);
				pendingApps.remove(pendingIndex - 1);
			}
		}
		
		do
		{
			ArrayList<User> cstList = new ArrayList<User>();
			
			for(i = 0; i < userList.size(); i++)
			{
				if(!(userList.get(i).isEmployee()) &&!(userList.get(i).isAdmin()) && userList.get(i).isApproved())
				{
					cstList.add(userList.get(i));
				}
			}
			
			System.out.println("\n\t    Customer List\n\t    -------------");
			printCustomers(cstList);
			System.out.println("\n\t[-99] Employee Log Out");
			
			int userIn = 0;
			System.out.print("\n\tIndex: ");
			do
			{
				try
				{
					index = scan.nextInt();
				} catch(InputMismatchException e)
				{
					System.out.print("\tIndex: ");
					scan.nextLine();
				}
			}while(index == 0);
			
			if(index == -99) //Employee Log Out
				break;
			if(index > cstList.size() || index < 0)
			{
				System.out.println("\tInvalid Selection Try Again");
				continue;
			}
			
			do
			{
				System.out.println("\nCustomer Selected: " + cstList.get(index - 1).printName());
				long cstAccountNumber = cstList.get(index - 1).getAccountNumber();
				System.out.println(cstList.get(index - 1));
				System.out.println("\n\t[-99] Back to Menu");
				
				System.out.println("\nSelect an option to edit customer information");
				System.out.print("\tSelection: ");
				do
				{
					try
					{
						userIn = scan.nextInt();
					} catch(InputMismatchException e)
					{
						System.out.print("\tSelection: ");
						scan.nextLine();
					}
				}while(userIn == 0);
				
				scan.nextLine();
				
				if(userIn > 8 || userIn < 0 && userIn != -99)
				{
					System.out.println("\tInvalid Selection Try Again");
					continue;
				}
				
				editCustomerInfo(userIn, cstAccountNumber, scan);
				
			}while(userIn != -99);
			
		}while(index != -99);
		
		//scan2.close();
	}
	
	public static void editCustomerInfo(int userIn, long accNo, Scanner scan)
	{		
		int i;
		
		for(i = 0; i < userList.size(); i++)
		{
			if(userList.get(i).getAccountNumber() == accNo)
				break;
		}
		
		switch(userIn)
		{
		case 1: 
			System.out.print("\n\tGenerating New 10 Digit Account Number");
			userList.get(i).setAccountNumber((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
			System.out.println("\n\tNew Account Number: " + userList.get(i).getAccountNumber());

			break;
		case 2: 
			System.out.print("\n\tDirectly Edit Account Balance\n\tEnter New Account Balance: ");
			userList.get(i).setAccountBalance(scan.nextLong());
			scan.nextLine();

			break;
		case 3:
			System.out.print("\n\tDirectly Edit Username\n\tEnter New Username: ");
			userList.get(i).setUsername(scan.nextLine());

			break;
		case 4:
			System.out.print("\n\tDirectly Edit Password\n\tEnter New Password: ");
			userList.get(i).setPassword(scan.nextLine());

			break;
		case 5:
			System.out.print("\n\tDirectly Edit Phone Number\n\tEnter New Phone Number: ");

			userList.get(i).setPhoneNumber(scan.nextLong());
			scan.nextLine();

			break;
		case 6:
			System.out.print("\n\tDirectly Email Address\n\tEnter New Email Address: ");
			userList.get(i).setEmail(scan.nextLine());

			break;
		case 7:
			System.out.print("\n\tDirectly Edit SSN\n\tEnter New SSN: ");
			userList.get(i).setSsn(scan.nextInt());
			scan.nextLine();

			break;
		case 8:
			System.out.println("\n\tJoint Account Status is " + userList.get(i).isJointAccount());

			break;
		case -99: System.out.println("\tExit Customer");
			break;
			
		default: System.out.println("yeer");
		}
		
		writeToFile(userList);
	}
	
	public static void approveApp(long accNo, boolean b)
	{
		for(int i = 0; i < userList.size(); i++)
		{
			if(userList.get(i).getAccountNumber() == accNo)
			{
				userList.get(i).setApproved(b);
				
				if(b)
					System.out.println(userList.get(i).getFirstName() + " has been APPROVED!");
				else
				{
					System.out.println(userList.get(i).getFirstName() + " has been DENIED!");
					userList.remove(i);
				}
				
				writeToFile(userList);
				break;
			}
		}
	}
	
	public static void createNewEmployee() {
		userList.add(new User("Monika", "Sid", 1234));  //Employee ID: 
		
		userList.add(new User("Andrew", "Kay", 4589));  //Employee ID: 
		
		userList.add(new User("Rohan", "Pat", 6352));  //Employee ID: 
		
		userList.add(new User("Nico", "Something", 0102));  //Employee ID: 
		
		writeToFile(userList);
	}
	
//---------------------------------------------------------------------------------------------PRINT FUNCTION----------------------------------------------------------------------------------------------------------------------

	public static void printCustomers(ArrayList<User> cstList) {
		if(cstList.size() == 0)
			System.out.println("\tNo Customers in List");
		
		for(int i = 0; i < cstList.size(); i++)
			
			System.out.println("\t[" + (i + 1) + "] " + cstList.get(i).getFirstName() + " " + cstList.get(i).getLastName() + " " + cstList.get(i).getAccountNumber());
	}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//---------------------------------------------------------------------------------------------SERIALIZATION-----------------------------------------------------------------------------------------------------------------------
	
	public static void writeToFile(ArrayList<User> list)
	{
		try {
			FileOutputStream fout = new FileOutputStream("files/data.ser");
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(list);
			out.flush();
			out.close();
			
			//System.out.println("List saved successfully!");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public static boolean readFromFile()
	{
		try {
			FileInputStream fin = new FileInputStream("files/data.ser");
			ObjectInputStream in = new ObjectInputStream(fin);
			userList = (ArrayList<User>) in.readObject();
			in.close();
			
			//System.out.println("File read successfully!");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Empty file. Nothing to read.");
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace(); 
		}
		return true;
	}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//--------------------------------------------------------------------------------------ALL METHODS PRETAINING TO ADMIN------------------------------------------------------------------------------------------------------------

	public static void adminInterface(Scanner scan) {
		ArrayList<User> pendingApps = new ArrayList<User>();
		
		int index = 0, pendingIndex = 0, status = 0, i = 0;
		
		for(int z = 0; z < userList.size(); z++)
		{
			
			if(!userList.get(z).isApproved())
				pendingApps.add(userList.get(z));
		}
		
		while(pendingApps.size() > 0)
		{
			System.out.println("** ATTENTION THERE ARE " + pendingApps.size() + " PENDING APPLICATION(S) THAT NEEDS TO BE APPROVED/DENIED **\n**          PLEASE APPROVE/DENY THESE APPLICATIONS BEFORE CONTINUING           **\n");
			System.out.println("\tList of Pending Applications");
			System.out.println("\t----------------------------");
			printCustomers(pendingApps);
			System.out.println("\n\t[-99] Skip Pending Applications");
			
			System.out.print("\n\tIndex: ");
			do
			{
				try
				{
					pendingIndex = scan.nextInt();
				} catch(InputMismatchException e)
				{
					System.out.print("\tIndex: ");
					scan.nextLine();
				}
			}while(pendingIndex == 0);
			scan.nextLine();
			
			if(pendingIndex == -99)
				break;
			
			if(pendingIndex > pendingApps.size() || pendingIndex < 0)
			{
				System.out.println("\tInvalid Selection Try Again");
				continue;
			}
			
			System.out.print("[1] Approve OR [2] Deny " + pendingApps.get(pendingIndex - 1).getFirstName() + " | Selection: "); //User Validation needs to be done here
			do
			{
				try
				{
					status = scan.nextInt();
				} catch(InputMismatchException e)
				{
					System.out.print("\tSelection: ");
					scan.nextLine();
				}
			}while(status == 0);
			scan.nextLine();
			
			if(status == 1)
			{
				System.out.println("Name: " + pendingApps.get(pendingIndex - 1).getFirstName() + " Acc No: " + pendingApps.get(pendingIndex - 1).getAccountNumber());
				approveApp(pendingApps.get(pendingIndex - 1).getAccountNumber(), true);
				pendingApps.remove(pendingIndex - 1);
			}
			
			if(status == 2)
			{
				approveApp(pendingApps.get(pendingIndex - 1).getAccountNumber(), false);
				pendingApps.remove(pendingIndex - 1);
			}
		}
		
		
		do
		{
			ArrayList<User> allApproved = new ArrayList<User>();
			
			for(i = 0; i < userList.size(); i++)
			{
				if(!userList.get(i).isAdmin())
				{
					allApproved.add(userList.get(i));
				}
			}
			
			System.out.println("\n\t    All Active Users\n\t------------------------");

			if(allApproved.size() == 0)
				System.out.println("\tNo Members in List");
			
			for(i = 0; i < userList.size(); i++)
			{
				if(userList.get(i).isEmployee())
					System.out.println("\t[" + (i + 1) + "] " + userList.get(i).getFirstName() + " " + userList.get(i).getLastName() + "\t\t*EMPLOYEE*");
				else if(!userList.get(i).isAdmin())
					System.out.println("\t[" + (i + 1) + "] " + userList.get(i).getFirstName() + " " + userList.get(i).getLastName() + "\t\t" + userList.get(i).getAccountNumber());
				else
					System.out.println("\t[" + (i + 1) + "] ADMIN - No action applicable");
			}
			
			System.out.println("\n\t[-99] Admin Log Out");
			
			int userIn;
			System.out.print("\n\tIndex: ");
			do
			{
				try
				{
					index = scan.nextInt();
				} catch(InputMismatchException e)
				{
					System.out.print("\tIndex: ");
					scan.nextLine();
				}
			}while(index == 0);
			scan.nextLine();
			
			if(index == -99) //Admin Log Out
				break;
			
			if(index > allApproved.size() || index < 0)
			{
				System.out.println("\tInvalid Selection Try Again");
				continue;
			}
			
			if(!userList.get(index - 1).isAdmin())
			{
				do
				{
					userIn = 0;
					System.out.println("\n\tIndex: " + userList.get(index - 1).printName());
					long cstAccountNumber = userList.get(index - 1).getAccountNumber();
					
					if(cstAccountNumber > 100000)
					{
						System.out.println(userList.get(index - 1).customerToString());
						System.out.println("\n\t[9] Delete Account");
						System.out.println("\n\t[-99] Back to Menu");
						
						System.out.println("\nSelect an option to edit customer information...");
						System.out.print("\tSelection: ");
						do
						{
							try
							{
								userIn = scan.nextInt();
							} catch(InputMismatchException e)
							{
								System.out.print("\tSelection: ");
								scan.nextLine();
							}
						}while(userIn == 0);
						scan.nextLine();
						boolean b = adminEdit(userIn, cstAccountNumber, scan);
						if(b)
							break;
					}
					else if(userList.get(index - 1).isEmployee())
					{
						System.out.println(userList.get(index - 1).employeeToString());
						System.out.println("\n\t[9] Delete Account");
						System.out.println("\n\t[-99] Back to Menu");
						
						System.out.println("\nSelect an option to edit employee information...");
						System.out.print("\tSelection: ");
						do
						{
							try
							{
								userIn = scan.nextInt();
							} catch(InputMismatchException e)
							{
								System.out.print("\tIndex: ");
								scan.nextLine();
							}
						}while(userIn == 0);
						scan.nextLine();
						boolean b = adminEdit(userIn, cstAccountNumber, scan);
						if(b)
							break;
					}
					
					
				}while(userIn != -99);
			}
		}while(index != -99);
	}
	
	public static boolean adminEdit(int userIn, long accNo, Scanner scan)
	{
		int i;
		
		for(i = 0; i < userList.size(); i++)
		{
			if(userList.get(i).getAccountNumber() == accNo)
				break;
		}
		
		switch(userIn)
		{
		case 1: 
			System.out.print("\n\tGenerating New 5 Digit Account Number");
			userList.get(i).setAccountNumber((long) Math.floor(Math.random() * 90_000L) + 10_000L);
			System.out.println("\n\tNew Account Number: " + userList.get(i).getAccountNumber());
			break;
			
		case 2:
			System.out.print("\n\tDirectly Edit Username\n\tEnter New Username: ");
			userList.get(i).setUsername(scan.nextLine());
			break;
		case 3:
			System.out.print("\n\tDirectly Edit Password\n\tEnter New Password: ");
			userList.get(i).setPassword(scan.nextLine());
			break;
			
		case 4:
			System.out.print("\n\tDirectly Edit Phone Number\n\tEnter New Phone Number: ");
			userList.get(i).setPhoneNumber(scan.nextLong());
			scan.nextLine();
			break;
			
		case 5:
			System.out.print("\n\tDirectly Email Address\n\tEnter New Email Address: ");
			userList.get(i).setEmail(scan.nextLine());
			break;
			
		case 6:
			System.out.print("\n\tDirectly Edit SSN\n\tEnter New SSN: ");
			userList.get(i).setSsn(scan.nextInt());
			scan.nextLine();
			break;
			
		case 7: 
			if(!userList.get(i).isEmployee())
			{
				System.out.print("\n\tDirectly Edit Account Balance\n\tEnter New Account Balance: ");
				userList.get(i).setAccountBalance(scan.nextLong());
				scan.nextLine();
			}
			else
				System.out.println("\tInvalid Selection Try Again!");
			break;
			
		case 9:
			System.out.print("Are you sure you would like to permanetly delete " + userList.get(i).getFirstName() + "'s account? Enter 'Y' to delete!\n\tSelection: ");
			scan.nextLine();
			String str = scan.nextLine();
			if(str.charAt(0) == 'Y' || str.charAt(0) == 'y')
			{
				userList.remove(i);
				return true;
			}
			break;
	
		case -99: System.out.println("\tReturn to Menu");
			break;
			
		default: System.out.println("\tInvalid Selection Try Again!");
		}
		
		writeToFile(userList);
		return false;
		//scan2.close();
	}
	
	public static void createAdmin() {
		userList.add(new User("BANK", "ADMIN"));
		writeToFile(userList);
	}
	
}