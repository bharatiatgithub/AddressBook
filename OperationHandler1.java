import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;


class OperationHandler1 implements ActionListener
{
	
	JFrame newFrame;
	JTextField txtFirstName;
    JTextField txtLastName;
    JTextField txtMiddlename;
    JTextField txtEMail;
    JTextField txtAddress;
    JTextField txtPhoneNo;
    JTextField txtWebpage;
    JTextField txtBDay;

    JButton BttnSaveAdded,bttnDelete,bttnSearch,bttnCancel,bttnReset,bttnSearchUser,bttnResetUser;
    JButton bttnRefreshUser,bttnAscDscUser,bttnRefresh,bttnAscDsc;
	JTable searchTable,searchTableUser,contactTableUser,contactTable;
	DefaultTableModel searchTableModel,searchTableUserModel;
    JTextField txtSearch,txtSearchUser,tfFirstname,tfLastname;
    Socket socketToServer;
    JLabel lblMessage1,lblMessage2,lblallContactsUser,lblallContacts,lblsearchmsgUser,lblsearchmsg;
 

     String columnNames[] = { "First Name","Last Name",
                                "Middle Name","E Mail Address",
                                "Address","Phone No.",
                                "Webpage","B'day"
                            };
     Object data[][]= new Object[10][8];

	 Toolkit kit = Toolkit.getDefaultToolkit();
     Dimension screenSize = kit.getScreenSize();
     int screenHeight = screenSize.height;
     int screenWidth = screenSize.width;

     String imname,iemail,iaddress,iph,iwebpage,ibday;
	 

	public  OperationHandler1()
	{
		try
		{
		socketToServer=new Socket("osl81.njit.edu",6969);
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			
		}
	}

	public JComponent AddNew()
	{    //display a new Frame
		// newFrame = new JFrame("Add New");
        // newFrame.setBounds(200,150,220,250);
        // newFrame.setResizable(false);
		lblMessage1 = new JLabel("Please Fill in Details and Press Save!!!");
		
		
		 JLabel lblFirstName = new JLabel("*First Name: ");
         JLabel lblLastName = new JLabel("*Last Name: ");
         JLabel lblMiddlename = new JLabel("Middlename: ");
         JLabel lblEMail = new JLabel("EMail: ");
         JLabel lblAddress = new JLabel("Address: ");
         JLabel lblPhoneNo = new JLabel("Phone No: ");
         JLabel lblWebpage = new JLabel("Webpage: ");
         JLabel lblBDay = new JLabel("BDay: ");
         JLabel lblEmpty1 = new JLabel("");
         JLabel lblEmpty2 = new JLabel("");

         txtFirstName = new JTextField(10);
         txtLastName = new JTextField(10);
         txtMiddlename = new JTextField(10);
         txtEMail = new JTextField(10);
         txtAddress = new JTextField(10);
         txtPhoneNo = new JTextField(10);
         txtWebpage = new JTextField(10);
         txtBDay = new JTextField(10);
  	     
		 JButton BttnAdd = new JButton("Save");

		 //create 2 panels 
		 JPanel centerPane = new JPanel();
         JPanel bottomPane = new JPanel();
         
   		 centerPane.setLayout(new GridLayout(8,2));
   	

         centerPane.add(lblFirstName);
         centerPane.add(txtFirstName);
         centerPane.add(lblLastName);
         centerPane.add(txtLastName);
         centerPane.add(lblMiddlename);
         centerPane.add(txtMiddlename);
         centerPane.add(lblEMail);
         centerPane.add(txtEMail);
         centerPane.add(lblAddress);
         centerPane.add(txtAddress);
         centerPane.add(lblPhoneNo);
         centerPane.add(txtPhoneNo);
         centerPane.add(lblWebpage);
         centerPane.add(txtWebpage);
         centerPane.add(lblBDay);
         centerPane.add(txtBDay);
         bottomPane.add(BttnAdd);
         
				 //create panel to hold add contacts 
	     JPanel addcontactPane=new JPanel(false);
	     addcontactPane.setLayout(new BorderLayout());

		 addcontactPane.add(lblMessage1,"North");
	     addcontactPane.add(centerPane,"Center");
		 addcontactPane.add(bottomPane,"South");
		 
		 
	    
		 //newFrame.getContentPane().add(centerPane,BorderLayout.CENTER);
		 //newFrame.getContentPane().add(bottomPane,BorderLayout.SOUTH);
			// newFrame.show();
			 BttnAdd.addActionListener(this);
			// System.out.println("in op handler");
			 return addcontactPane;
		
	}
	
	
	public JComponent allContactsUser()
	{
 		 JPanel centerPane = new JPanel(); 
 		 JPanel msgPane=new JPanel();
		
 		 bttnRefreshUser = new JButton("Refresh");
         bttnAscDscUser = new JButton("Sort");
         centerPane.add(bttnRefreshUser);
         centerPane.add(bttnAscDscUser);
         
         lblallContactsUser=new JLabel("Press refresh for updated contacts.");
         lblallContactsUser.setForeground(Color.blue);
         msgPane.add(lblallContactsUser);
		 
         DefaultTableModel contactTableUserModel=new DefaultTableModel(data,columnNames);
         contactTableUser = new JTable(contactTableUserModel);
     //    contactTableUser
         contactTableUser.setEnabled(false);
         JScrollPane scrollPane = new JScrollPane(contactTableUser);
         contactTableUser.setPreferredScrollableViewportSize(new Dimension(500, 180));
         
		 l1=new Listerner1();
		 bttnRefreshUser.addActionListener(l1);
		 bttnAscDscUser.addActionListener(l1);
		 
		 JPanel addcontactPane=new JPanel(false);
	     addcontactPane.setLayout(new BorderLayout());

	    
	     addcontactPane.add(msgPane,"North");
	     addcontactPane.add(centerPane,"Center");
	     addcontactPane.add(scrollPane,"South");
		 //addcontactPane.add(bottomPane);
		 return addcontactPane;
		 
	}
	
