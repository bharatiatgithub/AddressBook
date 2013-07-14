
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;


class MyAddressBook1 extends JApplet implements ActionListener
{
	
	Socket socketToServer;
	 
	// Get the screen width & height
	Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;

	OperationHandler1 oh;

	JFrame frame;
	final static String ADMIN = "Admin";
    final static String USER = "User";
    final static String INITIALPAGE="InititialPage";
    final static String LOGIN = "Login";
    final static String LOGOUT = "Logout";
    static int userType=1;
    JPanel addressbookPane,cards,card1,card2,card3,loginPane,logoutPane,loginLogoutpane;
    JPanel msgPane;
    JButton bttnLogin,bttnLogOut;
    JComboBox cbUserType;
    JTextField txtUserName;
    JPasswordField txtPassword;
    JLabel lblMessage1=new JLabel("");
    JLabel lblWelComeMsg1;
 //   JLabel lblWelComeMsg2=new JLabel("MyAddressBook");
    
    

	public MyAddressBook1()
	{
		try
		{
			socketToServer=new Socket("osl81.njit.edu",6969); 
		}
		catch(Exception e)
		{
			
			lblMessage1.setForeground(Color.RED);
			lblMessage1.setText("Sorry Server is down");
			System.out.println("Problem in Creating Socket.\n"+e);
			
		}
		oh = new OperationHandler1();
		 
	
		frame = new JFrame("Address Book");
   	    frame.setSize(700,500);
		frame.setLocation(screenWidth/4-50, screenHeight/4); 	  

				addressbookPane=new JPanel(new GridBagLayout()); //Main JPanel
				
				msgPane=new JPanel();
				msgPane.add(lblMessage1);
				
				loginLogoutpane=new JPanel(new CardLayout());
				cards=new JPanel(new CardLayout());
				
					card1=new JPanel();
					card2=new JPanel();
					card3=new JPanel();
					
					card1.setBackground(new Color(245,245,220));
					card2.setBackground(new Color(245,245,220));
					card3.setBackground(new Color(245,245,220));
				        
			
		
         
			//create LoginPane and complonents
			loginPane=new JPanel(new FlowLayout());
			JLabel lblUserName=new JLabel("Username:");
			JLabel lblPassword=new JLabel("Password:");
			JLabel lblUserType=new JLabel("User Type:");
			
			 txtUserName=new JTextField(10);
			 txtPassword=new JPasswordField(10);
		
		
			txtPassword.setEchoChar('*');
			String comboBoxItems[] = { ADMIN, USER };
	        cbUserType = new JComboBox(comboBoxItems);
	        cbUserType.setEditable(false);
	                 
			
	        bttnLogin=new JButton("Login");
			bttnLogin.addActionListener(this);
	        
	        loginPane.add(lblUserName);
	        loginPane.add(txtUserName);
	        loginPane.add(lblPassword);
	        loginPane.add(txtPassword);
	        loginPane.add(lblUserType);
	        loginPane.add(cbUserType);
			loginPane.add(bttnLogin);
			loginPane.setBackground(Color.lightGray);
	
			
			//create logoutPane and components
			logoutPane= new JPanel();
			logoutPane.setBackground(Color.LIGHT_GRAY);
			bttnLogOut=new JButton("Logout");
			bttnLogOut.addActionListener(this);
			logoutPane.add(bttnLogOut);
			
		
			
			JTabbedPane tabbedPane1 = new JTabbedPane();
            JTabbedPane tabbedPane2 = new JTabbedPane();
            
		
			
////////////////////////////////////////create tabbedPane for Admin-add to card1///////////////////////////////////////////
         
	        
	        JComponent panel1 = oh.AddNew();
	        tabbedPane1.addTab("Add Contact", panel1);
	        tabbedPane1.setMnemonicAt(0, KeyEvent.VK_1);
	        
	        JComponent panel2 = oh.DeleteContact();
	        tabbedPane1.addTab("Delete Contact", panel2);
	        tabbedPane1.setMnemonicAt(1, KeyEvent.VK_2);
	        
	        JComponent panel3 = oh.SearchContacts();
	        tabbedPane1.addTab("Search Contacts",panel3);
	        tabbedPane1.setMnemonicAt(2, KeyEvent.VK_3);
	       
	        
	        JComponent panel4 = oh.allContacts();
	        tabbedPane1.addTab("All Contacts",panel4);
	        tabbedPane1.setMnemonicAt(3, KeyEvent.VK_4);
	        
	        card1.add(tabbedPane1);
	        
//////////////////////////////////////////Create tabbedPane for normal user-add to card2/////////////////////////////////////
	     
	        
	        JComponent panel21 = oh.allContactsUser();
	        tabbedPane2.addTab("All Contacts",panel21);
	        tabbedPane2.setMnemonicAt(0, KeyEvent.VK_1);
	        
	        JComponent panel22 = oh.SearchContactsUser();
	        tabbedPane2.addTab("Search Contacts",panel22);
	        tabbedPane2.setMnemonicAt(1, KeyEvent.VK_2);
	        
	        card2.add(tabbedPane2);
	        
////////////////////////////////////////////PRAPARE INITIAL PAGE-Card 3/////////////////////////////////////////////////	       
	
	        Font bigFont = new Font("Helvetica",Font.ITALIC, 20);
	        lblWelComeMsg1=new JLabel("Welcome!!!Please login to access the AddressBook");
	        lblWelComeMsg1.setFont(bigFont);
	        
	        card3.add(lblWelComeMsg1);
	    

	     
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	      
	        cards.add(card1,ADMIN);
	        cards.add(card2,USER);
	        cards.add(card3,INITIALPAGE);
	        CardLayout cl = (CardLayout)(cards.getLayout());
	        cl.show(cards, INITIALPAGE); 
	        
	   
	      
	        loginLogoutpane.add(loginPane,LOGIN);
	        loginLogoutpane.add(logoutPane,LOGOUT);
	        
	     
	        GridBagConstraints constraint0 = new GridBagConstraints();
	        constraint0.gridx=0;
	        constraint0.gridy=0;
	        constraint0.fill=GridBagConstraints.HORIZONTAL;
	        addressbookPane.add(msgPane,constraint0);
	        GridBagConstraints constraint1 = new GridBagConstraints();
	        constraint1.gridx=0;
	        constraint1.gridy=1;
	        constraint1.fill=GridBagConstraints.HORIZONTAL;
	        addressbookPane.add(loginLogoutpane,constraint1);
	        GridBagConstraints constraint2 = new GridBagConstraints();
	        constraint2.gridx=0;
	        constraint2.gridy=2;
	        addressbookPane.add(cards,constraint2);
	        
	       
	        //frame.add(addressbookPane);
	        
	        //The following line enables to use scrolling tabs.
	        tabbedPane1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	        tabbedPane2.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	        add(addressbookPane);
	        //frame.show();
	}

 
	 

