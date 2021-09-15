package bank;

import static org.junit.Assert.*;

import org.junit.Test;

public class JUnitTestCase {
	
	@Test
	public void adminCreationTest()
	{
		User u = new User("Razeno", "Khan");
		
		assertTrue(u.isAdmin() == true);
	}
	
	@Test
	public void testUserObject()
	{	
		User u = new User();
		
		assertNotNull(u.getClass());
	}
	
	@Test
	public void testConn()
	{
		JDBCBank check = new JDBCBank();
		
		assertNotNull(check.conn);
	}
}
