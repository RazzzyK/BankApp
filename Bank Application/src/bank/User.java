package bank;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	/**
	 * 
	 */
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int ssn;
	private String email;
	private long phoneNumber;
	
	private boolean isJointAccount;
	private boolean isApproved;
	private boolean isAdmin;
	private boolean isEmployee;
	
	private double accountBalance;
	private long accountNumber;
	private long jointAccNo;
	
	private String temp;	

	public User()  //Creating a new Customer
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Thanks for choosing The Bank! Please fill out all information on this application\n\tYour Application is subject to pending approval...\n");
		
		System.out.print("First Name: ");
		setFirstName(scan.nextLine());
		System.out.print("Last Name: ");
		setLastName(scan.nextLine());
		
		try
		{
			System.out.print("Phone Number(Only Numerics Accepted): ");
			setPhoneNumber(scan.nextLong());
			scan.nextLine();
		} catch(InputMismatchException e)
		{
			System.out.println("\n*** Invalid input format an employee will help you correct this later. ***\n***                         Please Continue!                           ***");
			scan.nextLine();
		}
		
		System.out.print("Email Address: ");
		setEmail(scan.nextLine());
		
		try
		{
			System.out.print("Last 4 of SSN(Only Numerics Accepted): ");
			setSsn(scan.nextInt());
			scan.nextLine();
		} catch(InputMismatchException e)
		{
			System.out.println("\n*** Invalid input format an employee will help you correct this later. ***\n***                         Please Continue!                           ***");
			scan.nextLine();
		}
		
		System.out.print("Username: ");
		setUsername(scan.nextLine());
		System.out.print("Password: ");
		setPassword(scan.nextLine());
		
		setAccountNumber((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
		
		System.out.print("Will this be a joint account? Please enter 'y' for yes or anything else for no\nSelection: ");
		temp = scan.nextLine();
		if(temp.equals("y") || temp.equals("Y"))
		{
			setJointAccount(true);
			System.out.print("Enter existing account number: ");
			setJointAccNo(scan.nextLong());
		}
		else
		{
			setJointAccount(false);
			setJointAccNo(0);
		}
		
		setApproved(false);
		setAccountBalance(0);
		setEmployee(false);
		setAdmin(false);
		
		//scan.close(); cannot close scanner due to System.in
	}
	
	public User(String first, String last, int ssn)  //Creating a new employee
	{
		this.firstName = first;
		this.lastName = last;
		
		this.username = first + "." + last;
		this.password = first + "" + last.charAt(0);
		
		this.accountNumber = (long) Math.floor(Math.random() * 90_000L) + 10_000L;
		
		this.email = first + "." + last + "@bank.com";
		this.phoneNumber = 18001234560L;
		this.ssn = ssn;
		
		this.isJointAccount = false;
		this.isEmployee = true;
		this.isApproved = true;
		this.isAdmin = false;
	}
	
	public User(String first, String last)  //Creating a new Admin
	{
		this.firstName = first;
		this.lastName = last;
		
		this.username = "Admin";
		this.password = "Admin";
		
		this.accountNumber = -1;
		
		this.email = "admin@bank.com";
		this.phoneNumber = 18001234560L;
		this.ssn = 0000;
		
		this.isJointAccount = false;
		this.isEmployee = false;
		this.isApproved = true;
		this.isAdmin = true;
	}
	
	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	
	public String getUsername() {
		return username;
	}

	public  void setUsername(String username) {
		this.username = username;
	}

	public  String getPassword() {
		return password;
	}

	public  void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public  String getLastName() {
		return lastName;
	}

	public  void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSsn() {
		return ssn;
	}

	public  void setSsn(int ssn) {
		this.ssn = ssn;
	}

	public  String getEmail() {
		return email;
	}

	public  void setEmail(String email) {
		this.email = email;
	}

	public  long getPhoneNumber() {
		return phoneNumber;
	}

	public  void setPhoneNumber(long l) {
		this.phoneNumber = l;
	}
	
	public  boolean isJointAccount() {
		return isJointAccount;
	}

	public  void setJointAccount(boolean isJointAccount) {
		this.isJointAccount = isJointAccount;
	}

	public  boolean isApproved() {
		return isApproved;
	}

	public  void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public  String getAccountBalance() {
		String str = String.format("%.2f", accountBalance);

		return str;
	}

	public  void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public  long getAccountNumber() {
		return accountNumber;
	}

	public  void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String printName()
	{
		return getFirstName() + " " + getLastName() + "\n";
		
	}
	
	public long getJointAccNo() {
		return jointAccNo;
	}

	public void setJointAccNo(long jointAccNo) {
		this.jointAccNo = jointAccNo;
	}

	public String employeeToString() {
		return "\t[1] Account Number = " + accountNumber + "\n\t[2] Username = " + username + "\n\t[3] Password = " + password + "\n\t[4] PhoneNumber = " + phoneNumber + "\n\t[5] Email = " + email +  "\n\t[6] SSN = " + ssn
				;
	}
	
	public String customerToString() {
		return "\t[1] Account Number = " + accountNumber + "\n\t[2] Username = " + username + "\n\t[3] Password = " + password + "\n\t[4] PhoneNumber = " + phoneNumber + "\n\t[5] Email = " + email +  "\n\t[6] SSN = " + ssn + "\n\t[7] Account Balance = $" + accountBalance
				;
	}

	@Override
	public String toString() {
		return "\t[1] Account Number = " + accountNumber + "\n\t[2] Account Balance = $" + accountBalance + "\n\t[3] Username = " + username + "\n\t[4] Password = " + password + "\n\t[5] PhoneNumber = " + phoneNumber + "\n\t[6] Email = " + email +  "\n\t[7] SSN = " + ssn + "\n\t[8] Joint Account Status"
				;
	}
	
	

}