	//public static void main(String[] args) 
	//{
		//MyAddressBook1 book=new MyAddressBook1();
	//}
	
	 
	 
	 public void actionPerformed(ActionEvent ae){
		 String utype,username,password;
		 if(ae.getSource()== bttnLogin){
			 if (cbUserType.getSelectedIndex()==0){
				 utype="admin";
			 }
			 else {
				 utype="user";
			 }
				 	
			  username=txtUserName.getText();
			  password=txtPassword.getText();
			  
			  
			  		if(txtUserName.getText().equals("") || txtPassword.getText().equals(""))
		      		{

			  			lblMessage1.setForeground(Color.RED);
			  			lblMessage1.setText("Please enter both Username and Password");
	 		        }
			        else
	           	    {
	           	          
			        	
			        	String[] tokenarray={"checklogin",txtUserName.getText(),txtPassword.getText(),utype};
			        	
			       	        	
			        		String checkUserString=new TokenMe(tokenarray).gettokenizedString();
			        		DataObject myObject=new DataObject();
			        		myObject.setMessage(checkUserString);
			        		 try{
			        			 
			        			 if(socketToServer.isClosed())
			        			 {
			        				 
			        				 socketToServer=new Socket("osl81.njit.edu",6969);
			        				 System.out.println("Socket again created.");
			        				
			        			 }
			        			 else
			        			 {
			        				 System.out.println("Socket Not Closed:" + socketToServer.isConnected()); 
			        			 }
			        			 ObjectOutputStream out=new ObjectOutputStream(socketToServer.getOutputStream());
			        			 out.writeObject(myObject);
			        			 ObjectInputStream in=new ObjectInputStream(socketToServer.getInputStream());
			        			 myObject= (DataObject)in.readObject();
			        			 System.out.println("Afterchecking:"+myObject.getMessage());
			        			 
			        			 if(myObject.getMessage().equals("successful"))
			        			 {
			        				 lblMessage1.setForeground(Color.blue);
			        				 lblMessage1.setText("Enjoy Using AddressBook");
			        				 if(utype.equals("admin"))
			        				 {
			        					 System.out.println("Showing Admin Tab");
			        					 CardLayout cl = (CardLayout)(cards.getLayout());
			        					 cl.show(cards, ADMIN);
			        					
			        				 }
			        				 else
			        				 {
			        					 System.out.println("Showing User Tab");
			        					 CardLayout cl = (CardLayout)(cards.getLayout());
			        					 cl.show(cards, USER);
			        				 }			        				 

			 				         CardLayout c2 = (CardLayout)(loginLogoutpane.getLayout());
			 				         c2.show(loginLogoutpane, LOGOUT);
			        				 txtUserName.setText("");
		        					 txtPassword.setText("");
			        			 }
			        			 else
			        			 {
			        				 lblMessage1.setForeground(Color.RED);
			        				 lblMessage1.setText("Incorrect Username or Password.Try again.");
			        			 }
			        			 in.close();
			        			 out.close();
			        		
			        			 
			        			 
			        		 	}
			        			 catch(Exception e){
			        					lblMessage1.setForeground(Color.RED);
			        					lblMessage1.setText("Sorry Server is down");
			        				 System.out.println(e);
			        			}
			        	
			        	}				 
				 
				 
		}//end of bttnlogin
		 
		 /////////////////////////////////
		 if(ae.getSource()== bttnLogOut)
		 {
			 	//send msg to server to kill tread
			 
			 	CardLayout c2 = (CardLayout)(loginLogoutpane.getLayout());
		        c2.show(loginLogoutpane, LOGIN);
		        
		        CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards, INITIALPAGE); 
		        
		        lblMessage1.setForeground(Color.black);
		        lblMessage1.setText("");
		        
		        try{
		        	socketToServer.close();
		        	System.out.println("Socket closed!!!");
		        }
		        catch(Exception e)
		        {
		        	System.out.println(e);
		        }
			 
		 }
		 
		 
	 }//end of actionperformed
	
}
