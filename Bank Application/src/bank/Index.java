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
	static JDBCBank ref = new JDBCBank();
	static CustomerHandler cst = new CustomerHandler();
	static EmployeeHandler emp = new EmployeeHandler();
	static AdminHandler ad = new AdminHandler();
	static ArrayList<User> userList = new ArrayList<User>();
	
	static int[] temp = new int[] {1, 2, 3};


	public static void main(String[] args) throws IOException {
		//readFromFile();
		//User u = ad.createAdmin();
		//ref.addToMaster(u);
		//ref.createTable();
		
		//System.out.println(ref.findInMaster("Admin"));
		//ref.getNotApproveTable();
		
//		if(!readFromFile())  //Needs to be in a try catch for EOF Exception
//		{
//			User u = ad.createAdmin();
//			ref.addToMaster(u);
//			userList.add(u);
//			u = emp.newEmployee();
//			userList.add(u);
//			ref.addToMaster(u);
//			
//			writeToFile(userList);
//		}
		
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
				askTypeOfAccount(scan);
				continue;

			}
			else if (userInput == -99)
				System.out.println("********** Thanks for using The Bank! **********");
			else
				System.out.println("Please limit your input to only 1, 2 or 3");
			
		}while(userInput != -99);
		
		ref.closeConn();
		scan.close();
	}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	private static void login(Scanner scan) {
		String loggedIn = "", part1 = "", part2 = "", entireUser = "";
		int counter = 0;
		String userN = "", passW = "";
		do
		{
			System.out.println("\tPlease enter username and password. Case Sensitive.");
			System.out.print("Username: ");
			userN = scan.nextLine();
			System.out.print("Password: ");
			passW = scan.nextLine();
			
			loggedIn = ref.findInMaster(userN);
			if(loggedIn.equals("INVALID"))
			{
				System.out.println("---------Username or Password may be inccorrect.----------\n");
				counter++;
			}
			else
			{
				String[] parts = loggedIn.split(",");
				part1 = parts[0]; // password
				part2 = parts[1]; // user_id
			}
			
			if(counter >= 3)
			{
				System.out.println("---------Too Many Failed Attempts. Returning to Menu.---------");
				break;
			}
			
			if(part1.equals(passW) && part2.equals("-111"))
			{
				ad.adminInterface(ref);
				break;
			}
			
			if(part1.equals(passW))
			{
				if(part2.length() < 6)
				{
					entireUser = ref.findInEmployeeTable(part2);
					//System.out.println(entireUser);
					emp.employeeInterface(ref, entireUser);//should consider passing the part2 user_id
					break;
				}
				else
				{
					entireUser = ref.findInCustomerTable(part2);
					
					ref.updateCustomer(cst.customerInterface(entireUser, ref));
					break;
				}
			}
			
		}while(true);
		
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
			User u = cst.newCustomer();
			ref.addToMaster(u);
			ref.addToCustomerTable(u);
			
			//userList.add(u);
			writeToFile(userList);

			System.out.println("\n\tNew Customer Created Successfully!\n\t Subject to Pending Approval!");
			return;
		}
			
		else if(userIn == 2)
		{
			User u = emp.newEmployee();
			ref.addToMaster(u);
			ref.addToEmployeeTable(u);
			
			//userList.add(u);
			writeToFile(userList);
			
			System.out.println("\n\tNew Employee Created Successfully!\n\t Subject to Pending Approval!");
			return;
		}
		else
			System.out.println("\t\tBack to Login!");
		
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