	public JComponent allContacts()
	{
 		 JPanel centerPane = new JPanel(); 
 		 JPanel msgPane=new JPanel();
		
 		 bttnRefresh = new JButton("Refresh");
         bttnAscDsc = new JButton("Sort");
         centerPane.add(bttnRefresh);
         centerPane.add(bttnAscDsc);
         
         lblallContacts=new JLabel("Press refresh for updated contacts.");
         lblallContacts.setForeground(Color.blue);
         msgPane.add(lblallContacts);
		 
         DefaultTableModel contactTableModel=new DefaultTableModel(data,columnNames);
         contactTable = new JTable(contactTableModel);
     //    contactTableUser
         contactTable.setEnabled(false);
         JScrollPane scrollPane = new JScrollPane(contactTable);
         contactTable.setPreferredScrollableViewportSize(new Dimension(500, 180));
         
		
		 bttnRefresh.addActionListener(this);
		 bttnAscDsc.addActionListener(this);
		 
		 JPanel addcontactPane=new JPanel(false);
	     addcontactPane.setLayout(new BorderLayout());

	    
	     addcontactPane.add(msgPane,"North");
	     addcontactPane.add(centerPane,"Center");
	     addcontactPane.add(scrollPane,"South");
		 //addcontactPane.add(bottomPane);
		 return addcontactPane;
		 
	}
	
    public JComponent SearchContactsUser()
	{
    	JPanel msgPane=new JPanel();
		
		lblsearchmsgUser=new JLabel("Enter First Name to search contact");
		lblsearchmsgUser.setForeground(Color.blue);
		msgPane.add(lblsearchmsgUser);
    	
    	
    	
    	 JPanel centerPane = new JPanel(); 
		 JLabel label = new JLabel("First Name:");
         txtSearchUser = new JTextField(20);
         bttnSearchUser = new JButton("Search!");
         bttnResetUser = new JButton("Reset");
         centerPane.add(label);
         centerPane.add(txtSearchUser);
         centerPane.add(bttnSearchUser);
         centerPane.add(bttnResetUser);
    
	
		 
         DefaultTableModel searchTableUserModel=new DefaultTableModel(data,columnNames);
         searchTableUser = new JTable(searchTableUserModel);
         searchTableUser.setEnabled(false);
         JScrollPane scrollPane = new JScrollPane(searchTableUser);
         searchTableUser.setPreferredScrollableViewportSize(new Dimension(500, 180));
         
		 l1=new Listerner1();
         bttnSearchUser.addActionListener(l1);
		 bttnResetUser.addActionListener(l1);
		 
		 JPanel addSearchPane=new JPanel(false);
	     addSearchPane.setLayout(new BorderLayout());

	     addSearchPane.add(msgPane,"North");
	     addSearchPane.add(centerPane,"Center");
		 addSearchPane.add(scrollPane,"South");
		 //addcontactPane.add(bottomPane);
		 return addSearchPane;
		 
	}
    
