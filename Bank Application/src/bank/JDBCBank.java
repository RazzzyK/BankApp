package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCBank {
	
	private final String host = "jdbc:oracle:thin:@java-react-free-tier.csedppwlopoz.us-east-1.rds.amazonaws.com:1521:ORCL";
	private final String user = "Admin";                //Save in a flat file
	private final String password = "12345678";
	
	Connection conn;
	
	JDBCBank()
	{

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(host, user, password);
			//System.out.println("HIT!");
			
		}catch (ClassNotFoundException e)
		{
			System.out.println("Unable to load driver class");
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addTransaction(User u, String str)
	{
		java.util.Date today = new java.util.Date();
	    
		String safeInsert = "INSERT INTO TRANSACTIONS VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeInsert);
			
			push.setLong(1, u.getAccountNumber());
			push.setString(2, u.getFirstName() + " " + u.getLastName());
			push.setString(3, u.getAccountBalance());
			push.setDate(4, new java.sql.Date(today.getTime()));
			push.setString(5, str);
			
			push.executeUpdate();
			
			System.out.println("\tSucessfully Inserted into TRANSACTIONS Table");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void addToMaster(User u)
	{
		String safeInsert = "INSERT INTO MASTER VALUES (?, ?, ?)";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeInsert);
			
			push.setLong(1, u.getAccountNumber());
			push.setString(2, u.getUsername());
			push.setString(3, u.getPassword());
			
			push.executeUpdate();
			
			System.out.println("\tSucessfully Inserted into MASTER");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void addToNotApproved(User u)
	{
		String safeInsert = "INSERT INTO NOT_APPROVED VALUES (?, ?, ?)";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeInsert);
			
			push.setLong(1, u.getAccountNumber());
			push.setString(2, u.getFirstName());
			push.setString(3, u.getLastName());
			
			push.executeUpdate();
			
			System.out.println("\tSucessfully Inserted into NOT_APPROVED");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void addToCustomerTable(User u)
	{
		String safeInsert = "INSERT INTO CUSTOMER VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeInsert);
			
			push.setLong(1, u.getAccountNumber());
			push.setString(2, u.getFirstName());
			push.setString(3, u.getLastName());
			push.setInt(4, u.getSsn());
			push.setString(5, u.getEmail());
			push.setLong(6, u.getPhoneNumber());
			if(u.isApproved())
				push.setString(7, "Y");
			else
			{
				this.addToNotApproved(u);
				push.setString(7, "N");
			}
			push.setFloat(8, Float.parseFloat(u.getAccountBalance()));
			push.setInt(9, u.getDailyLimit());
			
			push.executeUpdate();
			
			System.out.println("\tSucessfully Inserted into CUSTOMER");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void addToEmployeeTable(User u)
	{
		String safeInsert = "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeInsert);
			
			push.setLong(1, u.getAccountNumber());
			push.setString(2, u.getFirstName());
			push.setString(3, u.getLastName());
			push.setInt(4, u.getSsn());
			push.setLong(5, u.getPhoneNumber());
			push.setString(6, u.getEmail());
			
			if(u.isApproved())
				push.setString(7, "Y");
			else
			{
				this.addToNotApproved(u);
				push.setString(7, "N");
			}
			
			push.executeUpdate();
			
			System.out.println("\tSucessfully Inserted into EMPLOYEE");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public String findInMaster(String userN)
	{
		String safeQuery = "Select * from MASTER where user_name=?";
		
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, userN);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return "INVALID";
			else
			{
				do
				{
					return(rs.getString(3) + "," + rs.getString(1));
				}while(rs.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "INVALID";
	}
	
	public String findInCustomerTable(String id)
	{
		String safeQuery = "Select * from CUSTOMER where user_id=?";
		
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, id);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return "INVALID";
			else
			{

				return(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) 
				+ "," + rs.getString(5) + "," + rs.getString(6) + "," + rs.getString(7) + "," + rs.getString(8) 
				+ "," + rs.getString(9));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String findInEmployeeTable(String id)
	{
		String safeQuery = "Select * from EMPLOYEE where user_id=?";
		
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, id);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return "INVALID";
			else
			{
				//do
				//{
					return(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) 
					+ "," + rs.getString(5) + "," + rs.getString(6) + "," + rs.getString(7));
					// have to do 10
				//}while(rs.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void updateCustomer(User u)
	{
		//String safeInsert = "INSERT INTO CUSTOMER VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String safeUpdate = "UPDATE CUSTOMER SET FIRST_NAME =?,LAST_NAME =?, SSN =?, EMAIL =?, PHONE_NO =?, IS_APPROVED =?, BALANCE =?, DAILY_LIMIT=? WHERE USER_ID =?";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeUpdate);
			
			push.setString(1, u.getFirstName());
			push.setString(2, u.getLastName());
			push.setInt(3, u.getSsn());
			push.setString(4, u.getEmail());
			push.setLong(5, u.getPhoneNumber());
			if(u.isApproved())
				push.setString(6, "Y");
			else
				push.setString(6, "N");
			push.setFloat(7, Float.parseFloat(u.getAccountBalance()));
			push.setInt(8, u.getDailyLimit());
			push.setLong(9, u.getAccountNumber());
			
			push.executeUpdate();
			
			System.out.println("\tSucessfuly Update");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void updateEmployee(User u)
	{
		String safeUpdate = "UPDATE EMPLOYEE SET FIRSTNAME =?, LASTNAME =?, SSN =?, PHONE =?, EMAIL =?, IS_APPROVED =? WHERE USER_ID =?";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeUpdate);
			
			push.setString(1, u.getFirstName());
			push.setString(2, u.getLastName());
			push.setInt(3, u.getSsn());
			push.setLong(4, u.getPhoneNumber());
			push.setString(5, u.getEmail());
			if(u.isApproved())
				push.setString(6, "Y");
			else
				push.setString(6, "N");
			push.setLong(7, u.getAccountNumber());
			
			push.executeUpdate();
			
			System.out.println("\tSucessfuly Update");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void updateID(User u, long oldID)
	{
		String safeUpdate;
		PreparedStatement push, push2;
		
		if(u.isEmployee())
		{
			safeUpdate = "UPDATE EMPLOYEE SET USER_ID =? WHERE USER_ID =?";
			try {
				
				push = conn.prepareStatement(safeUpdate);
				push.setLong(1, u.getAccountNumber());
				push.setLong(2, oldID);
				push.executeUpdate();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
		}
		else
		{
			safeUpdate = "UPDATE CUSTOMER SET USER_ID =? WHERE USER_ID =?";
			try {
				
				push2 = conn.prepareStatement(safeUpdate);
				push2.setLong(1, u.getAccountNumber());
				push2.setLong(2, oldID);
				push2.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		safeUpdate = "UPDATE MASTER SET USER_ID =? WHERE USER_ID =?";
		
		
		try {
			push = conn.prepareStatement(safeUpdate);
			
			push.setLong(1, u.getAccountNumber());
			push.setLong(2, oldID);
			push.executeUpdate();
			
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> getNotApproveTable()
	{
		String safeQuery = "Select * from NOT_APPROVED";
		
		PreparedStatement pull;
		
		try {
			pull = conn.prepareStatement(safeQuery);
			
			ResultSet rs = pull.executeQuery();
			
			if(!rs.next())
				return null;
			else
			{
				
				ArrayList<User> pendingApps = new ArrayList<User>();
				do
				{
					User u = new User();
					u.setAccountNumber(Long.parseLong(rs.getString(1)));
					u.setFirstName(rs.getString(2));
					u.setLastName(rs.getString(3));
					
					pendingApps.add(u);
					
				}while(rs.next());
				
				return pendingApps;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<User> getCustomerTable()
	{
		String safeQuery = "Select * from CUSTOMER WHERE IS_APPROVED =?";
		
		PreparedStatement pull;
		
		try {
			pull = conn.prepareStatement(safeQuery);
			pull.setString(1, "Y");
			
			ResultSet rs = pull.executeQuery();
			
			if(!rs.next())
				return null;
			else
			{
				ArrayList<User> cstList = new ArrayList<User>();
				do
				{
					User u = new User();
					u.setAccountNumber(Long.parseLong(rs.getString(1)));
					u.setFirstName(rs.getString(2));
					u.setLastName(rs.getString(3));
					u.setSsn(Integer.parseInt(rs.getString(4)));
					u.setEmail(rs.getString(5));
					u.setPhoneNumber(Long.parseLong(rs.getString(6)));
					
					if(rs.getString(7).equals("Y"))
						u.setApproved(true);
					else
						u.setApproved(false);
						
					u.setAccountBalance(Double.parseDouble(rs.getString(8)));
					u.setDailyLimit(Integer.parseInt(rs.getString(9)));
					cstList.add(u);
					
				}while(rs.next());
				
				return cstList;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<User> getEmployeeTable()
	{
		String safeQuery = "Select * from EMPLOYEE WHERE IS_APPROVED =?";
		
		PreparedStatement pull;
		
		try {
			pull = conn.prepareStatement(safeQuery);
			pull.setString(1, "Y");
			
			ResultSet rs = pull.executeQuery();
			
			if(!rs.next())
				return null;
			else
			{
				ArrayList<User> cstList = new ArrayList<User>();
				do
				{
					User u = new User();
					u.setAccountNumber(Long.parseLong(rs.getString(1)));
					u.setFirstName(rs.getString(2));
					u.setLastName(rs.getString(3));
					u.setSsn(Integer.parseInt(rs.getString(4)));
					u.setPhoneNumber(Long.parseLong(rs.getString(5)));
					u.setEmail(rs.getString(6));
					
					
					if(rs.getString(7).equals("Y"))
						u.setApproved(true);
					else
						u.setApproved(false);
					
					u.setEmployee(true);
						
					cstList.add(u);
					
				}while(rs.next());
				
				return cstList;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void customerApproved(User u)
	{
		String safeUpdate = "UPDATE CUSTOMER SET IS_APPROVED =? WHERE USER_ID =?";
		String safeRemoval = "DELETE FROM NOT_APPROVED WHERE USER_ID = ?";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeUpdate);
			
			push.setString(1, "Y");
			push.setLong(2, u.getAccountNumber());
			
			push.executeUpdate();
			
			push = conn.prepareStatement(safeRemoval);
			push.setLong(1, u.getAccountNumber());
			
			push.executeUpdate();
			
			System.out.println("\tSucessfuly approved customer and removed from not_approved");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void employeeApproved(User u)
	{
		String safeUpdate = "UPDATE EMPLOYEE SET IS_APPROVED =? WHERE USER_ID =?";
		String safeRemoval = "DELETE FROM NOT_APPROVED WHERE USER_ID = ?";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeUpdate);
			
			push.setString(1, "Y");
			push.setLong(2, u.getAccountNumber());
			
			push.executeUpdate();
			
			push = conn.prepareStatement(safeRemoval);
			push.setLong(1, u.getAccountNumber());
			
			push.executeUpdate();
			
			System.out.println("\tSucessfully approved employee and removed from not_approved");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void customerDenied(User u)
	{
		String safeRemoval = "DELETE FROM NOT_APPROVED WHERE USER_ID = ?";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeRemoval);
			push.setLong(1, u.getAccountNumber());
			
			push.executeUpdate();
			
			safeRemoval = "DELETE FROM CUSTOMER WHERE USER_ID = ?";
			push = conn.prepareStatement(safeRemoval);
			push.setLong(1, u.getAccountNumber());
			
			push.executeUpdate();
			
			System.out.println("\tDenied customer, removed from CUSTOMER and NOT_APPROVED");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void employeeDenied(User u)
	{
		String safeRemoval = "DELETE FROM NOT_APPROVED WHERE USER_ID = ?";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeRemoval);
			push.setLong(1, u.getAccountNumber());
			
			push.executeUpdate();
			
			safeRemoval = "DELETE FROM EMPLOYEE WHERE USER_ID = ?";
			push = conn.prepareStatement(safeRemoval);
			push.setLong(1, u.getAccountNumber());
			
			push.executeUpdate();
			
			System.out.println("\tDenied customer, removed from EMPLOYEE and NOT_APPROVED");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void deleteFromCustomerTable(User u)
	{
		String safeRemoval = "DELETE FROM CUSTOMER WHERE USER_ID = ?";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeRemoval);
			push.setLong(1, u.getAccountNumber());
			
			push.executeUpdate();
			
			safeRemoval = "DELETE FROM MASTER WHERE USER_ID = ?";
			push = conn.prepareStatement(safeRemoval);
			push.setLong(1, u.getAccountNumber());
			
			push.executeUpdate();
			
			System.out.println("\tRemoved from CUSTOMER and MASTER");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void deleteFromEmployeeTable(User u)
	{
		String safeRemoval = "DELETE FROM EMPLOYEE WHERE USER_ID = ?";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeRemoval);
			push.setLong(1, u.getAccountNumber());
			
			push.executeUpdate();
			
			safeRemoval = "DELETE FROM MASTER WHERE USER_ID = ?";
			push = conn.prepareStatement(safeRemoval);
			push.setLong(1, u.getAccountNumber());
			
			push.executeUpdate();
			
			System.out.println("\tRemoved from EMPLOYEE and MASTER");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void createTable()
	{
		String SafeQuery = "CREATE TABLE EMPLOYEE("
			      + "USER_ID NUMBER NOT NULL, "
			      + "FIRSTNAME VARCHAR2 (50), "
			      + "LASTNAME VARCHAR2 (50), "
			      + "SSN NUMBER,"
			      + "PHONE NUMBER, "
			      + "EMAIL VARCHAR2 (50), "
			      + "IS_APPROVED VARCHAR2 (1))";
		
		PreparedStatement create;
		
		try {
			create = conn.prepareStatement(SafeQuery);
			create.executeUpdate();
			
			System.out.println("\tTable Created Sucessfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConn()
	{
		try {
			conn.close();
			System.out.println("Connection to database has been closed!");
		} catch (SQLException e) {
			System.out.println("Error closing the connection");
			e.printStackTrace();
		}
	}
}
