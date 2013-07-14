import java.io.*;
public class TokenMe {
	
	StringBuilder tokenizedString=new StringBuilder();
	
	
	public String gettokenizedString(){
		return tokenizedString.toString();
	}
	
	public TokenMe(String tokenarray[])
	{ 	
		 int i;
		 System.out.println("I am in Token Me");
		 for  (i = 0; i < (tokenarray.length-1); i++) 
		 {		       
			 tokenizedString.append(tokenarray[i]);
			 tokenizedString.append("~");
			 
		 }
		 tokenizedString.append(tokenarray[i]);
		 
	}
}