    public JComponent SearchContacts()
	{
    	JPanel msgPane=new JPanel();
		
		lblsearchmsg=new JLabel("Enter First Name to serach contact");
		lblsearchmsg.setForeground(Color.blue);
		msgPane.add(lblsearchmsg);
    	
    	
    	
    	 JPanel centerPane = new JPanel(); 
		 JLabel label = new JLabel("First Name:");
         txtSearch = new JTextField(20);
         bttnSearch = new JButton("Search!");
         bttnReset = new JButton("Reset");
         centerPane.add(label);
         centerPane.add(txtSearch);
         centerPane.add(bttnSearch);
         centerPane.add(bttnReset);
    
	
		 
		 
         DefaultTableModel searchTableModel=new DefaultTableModel(data,columnNames);
         searchTable = new JTable(searchTableModel);
         searchTable.setEnabled(false);
		 
		 
         JScrollPane scrollPane = new JScrollPane(searchTable);
         searchTable.setPreferredScrollableViewportSize(new Dimension(500, 90));
         
		 bttnSearch.addActionListener(this);
		 bttnReset.addActionListener(this);
		 
		 JPanel addSearchPane=new JPanel(false);
	     addSearchPane.setLayout(new BorderLayout());

	     addSearchPane.add(msgPane,"North");
	     addSearchPane.add(centerPane,"Center");
		 addSearchPane.add(scrollPane,"South");
		 //addcontactPane.add(bottomPane);
		 return addSearchPane;
		 
	}

       

	public JComponent DeleteContact()
	{
            
		JPanel msgPane=new JPanel();
		
		lblMessage2=new JLabel("Enter First Name and Last Name to Delete Entry!!!");
		lblMessage2.setForeground(Color.blue);
		msgPane.add(lblMessage2);
		
		
        JPanel topPane = new JPanel();
        JLabel flabel = new JLabel("*First Name: ");
        JLabel llabel = new JLabel("*Last Name: ");
		tfFirstname=new JTextField(20);
		tfLastname=new JTextField(20);
        topPane.add(flabel);        
		topPane.add(tfFirstname);	
		topPane.add(llabel);
		topPane.add(tfLastname);

        JPanel bottomPane = new JPanel();
        bttnDelete = new JButton("Delete Selected");
        bottomPane.add(bttnDelete);
        bttnDelete.addActionListener(this);

        bttnCancel = new JButton("Cancel");
        bottomPane.add(bttnCancel);
        bttnCancel.addActionListener(this);
        
        
        JPanel addDeletePane=new JPanel();
	     addDeletePane.setLayout(new GridLayout(3,0));

		 addDeletePane.add(msgPane);
	     addDeletePane.add(topPane);
		 addDeletePane.add(bottomPane);

		 return addDeletePane;

	}




