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
	
	public void adminInterface(JDBCBank ref) {
		ArrayList<User> pendingApps = new ArrayList<User>();
		
		int index = 0, pendingIndex = 0, status = 0;
		
		pendingApps = ref.getNotApproveTable();
		

		while(pendingApps != null && pendingApps.size() > 0)
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
				approveApp(true, pendingApps.get(pendingIndex - 1), ref);
				pendingApps.remove(pendingIndex - 1);
			}
			
			if(status == 2)
			{
				approveApp(false, pendingApps.get(pendingIndex - 1), ref);
				pendingApps.remove(pendingIndex - 1);
			}
		}
		
		
		do
		{
			
			System.out.println("\n\t    All Active Users\n\t------------------------");
			
			ArrayList<User> cstList = ref.getCustomerTable();
			ArrayList<User> empList = ref.getEmployeeTable();
			
			if(empList == null && cstList == null)
				System.out.println("\tCould not find anyone in the lists");
				
			if(cstList == null && empList != null)
				cstList = empList;
			
			if(empList != null && cstList != null)
				cstList.addAll(empList);
			
			printUsers(cstList);
			
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
			
			User edit = cstList.get(index - 1);
			do
			{
				userIn = 0;
				
				System.out.println("\n\tIndex: " + edit.printName());
				long cstAccountNumber = edit.getAccountNumber();
				
				if(cstAccountNumber > 100000)
				{
					System.out.println(edit.customerToString());
					System.out.println("\n\t[9] Delete Account");
					System.out.println("\n\t[-99] Back to Menu (Save Changes)");
					
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
					
					edit = adminEdit(userIn, edit, scan, ref);
					
					if(edit == null)
						break;
				}
				else if(edit.isEmployee())
				{
					System.out.println(edit.employeeToString());
					System.out.println("\n\t[9] Delete Account");
					System.out.println("\n\t[-99] Back to Menu (Save Changes)");
					
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
					
					edit = adminEdit(userIn, edit, scan, ref);
					if(edit == null)
						break;
				}
				
			}while(userIn != -99);
			
			if(edit.isEmployee())
				ref.updateEmployee(edit);
			else
				ref.updateCustomer(edit);
			
		}while(index != -99);
		
	}
	
	public static User adminEdit(int userIn, User u, Scanner scan, JDBCBank ref)
	{
		switch(userIn)
		{
		case 1: 
			if(!u.isEmployee())
			{
				System.out.println("Account number can not be changed right now...");
				//long oldID = u.getAccountNumber();
				//System.out.print("\n\tGenerating New 10 Digit Account Number");
				//u.setAccountNumber((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
				
				//ref.updateID(u, oldID);
			}
			else
			{
				System.out.println("Account number can not be changed right now...");
				//long oldID = u.getAccountNumber();
				//System.out.print("\n\tGenerating New 5 Digit Account Number");
				//u.setAccountNumber((long) Math.floor(Math.random() * 90_000L) + 10_000L);
				
				//ref.updateID(u, oldID);
			}
			System.out.println("\n\tCurrent Account Number: " + u.getAccountNumber());
			
			break;
			
		case 2:
			System.out.print("\n\tDirectly Edit Phone Number\n\tEnter New Phone Number: ");
			u.setPhoneNumber(scan.nextLong());
			scan.nextLine();
			break;
			
		case 3:
			System.out.print("\n\tDirectly Email Address\n\tEnter New Email Address: ");
			u.setEmail(scan.nextLine());
			break;
			
		case 4:
			System.out.print("\n\tDirectly Edit SSN\n\tEnter New SSN: ");
			u.setSsn(scan.nextInt());
			scan.nextLine();
			break;
			
		case 5: 
			if(!u.isEmployee())
			{
				System.out.print("\n\tDirectly Edit Account Balance\n\tEnter New Account Balance: ");
				u.setAccountBalance(scan.nextLong());
				scan.nextLine();
			}
			else
				System.out.println("\tInvalid Selection Try Again!");
			break;
			
		case 9:
			System.out.print("Are you sure you would like to permanetly delete " + u.getFirstName() + "'s account? Enter 'Y' to delete!\n\tSelection: ");
			//scan.nextLine();
			String str = scan.nextLine();
			if(str.charAt(0) == 'Y' || str.charAt(0) == 'y')
			{
				if(u.isEmployee())
					ref.deleteFromEmployeeTable(u);
				else
					ref.deleteFromCustomerTable(u);
				return null;
			}
			break;
	
		case -99: System.out.println("\tReturn to Menu");
			break;
			
		default: System.out.println("\tInvalid Selection Try Again!");
		}
		
		return u;
	}
	
	public static void approveApp( boolean b, User u, JDBCBank ref)
	{
		if(b)
		{
			if(u.getAccountNumber() < 100000)
				ref.employeeApproved(u);
			else
				ref.customerApproved(u);
		}
		else
			if(u.getAccountNumber() < 100000)
				ref.employeeDenied(u);
			else
				ref.customerDenied(u);
	}
	
	public User createAdmin() {
		User newAdmin = new User("BANK", "ADMIN");
		ArrayList<String> splitObj = new ArrayList<>();
		
		splitObj.add(newAdmin.getUsername());
		splitObj.add(newAdmin.getPassword());
		splitObj.add(newAdmin.getFirstName());
		splitObj.add(newAdmin.getLastName());
		splitObj.add(newAdmin.getSsn() + "");
		splitObj.add(newAdmin.getEmail());
		splitObj.add(newAdmin.getPhoneNumber() + "");
		splitObj.add(newAdmin.getAccountBalance());
		
		return newAdmin;
	}
	
	public static void printUsers(ArrayList<User> cstList) {
		if(cstList.size() == 0)
			System.out.println("\tNo Customers in List");
		//System.out.println(cstList.get(1).getFirstName());
		for(int i = 0; i < cstList.size(); i++)
			
			System.out.println("\t[" + (i + 1) + "] " + cstList.get(i).getFirstName() + " " + cstList.get(i).getLastName() + " " + cstList.get(i).getAccountNumber());
	}
}
