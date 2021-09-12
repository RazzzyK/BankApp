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
	static ArrayList<User> master = new ArrayList<User>();
	
	static int userInput;
	static CustomerHandler cst = new CustomerHandler();
	static EmployeeHandler emp = new EmployeeHandler();
	static AdminHandler ad = new AdminHandler();
	static ArrayList<User> userList = new ArrayList<User>();
	
	static int[] temp = new int[] {1, 2, 3};


	public static void main(String[] args) throws IOException {
		
		JDBCBank test = new JDBCBank();
		
		
		if(!readFromFile())
		{
			
			userList.add(ad.createAdmin());
			userList.add(emp.newEmployee());
		}
		test.insertDB(userList);
		
//		do
//		{
//			System.out.print("\n\t\tWelcome to the Bank!\n\tPlease choose from the following menu\n\n[1] Login\t[2] Create New Account\t[-99] Quit\n\t\tSelection: ");
//			do
//			{
//				try
//				{
//					userInput = scan.nextInt();
//					scan.nextLine();
//				} catch(InputMismatchException e)
//				{
//					System.out.print("\t\tSelection: ");
//					scan.nextLine();
//				}
//			}while(userInput == 0);
//			
//			System.out.println();
//		
//			if(userInput == 1)
//				login(scan);
//			else if(userInput == 2)
//			{
//				askTypeOfAccount(scan);
//				continue;
//				//userList.add(new User());
//				//writeToFile(userList);
//			}
//			else if (userInput == -99)
//				System.out.println("********** Thanks for using The Bank! **********");
//			else
//				System.out.println("Please limit your input to only 1, 2 or 3");
//			
//		}while(userInput != -99);
		
		scan.close();
	}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
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
						System.out.println("\n\t-- Access Granted --\n\t Welcome " + userList.get(i).getFirstName() + " " +userList.get(i).getLastName());
						
						if(userList.get(i).isAdmin())
						{
							userList = ad.adminInterface(userList);
							found = true;
							writeToFile(userList);
							break;
						}
						if(userList.get(i).isEmployee())
						{
							userList = emp.employeeInterface(i, userList);
							found = true;
							writeToFile(userList);
							break;
						}
						else 
						{
							userList = cst.customerInterface(i, userList);
							writeToFile(userList);
						}
						
						found = true;
						break;
					}
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
		
		writeToFile(userList);
	}

	public static void askTypeOfAccount(Scanner scan)
	{
		int userIn = 0;
		System.out.println("Please choose the type of account you would like to create...\n");
		System.out.println("[1] New Customer\t[2] New Employee\t[3] -99 Cancel");
		
		
		do
		{
			System.out.print("\t\tSelection: ");
			try
			{
				userIn = scan.nextInt();
				scan.nextLine();
			} catch(InputMismatchException e)
			{
				System.out.print("\t\tSelection: ");
				scan.nextLine();
			}
		}while(userIn != 1 && userIn != 2 && userIn != -99);
		
		if(userIn == 1)
		{
			//CustomerHandler cst = new CustomerHandler();
			userList.add(cst.newCustomer());

			System.out.println("\n\tNew User Created Successfully!\n\t Subject to Pending Approval!");
			return;
		}
			
		else if(userIn == 2)
		{
			userList.add(emp.newEmployee());
		}
		else
			System.out.println("\t\tBack to Login!");
		
		writeToFile(userList);
			
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

}