	  public void actionPerformed(ActionEvent ae)
      {
		  if(ae.getSource()==bttnCancel)
		  {
			  tfFirstname.setText("");
			  tfLastname.setText("");
			  lblMessage2.setForeground(Color.blue);
			  lblMessage2.setText("Enter First Name and Last Name to Delete Entry.");
		  }
		  if(ae.getSource()== bttnDelete)
		  {
			 String fname, lname;

			  fname=tfFirstname.getText();
			  lname=tfLastname.getText();
			  
			  
			  
			  		if(tfFirstname.getText().equals("") || tfLastname.getText().equals(""))
		      		{

						lblMessage2.setForeground(Color.red);
				  		lblMessage2.setText("Please enter both First Name and Last name");
	 		        }
			        else
	           	    {
	           	      
			        	String[] tokenarray={"Delete",tfFirstname.getText(),tfLastname.getText()};
			        
			        	
			        		String deletestring=new TokenMe(tokenarray).gettokenizedString();
			        		DataObject myObject=new DataObject();
			        		myObject.setMessage(deletestring);
			        		 try{
			        			 
			        			 if(socketToServer.isClosed())
			        			 {
			        				 socketToServer=new Socket("osl81.njit.edu",6969);
			        				 
			        			 }
			        			 else
			        			 {
			        				 System.out.println("Socket was connected:" + socketToServer.isConnected());
			        			 }       
			        			 ObjectOutputStream out=new ObjectOutputStream(socketToServer.getOutputStream());
			        			 out.writeObject(myObject);
			        			 ObjectInputStream in=new ObjectInputStream(socketToServer.getInputStream());
			        			 myObject= (DataObject)in.readObject();
			        			 System.out.println("Message received:"+myObject.getMessage());
			        			 
			        			 lblMessage2.setForeground(Color.blue);
			        			 lblMessage2.setText("Enrty Deleted!Enter First Name and Last Name to delete another entry.");
			        			 tfFirstname.setText("");
			           	            tfLastname.setText("");
			        			 in.close();
			        			 out.close();
			        			// socketToServer.close();
			        			 
			        			 
			        		 	}
			        			 catch(Exception e){
			        				 System.out.println(e);
			        			 }
			        	
			                   	            
	           	    }
		  }//end if bbtnDelete
////////////////////////////////////////////////////////////////////////
		  if(ae.getSource()==bttnReset)
		  {
			  txtSearch.setText("");
			  //code to empty table
			  resetTable(searchTable);
			  lblsearchmsg.setText("Enter First Name to search contact");
		  }  
			  
		  
		  if(ae.getSource()==bttnSearch)
		  {
  				
			  		String[] tokenarray={"Search",txtSearch.getText()};
	      
	        		String insertstring=new TokenMe(tokenarray).gettokenizedString();
	        		DataObject myObject=new DataObject();
	        		myObject.setMessage(insertstring);
	        		 try{
	        			 

	        			 if(socketToServer.isClosed())
	        			 {
	        				 socketToServer=new Socket("osl81.njit.edu",6969);
	        				 
	        			 }
	        			 else
	        			 {
	        				 System.out.println("Socket was connected:" + socketToServer.isConnected());
	        			 }     
	        			 
	        			 ObjectOutputStream out=new ObjectOutputStream(socketToServer.getOutputStream());
	        			 out.writeObject(myObject);
	        			 
	        			 ObjectInputStream in=new ObjectInputStream(socketToServer.getInputStream());
	        			 myObject= (DataObject)in.readObject();
	        			 
	        			 System.out.println("Message received Result set is:"+myObject.getMessage());
	        			
	        			        			 
	        			 //code to empty table
	       			  	 resetTable(searchTable);
	        			  
	       			  	 //get the row and get the elements
	        		     int row=fillTable(searchTable,myObject.getMessage()); 
	        		     lblsearchmsg.setText(row +" contacts found.");  			  	
	       			  	 if(row==0)
	        			 {
	       			  		 lblallContacts.setText("No contacts found.");
	       			  		 System.out.println("No search result found");
	        			 }
	        			 
	        			 
	        			 
	        			 in.close();
	        			 out.close();
	        			// socketToServer.close();
	        			 
	        			 
	       			  	}
	        			 catch(Exception e){
	        				 System.out.println(e);
	        			 }
		  }
///////////////////////////////////////////////////////////////////////////////////////////
		   if(ae.getActionCommand() == "Save")
		  {
			    	if(txtFirstName.getText().equals("") || txtLastName.getText().equals(""))
	            {
			    		lblMessage1.setText("Please enter First Name and Last Name");
 		        }
		        else
           	    {
           	          
		        	//form a string to pass to server
		        	//int loopvar=0;
		        	if(txtMiddlename.getText().equals(""))
		        		imname="-";
		        	else
		        		imname=txtMiddlename.getText();
		        	
		        	if(txtEMail.getText().equals(""))
		        		iemail="-";
		        	else
		        		iemail=txtEMail.getText();
		        	
		        	if(txtAddress.getText().equals(""))
		        		iaddress="-";
		        	else
		        		iaddress=txtAddress.getText();
		        	
		        	if(txtPhoneNo.getText().equals(""))
		        		iph="-";
		        	else
		        		iph=txtPhoneNo.getText();
		        	
		        	if(txtWebpage.getText().equals(""))
		        		iwebpage="-";
		        	else
		        		iwebpage=txtWebpage.getText();
		        	
		        	if(txtBDay.getText().equals(""))
		        		ibday="-";
		        	else
		        		ibday=txtBDay.getText();
		        	
		        	
		        	String[] tokenarray={"Save",txtFirstName.getText(),txtLastName.getText(),imname,iemail,iaddress,iph,iwebpage,ibday};
		  				   								
		        	
		        //	for(loopvar=0;loopvar<tokenarray.length;loopvar++)
		        		
		        	//System.out.println(tokenarray[loopvar]);
		        	
		        		String insertstring=new TokenMe(tokenarray).gettokenizedString();
		        		DataObject myObject=new DataObject();
		        		myObject.setMessage(insertstring);
		        		 try{
		        			
		        			
		        			 if(socketToServer.isClosed())
		        			 {
		        				 socketToServer=new Socket("osl81.njit.edu",6969);
		        				 
		        			 }
		        			 else
		        			 {
		        				 System.out.println("Socket was connected:" + socketToServer.isConnected());
		        			 }      			 
				        			 ObjectOutputStream out=new ObjectOutputStream(socketToServer.getOutputStream());
				        			 out.writeObject(myObject);
				        			 ObjectInputStream in=new ObjectInputStream(socketToServer.getInputStream());
				        			 myObject= (DataObject)in.readObject();
				        			 System.out.println("Message received:"+myObject.getMessage());
				        			 
				        			 lblMessage1.setForeground(Color.blue);
				        			 lblMessage1.setText("Contact Added!To add another contact enter details and press Save.");
				        			 in.close();
				        			 out.close();
				        			// socketToServer.close();
		        			 
		        			 
		        		 	}
		        			 catch(Exception e)
		        			 {
		        				 System.out.println(e);
		        			 }
		        	
		        	
		              	
		        	
           	            txtFirstName.setText("");
           	            txtLastName.setText("");
           	            txtMiddlename.setText("");
           	            txtEMail.setText("");
           	            txtAddress.setText("");
           	            txtPhoneNo.setText("");
           	            txtWebpage.setText("");
           	            txtBDay.setText("");
    	            						
           	    }
		  }
		   if(ae.getSource()==bttnRefresh)
		   {
			   String[] tokenarray={"Search",""};
			      
       		String insertstring=new TokenMe(tokenarray).gettokenizedString();
       		DataObject myObject=new DataObject();
       		myObject.setMessage(insertstring);
       		 try{
       			 

       			 if(socketToServer.isClosed())
       			 {
       				 socketToServer=new Socket("osl81.njit.edu",6969);
       				 
       			 }
       			 else
       			 {
       				 System.out.println("Socket was connected:" + socketToServer.isConnected());
       			 }     
       			 
       			 ObjectOutputStream out=new ObjectOutputStream(socketToServer.getOutputStream());
       			 out.writeObject(myObject);
       			 
       			 ObjectInputStream in=new ObjectInputStream(socketToServer.getInputStream());
       			 myObject= (DataObject)in.readObject();
       			 
       			 System.out.println("Message received Result set is:"+myObject.getMessage());
       			
       			//code to empty table
      			  	resetTable(contactTable);
       			  
       		    //get the row and get the elements
       		     int row=fillTable(contactTable,myObject.getMessage());
       		    
       		     lblallContacts.setText(row +" contacts found.");
       			        			 
       			 in.close();
       			 out.close();
       			// socketToServer.close();
       			 
       			 
      			  	}
       			 catch(Exception e){
       				 System.out.println(e);
       			 }
		   }
		   
		   if(ae.getSource()==bttnAscDsc)
		   {
			   String[] tokenarray1={"sort",""};
			      
				  if(flag2)
				  {
					 tokenarray1 [1]="asc";
					  	flag2=false;
				  	}
				  else
				  {
					  tokenarray1 [1]="desc";
				  		flag2=true;
				  	}
				  
				  
				  	String sortstring=new TokenMe(tokenarray1).gettokenizedString();
	        		DataObject myObject=new DataObject();
	        		myObject.setMessage(sortstring);
	        				  
					  
	        		try{
	        			 

		        			 if(socketToServer.isClosed())
		        			 {
		        				 socketToServer=new Socket("osl81.njit.edu",6969);
		        				 
		        			 }
		        			 else
		        			 {
		        				 System.out.println("Socket was connected:" + socketToServer.isConnected());
		        			 }     
		        			 
		        			 ObjectOutputStream out=new ObjectOutputStream(socketToServer.getOutputStream());
		        			 out.writeObject(myObject);
		        			 
		        			 ObjectInputStream in=new ObjectInputStream(socketToServer.getInputStream());
		        			 myObject= (DataObject)in.readObject();
		        			 
		        			 System.out.println("Message received Result set is:"+myObject.getMessage());
		        			
		        			//code to empty table
		       			  	resetTable(contactTable);
		        			  
		        		    //get the row and get the elements
		        		     int row=fillTable(contactTable,myObject.getMessage());
		        		    
		        		     lblallContacts.setText(row +" contacts found.");
		        			        			 
		        			 in.close();
		        			 out.close();
		        			// socketToServer.close();
		        			 
	        			 
	       			  	}
	        		catch(Exception e){System.out.println(e);}
	        				 
			   
		   }

	  }
	  
