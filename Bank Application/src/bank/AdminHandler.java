package bank;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminHandler extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Scanner scan = new Scanner(System.in);

	AdminHandler()
	{
		
	}
	
	public ArrayList<User> adminInterface(ArrayList<User> userList) {
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
			printUsers(pendingApps);
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
				if(pendingIndex == 0)
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
				if(userList.get(i).isEmployee() && userList.get(i).isApproved())
					System.out.println("\t[" + (i + 1) + "] " + userList.get(i).getFirstName() + " " + userList.get(i).getLastName() + "\t\t*EMPLOYEE*");
				else if(!userList.get(i).isAdmin() && userList.get(i).isApproved())
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
						boolean b = adminEdit(userIn, cstAccountNumber, userList);
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
						boolean b = adminEdit(userIn, cstAccountNumber, userList);
						if(b)
							break;
					}
					
					
				}while(userIn != -99);
			}
		}while(index != -99);
		
		return userList;
	}
	
	public static boolean adminEdit(int userIn, long accNo, ArrayList<User> userList)
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
		
		//writeToFile(userList);
		return false;
		//scan2.close();
	}
	
	public static ArrayList<User> approveApp(long accNo, boolean b, ArrayList<User> userList)
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
	
	public User createAdmin() {
		User newAdmin = new User("BANK", "ADMIN");
		
		//writeToFile(userList);
		
		return newAdmin;
	}
	
	public void callJDBC(User u) {
		ArrayList<String> splitObj = new ArrayList<>();
		
		splitObj.add(u.getUsername());
		splitObj.add(u.getPassword());
		splitObj.add(u.getFirstName());
		splitObj.add(u.getLastName());
		splitObj.add(u.getSsn() + "");
		splitObj.add(u.getEmail());
		splitObj.add(u.getPhoneNumber() + "");
		//splitObj.add(u.isApproved());
		//splitObj.add(u.isEmployee());
		//splitObj.add(u.isAdmin());
		splitObj.add(u.getAccountBalance());
		
		
	}
	
	public static void printUsers(ArrayList<User> cstList) {
		if(cstList.size() == 0)
			System.out.println("\tNo Customers in List");
		
		for(int i = 0; i < cstList.size(); i++)
			
			System.out.println("\t[" + (i + 1) + "] " + cstList.get(i).getFirstName() + " " + cstList.get(i).getLastName() + " " + cstList.get(i).getAccountNumber());
	}
}
