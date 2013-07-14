import java.sql.*;

class JDBCFunctions 
{
	Connection conn;
	PreparedStatement pstat;
	String query;
	ResultSet rs,rs1;


	public JDBCFunctions()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String connectionURL = "jdbc:mysql://sql2.njit.edu:3306/bw59";
		    conn = DriverManager.getConnection(connectionURL,"bw59", "68BHGZX65");
		
		}
		catch(Exception ex)
		{
			System.out.println("Driver not found");
			ex.printStackTrace();
		}
	}
	
	
	public boolean checkLogin(String uname,String pass,String type){
		System.out.println("Checking Login from the DATABASE !!!");
		try
		{
			query="select * from Login where  Username = ? and Password= ? and Admin= ?";
			pstat=conn.prepareStatement(query);
			pstat.setString(1,uname);
			pstat.setString(2,pass);
			pstat.setString(3,type);
			rs1=pstat.executeQuery();
			
			 if(rs1.next())
			 {
				 System.out.println("record Found");
				 conn.close(); 
				 return true;
				
			 }
			 else
				 conn.close(); 
				 return false;
			 
		}
		catch(Exception ex){			
		ex.printStackTrace();
		return false;
		}
	}
	
	
	
	public void insertDB(Contact c)
	{
		System.out.println("Inserting data into the DATABASE !!!");
		try
		{
			query="insert into AddressBook values(?,?,?,?,?,?,?,?)";
			pstat=conn.prepareStatement(query);
			pstat.setString(1,c.getFName());
			pstat.setString(2,c.getLName());
			pstat.setString(3,c.getMname());
			pstat.setString(4,c. getEMail());
			pstat.setString(5,c.getAddress());
			pstat.setString(6,c.getPhoneNo());
			pstat.setString(7,c.getWebpage());
			pstat.setString(8,c.getBday());

			pstat.executeUpdate();
			conn.close();
		}
		catch(Exception ex){ex.printStackTrace();}
	}

	public void searchDB(String nm)
	{
		System.out.println("Searching data from the DATABASE !!!"+nm);
		try
		{
			query="select * from AddressBook where FirstName like '" + nm + "%'";
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery(query);	
		
		}
		catch(Exception ex){ex.printStackTrace();}
	}

	public void deleteDB(String fname,String lname)
	{
		System.out.println("Deleting data from the DATABASE !!!");
		System.out.println(fname);
		System.out.println(lname);
		try
		{
			query="delete from AddressBook where FirstName = ? and LastName = ?";
			pstat=conn.prepareStatement(query);
			pstat.setString(1,fname);
			pstat.setString(2,lname);
			pstat.executeUpdate();	
			conn.close();
		}
		catch(Exception ex){ex.printStackTrace();}		
	}

	public void sortDB(String order)
	{
		System.out.println("sorting data from the DATABASE !!!");
		try
		{
			query="select * from AddressBook order by FirstName " + order;
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery(query);	
		}
		catch(Exception ex){ex.printStackTrace();}	
		
	}
}
