import java.io.*;

class Contact implements Serializable
    {
    	 private String FName;
    	 private String LName;
    	 private String Mname;
    	 private String EMail;
    	 private String Address;
    	 private String PhoneNo;
    	 private String Webpage;
    	 private String Bday;

    	 public void setDetails(String fname, String lname, String nname,String email, String address, String phone, String web, String bday)
    	     {
    	     	  FName = fname;
    	     	  LName = lname;
    	     	  Mname = nname;
    	     	  EMail = email;
    	     	  Address = address;
    	     	  PhoneNo = phone;
    	     	  Webpage = web;
    	     	  Bday = bday;
             }


         public String getFName()
             {
             	  return FName;
             }

         public String getLName()
             {
             	  return LName;
             }

         public String getMname()
             {
             	  return Mname;
             }

          public String getEMail()
             {
             	  return EMail;
             }

          public String getAddress()
             {
             	  return Address;
             }

          public String getPhoneNo()
             {
             	  return PhoneNo;
             }

          public String getWebpage()
             {
             	  return Webpage;
             }

          public String getBday()
             {
             	  return Bday;
             }
    }

