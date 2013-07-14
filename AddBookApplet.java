import java.awt.Container;

import javax.swing.JApplet;


public class AddBookApplet extends JApplet{

	public void init()
	{
		Container contentPane=getContentPane();
		contentPane.add(new MyAddressBook1());
	}
}
