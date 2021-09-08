package bank;

public class Employee {
	String firstName;
	String lastName;
	
	long employeeid;
	
	Employee(String first, String last)
	{
		this.firstName = first;
		this.lastName = last;
		this.employeeid = (long) Math.floor(Math.random() * 90_000L) + 10_000L;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(long employeeid) {
		this.employeeid = employeeid;
	}
	
	public String toString()
	{
		return "Employee Name: " + getFirstName().toUpperCase() + " " + getLastName().toUpperCase() + "\nEmployee ID: " + getEmployeeid();
	}
}
