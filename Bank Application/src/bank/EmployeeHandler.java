package bank;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeHandler extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Scanner scan = new Scanner(System.in);
	
	EmployeeHandler()
	{
		
	}
	
	public ArrayList<User> employeeInterface(int x, ArrayList<User> userList)
	{
		ArrayList<User> pendingApps = new ArrayList<User>();
		
		int index = 0, pendingIndex = 0, status = 0, i = 0;
		
		if(!userList.get(x).isApproved())
		{
			System.out.println("Your account is still pending approval please try again later.");
			return userList;
		}
		
		for(int z = 0; z < userList.size(); z++)
		{
			
			if(!userList.get(z).isApproved() && !userList.get(z).isEmployee())
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
				userList = approveApp(pendingApps.get(pendingIndex - 1).getAccountNumber(), true, userList);
				pendingApps.remove(pendingIndex - 1);
			}
			
			if(status == 2)
			{
				userList = approveApp(pendingApps.get(pendingIndex - 1).getAccountNumber(), false, userList);
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
			System.out.println("\n\t[-99] Employee Log Out (Save Changes)");
			
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
				
				userList = editCustomerInfo(userIn, cstAccountNumber, userList);
				
			}while(userIn != -99);
			
		}while(index != -99);
		
		//scan2.close();
		return userList;
	}
	
	public ArrayList<User> editCustomerInfo(int userIn, long accNo, ArrayList<User> userList)
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
		
		//writeToFile(userList);
		return userList;
	}
	
	public ArrayList<User> approveApp(long accNo, boolean b, ArrayList<User> userList)
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
				
				//writeToFile(userList);
				return userList;
			}
		}
		return userList;
	}
	
	public static void printCustomers(ArrayList<User> cstList) {
		if(cstList.size() == 0)
			System.out.println("\tNo Customers in List");
		
		for(int i = 0; i < cstList.size(); i++)
			
			System.out.println("\t[" + (i + 1) + "] " + cstList.get(i).getFirstName() + " " + cstList.get(i).getLastName() + " " + cstList.get(i).getAccountNumber());
	}
	
	public User newEmployee()
	{
		String first, last;
		int ssn = 0;
			
		System.out.println("Thanks for choosing The Bank! Please fill out all information on this application\n\tYour Application is subject to pending approval...\n");
		
		System.out.print("First Name: ");
		first = (scan.nextLine());
		System.out.print("Last Name: ");
		last = (scan.nextLine());
		
		try
		{
			System.out.print("Last 4 of SSN(Only Numerics Accepted): ");
			ssn = (scan.nextInt());
			scan.nextLine();
		} catch(InputMismatchException e)
		{
			System.out.println("\n*** Invalid input format an Admin will help you correct this later. ***\n***                         Please Continue!                           ***");
			scan.nextLine();
		}
		
		User newEmp = new User(first, last, ssn, false, true, false);
		
		return newEmp;
	}
}
