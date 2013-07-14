import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadedDataObjectServer
{  public static void main(String[] args ) 
   {  
	
      try 
      {  ServerSocket s = new ServerSocket(6969);
         
         for (;;)
         {  Socket incoming = s.accept();
            new ThreadedDataObjectHandler(incoming).start();
             
	   	 }   
      }
      catch (Exception e) 
      {  System.out.println(e);
      } 
   } 
}

class ThreadedDataObjectHandler extends Thread
{  public ThreadedDataObjectHandler(Socket i) 
   { 
   		incoming = i;
   		System.out.println("Socket Created!!!Thread Created!!!");
   }
   
   public void run()
   {  
	    String FName="";
	    String LName="";
	    String Mname="";
	    String EMail="";
	    String Address="";
	    String PhoneNo="";
	    String Webpage="";
	    String Bday="";
	    String newRs;
	    String sortOrder="";
	    
	   
	    
	   try 
      { 	
	
	   
	   		ObjectInputStream in =new ObjectInputStream(incoming.getInputStream());
	   		ObjectOutputStream out =new ObjectOutputStream(incoming.getOutputStream());
	   		myObject = (DataObject)in.readObject();
	   		System.out.println("Client: " + myObject.getMessage());
		
	   		//Get Data from client and decode
	   		StringTokenizer st = new StringTokenizer(myObject.getMessage(), "~");
	   		String operation;
	   		int op=0;
			if(st.hasMoreTokens()){
				operation=st.nextToken();
				if (operation.toLowerCase().equals("checklogin"))
					op=0;
				if (operation.toLowerCase().equals("save"))
					op=1;
				if (operation.toLowerCase().equals("delete"))
					op=2;
				if (operation.toLowerCase().equals("search"))
					op=3;
				if(operation.toLowerCase().equals("sort"))
					op=4;
			
				
				System.out.println(operation);
			 }
			
			switch(op)
			{
			case 0:	
					boolean result0;
					JDBCFunctions ckLogin=new  JDBCFunctions();
					result0=ckLogin.checkLogin(st.nextToken(),st.nextToken(),st.nextToken());
					if(result0)
					{
						myObject.setMessage("successful");
					}
					else
						myObject.setMessage("unsuccessful");
					break;
			case 1:
					Contact newContact = new Contact();
				
					if(st.hasMoreTokens()) 
					{
						FName=st.nextToken();
					}
					if(st.hasMoreTokens())
					{
						LName=st.nextToken();
					}
					
					
						Mname=st.nextToken();
						System.out.println("mname:"+ Mname);
					
					if(st.hasMoreTokens())
					{  
						EMail=st.nextToken();
						System.out.println("Email:"+ EMail);
					}
					if(st.hasMoreTokens())  
					{
						Address=st.nextToken();
						System.out.println("Address:"+ Address);
					}
					if(st.hasMoreTokens())  
					{
						PhoneNo=st.nextToken();
						System.out.println("ph:"+PhoneNo);
					}
					if(st.hasMoreTokens())  
					{
						Webpage=st.nextToken();
						System.out.println("webpage:"+Webpage);
					}
					if(st.hasMoreTokens())  
					{
						Bday=st.nextToken();
						System.out.println("Bday:"+Bday);
					}
					
					newContact.setDetails(FName,LName,Mname,EMail,Address,PhoneNo,Webpage,Bday);
					JDBCFunctions finsert=new  JDBCFunctions();
					 finsert.insertDB(newContact);
					 myObject.setMessage("Data Inserted!!!");
					 out.writeObject(myObject);
						break;
			case 2: 
					if(st.hasMoreTokens())  FName=st.nextToken();
					if(st.hasMoreTokens())  LName=st.nextToken();
					JDBCFunctions fdelete=new  JDBCFunctions();
					fdelete.deleteDB(FName, LName);
					 myObject.setMessage("Data Deleted!!!");
					 out.writeObject(myObject);
					break;
					
			case 3: 
					System.out.println("in case 3");
					if(st.hasMoreTokens()) FName=st.nextToken();
					JDBCFunctions fsearch=new JDBCFunctions();
					fsearch.searchDB(FName);
					if(!(fsearch.rs.equals(null)))
					{
					StringBuilder sendRs=new StringBuilder();
					System.out.println("Calculating sendrs");
					int i=0;
					fsearch.rs.beforeFirst();
					while(fsearch.rs.next())
					  {
						i=1;
						while(i<8)
						{
							
							if(fsearch.rs.getString(i).equals(null))
							sendRs.append("");
							else
							sendRs.append(fsearch.rs.getString(i));
							
							sendRs.append("~");
							i++;
						}
						sendRs.append(fsearch.rs.getString(8));
						sendRs.append("|");
					  }
									
						newRs=sendRs.toString();
		             }
					else
					{
						newRs="";           	
		            }
				
					System.out.println("sending object");
					myObject.setMessage(newRs);
					fsearch.conn.close();  
					break;
			case 4:
						System.out.println("in case 4");
						if(st.hasMoreTokens()) sortOrder=st.nextToken();
						JDBCFunctions fsort=new JDBCFunctions();
						fsort.sortDB(sortOrder);
						if(!(fsort.rs.equals(null)))
						{
						StringBuilder sendRs=new StringBuilder();
						System.out.println("Calculating sendrs");
						int i=0;
						fsort.rs.beforeFirst();
						while(fsort.rs.next())
						  {
							i=1;
							while(i<8)
							{
								
								if(fsort.rs.getString(i).equals(null))
								sendRs.append("");
								else
								sendRs.append(fsort.rs.getString(i));
								
								sendRs.append("~");
								i++;
							}
							sendRs.append(fsort.rs.getString(8));
							sendRs.append("|");
						  }
								
							newRs=sendRs.toString();
			            	
			            }
						else
						{
							newRs="";           	
			            }
					
						System.out.println("sending object");
						myObject.setMessage(newRs);
						
						fsort.conn.close();  
						break;
					
			
			default:
				
				myObject.setMessage("Sorry Operation can not be completed");
			}

			out.writeObject(myObject);
			in.close();
			out.close();
			incoming.close(); 
 		}
      catch (Exception e) 
      {  System.out.println(e);
         e.printStackTrace(); 
      } 
   }

   DataObject myObject = null;
   private Socket incoming;
   
}


