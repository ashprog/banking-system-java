package com.bankingsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

class UserData implements Serializable
{
	private static final long serialVersionUID = -2973470947399491416L;
	private String firstName;
	private String lastName;
	private String accountNo;
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public String getName() {
		return firstName+" "+lastName;
	}
	
}

public class Register
{
     static void main()
     {
    	 try {
    		 
    		 Scanner registerSc = new Scanner(System.in);
    		 UserData ud = new UserData();
    		 ArrayList<UserData> userList;
    		 LoginData ld = new LoginData();
    		 UserBalance ub = new UserBalance();
    		 
    		 File registerFile = new File("register.txt");
        	 if(registerFile.exists())
        	 {
        		 FileInputStream fis = new FileInputStream(registerFile);
            	 ObjectInputStream ois = new ObjectInputStream(fis);
    			 userList = (ArrayList<UserData>) ois.readObject();
            	 ois.close();
        	 } else {
        		 registerFile.createNewFile();
        		 userList = new ArrayList<>();
        	 }
        	 
        	 System.out.println("Enter the user's first name: ");
        	 ud.setFirstName(registerSc.nextLine());
        	 System.out.println("Enter the user's last name: ");
        	 ud.setLastName(registerSc.nextLine());
        	 System.out.println("Enter the user's password name: ");
        	 ld.setPassword(registerSc.nextLine());
        	 
        	 System.out.println("Press 'Y' to create account or 'N' to exit.");
        	 char ch = registerSc.next().charAt(0);
        	 if(!(ch=='Y' || ch=='y'))
        		 return;
        	
        	 UserCount uc = new UserCount();
        	 ud.setAccountNo(Integer.toString(uc.userCount()));
        	 ld.setAccountNo(ud.getAccountNo());
        	 ub.setAccountNo(ud.getAccountNo());
        	 
        	 userList.add(ud);
        	 FileOutputStream fos = new FileOutputStream(registerFile);
        	 ObjectOutputStream oos = new ObjectOutputStream(fos);
        	 oos.writeObject(userList);
        	 oos.close();
        	 
        	 if(Login.writeInFile(ld) && Balance.writeInFile(ub))
        		 System.out.println("Account created successfully. \nYour account no. is: "+ud.getAccountNo());
        	 else 
        		 System.out.println("Error occured.");
    	 } catch(Exception e) {
    		 e.printStackTrace();
    	 }
     }
}

class UserCount
{
	protected int userCount() 
	{
		int userCount=0;
		
		try {
			
			File userFile = new File("user_count.txt");
			
			if(!userFile.exists())
			{
				FileWriter userCountFW = new FileWriter(userFile);
				userCountFW.write("1000");
				userCountFW.close();
			}
			
			Scanner userCountSc = new Scanner(userFile);
			userCount = Integer.parseInt(userCountSc.next());
			userCountSc.close();
			
			FileWriter userCountFW = new FileWriter(userFile);
			userCountFW.write(Integer.toString(++userCount));
			userCountFW.close();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return userCount;
        
    }
}