	  public void resetTable(JTable table)
	  {
		  for( int nRow = 0 ; nRow<table.getRowCount() ; nRow++ )
		  {
			  for( int nColumn = 0 ; nColumn < table.getColumnCount(); nColumn++ )
			  {
				  table.setValueAt("" , nRow , nColumn ); 
			  }
		  }  
	  }
	  
	  public int fillTable(JTable table,String tablerows)
	  {
		  
		  //	DefaultTableModel model=new DefaultTableModel();
		  DefaultTableModel tableModel = (DefaultTableModel)table.getModel();   
	       
		  	StringTokenizer st1,st2;
		     String rs;
		     Object[] data1={"","","","","","","",""};
		     st1=new StringTokenizer(tablerows,"|");
			 
		     //get the row and get the elements
		     int row=0;
		     while(st1.hasMoreTokens())
			 {
				 
		    	
		    	 rs=st1.nextToken();
				 st2=new StringTokenizer(rs,"~");
				
				 
				if(tableModel.getRowCount()<(row+1))
				 {
					 System.out.println("adding in row>4");
					  tableModel.addRow(data1);
					 System.out.println("Got new Row");
				 }
				 
				 		if(st2.hasMoreTokens()) table.setValueAt(st2.nextToken(),row,0);
				 		else table.setValueAt("",row,0);
				 		if(st2.hasMoreTokens()) table.setValueAt(st2.nextToken(),row,1);
				 		else table.setValueAt("",row,1);
				 		if(st2.hasMoreTokens()) table.setValueAt(st2.nextToken(),row,2);
				 		else table.setValueAt("",row,2);
				 		if(st2.hasMoreTokens()) table.setValueAt(st2.nextToken(),row,3);
				 		else table.setValueAt("",row,3);
				 		if(st2.hasMoreTokens()) table.setValueAt(st2.nextToken(),row,4);
				 		else table.setValueAt("",row,4);
				 		if(st2.hasMoreTokens()) table.setValueAt(st2.nextToken(),row,5);
				 		else table.setValueAt("",row,5);
				 		if(st2.hasMoreTokens()) table.setValueAt(st2.nextToken(),row,6);
				 		else table.setValueAt("",row,6);
				 		if(st2.hasMoreTokens()) table.setValueAt(st2.nextToken(),row,7);
				 		else table.setValueAt("",row,7);
				 		        						
						
				 row++;
		    	 
			 }
		     return row;
			 
		  
	  }
	  
