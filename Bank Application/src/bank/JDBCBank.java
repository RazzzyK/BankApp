package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCBank {
	
	static final String host = "jdbc:oracle:thin:@java-react-27.csedppwlopoz.us-east-1.rds.amazonaws.com:1521:ORCL";
	static final String user = "Admin";                //Save in a flat file
	static final String password = "12345678";
	
	JDBCBank()
	{

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(host, user, password);
			
			
			String safeInsert = "INSERT INTO MASTER(user_id, user, pass) VALUES (?, ?, ?)";
			
			String safeQuery = "Select * from MASTER where user_id=?";
			
			PreparedStatement ps = conn.prepareStatement(safeInsert);
			
			ps.setInt(1, 111);
			ps.setString(2, "raz");
			ps.setString(3, "pass");
			
			int row = ps.executeUpdate();
			
			
			
			
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, "111");
			
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				System.out.println("User_ID: " + rs.getInt(1));
				
				System.out.println("User Name: " + rs.getString(2));
				
				System.out.println("Password: " + rs.getString(3));
			}
			
			System.out.println("HIT!");
			
		}catch (ClassNotFoundException e)
		{
			System.out.println("Unable to load driver class");
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void insertDB(ArrayList<User> list)
	{
		
	}
}
