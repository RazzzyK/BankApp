package bank;

import java.io.Serializable;

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

	
	
	
	public User() {
		super();
		this.username = "";
		this.password = "";
		this.firstName = "";
		this.lastName = "";
		this.ssn = 0;
		this.email = "";
		this.phoneNumber = 0;
		this.isJointAccount = false;
		this.isApproved = false;
		this.isAdmin = false;
		this.isEmployee = false;
		this.accountBalance = 0;
		this.accountNumber = 0;
		this.jointAccNo = 0; //This should be an array of accno
	}

	public User(String first, String last, int ssn, Boolean app, Boolean emp, Boolean ad)  //Creating a new employee
	{
		this.firstName = first;
		this.lastName = last;
		
		this.username = first + "." + last;
		this.password = first + "" + last.charAt(0);
		
		this.accountNumber = (long) Math.floor(Math.random() * 90_000L) + 10_000L;  //setter method for generating a new unique id
		
		this.email = first + "." + last + "@bank.com";
		this.phoneNumber = 18001234560L;
		this.ssn = ssn;
		
		this.isJointAccount = false;
		this.isEmployee = emp;
		this.isApproved = app;
		this.isAdmin = ad;
	}
	
	public User(String first, String last)  //Creating a new Admin
	{
		this.firstName = first;
		this.lastName = last;
		
		this.username = "Admin";
		this.password = "Admin";
		
		this.accountNumber = -111;
		
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
		return "\t[1] Account Number = " + accountNumber + "\n\t[2] Account Balance = $" + getAccountBalance() + "\n\t[3] Username = " + username + "\n\t[4] Password = " + password + "\n\t[5] PhoneNumber = " + phoneNumber + "\n\t[6] Email = " + email +  "\n\t[7] SSN = " + ssn + "\n\t[8] Joint Account Status"
				;
	}
	
	

}