	  private class Listerner1 implements ActionListener
	  {
		  public void actionPerformed(ActionEvent ae){
			  
			  
			  if(ae.getSource()==bttnAscDscUser)
			  {
				  
				 
				  String[] tokenarray1={"sort",""};
			      
				  if(flag1)
				  {
					 tokenarray1 [1]="asc";
					  	flag1=false;
				  	}
				  else
				  {
					  tokenarray1 [1]="desc";
				  		flag1=true;
				  	}
				  
				  
				  	String sortstring=new TokenMe(tokenarray1).gettokenizedString();
	        		DataObject myObject=new DataObject();
	        		myObject.setMessage(sortstring);
	        				  
					  
	        		try{
	        			 

		        			 if(socketToServer.isClosed())
		        			 {
		        				 socketToServer=new Socket("osl81.njit.edu",6969);
		        				 
		        			 }
		        			 else
		        			 {
		        				 System.out.println("Socket was connected:" + socketToServer.isConnected());
		        			 }     
		        			 
		        			 ObjectOutputStream out=new ObjectOutputStream(socketToServer.getOutputStream());
		        			 out.writeObject(myObject);
		        			 
		        			 ObjectInputStream in=new ObjectInputStream(socketToServer.getInputStream());
		        			 myObject= (DataObject)in.readObject();
		        			 
		        			 System.out.println("Message received Result set is:"+myObject.getMessage());
		        			
		        			//code to empty table
		       			  	resetTable(contactTableUser);
		        			  
		        		    //get the row and get the elements
		        		     int row=fillTable(contactTableUser,myObject.getMessage());
		        		    
		        		     lblallContactsUser.setText( row + " contacts Found.");
		        			        			 
		        			 in.close();
		        			 out.close();
		        			// socketToServer.close();
		        			 
	        			 
	       			  	}
	        		catch(Exception e){System.out.println(e);}
	        				 
	        			 
			  }
			  
			  
			  
			  if(ae.getSource()==bttnRefreshUser)
			  {
				  String[] tokenarray={"Search",""};
			      
	        		String insertstring=new TokenMe(tokenarray).gettokenizedString();
	        		DataObject myObject=new DataObject();
	        		myObject.setMessage(insertstring);
	        		 try{
	        			 

	        			 if(socketToServer.isClosed())
	        			 {
	        				 socketToServer=new Socket("osl81.njit.edu",6969);
	        				 
	        			 }
	        			 else
	        			 {
	        				 System.out.println("Socket was connected:" + socketToServer.isConnected());
	        			 }     
	        			 
	        			 ObjectOutputStream out=new ObjectOutputStream(socketToServer.getOutputStream());
	        			 out.writeObject(myObject);
	        			 
	        			 ObjectInputStream in=new ObjectInputStream(socketToServer.getInputStream());
	        			 myObject= (DataObject)in.readObject();
	        			 
	        			 System.out.println("Message received Result set is:"+myObject.getMessage());
	        			
	        			//code to empty table
	       			  	resetTable(contactTableUser);
	        			  
	        		    //get the row and get the elements
	        		     int row=fillTable(contactTableUser,myObject.getMessage());
	        		    
	        		     lblallContactsUser.setText(row+ " contacts found.");
	        			        			 
	        			 in.close();
	        			 out.close();
	        			// socketToServer.close();
	        			 
	        			 
	       			  	}
	        			 catch(Exception e){
	        				 System.out.println(e);
	        			 }
			  }
			  
			  
			  
			  if(ae.getSource()==bttnResetUser)
			  {
				  txtSearchUser.setText("");
				  //code to empty table
				  resetTable(searchTableUser);
				  lblsearchmsgUser.setText("Enter First Name to search contact");
			  }  
				  
			  
			  if(ae.getSource()==bttnSearchUser)
			  {
	  				
				  		String[] tokenarray={"Search",txtSearchUser.getText()};
		      
		        		String insertstring=new TokenMe(tokenarray).gettokenizedString();
		        		DataObject myObject=new DataObject();
		        		myObject.setMessage(insertstring);
		        		 try{
		        			 

		        			 if(socketToServer.isClosed())
		        			 {
		        				 socketToServer=new Socket("osl81.njit.edu",6969);
		        				 
		        			 }
		        			 else
		        			 {
		        				 System.out.println("Socket was connected:" + socketToServer.isConnected());
		        			 }     
		        			 
		        			 ObjectOutputStream out=new ObjectOutputStream(socketToServer.getOutputStream());
		        			 out.writeObject(myObject);
		        			 
		        			 ObjectInputStream in=new ObjectInputStream(socketToServer.getInputStream());
		        			 myObject= (DataObject)in.readObject();
		        			 
		        			 System.out.println("Message received Result set is:"+myObject.getMessage());
		        			
		        			//code to empty table
		       			  	resetTable(searchTableUser);
		        			  
		        		    //get the row and get the elements
		        		     int row=fillTable(searchTableUser,myObject.getMessage());
		        		     lblsearchmsgUser.setText(row+ " contacts found.");
		        		     if(row==0)
		        			 {
		        		    	 lblsearchmsgUser.setText("No contacts found.");
		        		    	 System.out.println("No search result found");
		        			 }
		        			        			 
		        			 in.close();
		        			 out.close();
		        			// socketToServer.close();
		        			 
		        			 
		       			  	}
		        			 catch(Exception e){
		        				 System.out.println(e);
		        			 }
			  }
		  }
	  }
	  
	  Listerner1 l1;
	  Boolean flag1=true,flag2=true;


}
