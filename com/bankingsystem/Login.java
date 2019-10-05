package com.bankingsystem;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

class LoginData implements Serializable
{
	private static final long serialVersionUID = 1444278469712858177L;
	private String password;
	private String accountNo;
	
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountNo()
	{
		return accountNo;
	}
	boolean equals(LoginData ld)
	{
		if((this.accountNo.equals(ld.accountNo)) && (this.password.equals(ld.password)))
			return true;
		
		return false;
	}
}

public class Login 
{
	static File loginFile = new File("login.txt");
	
	static String main()
	{
		try {
		
		LoginData ld = new LoginData();
		Scanner loginSc = new Scanner(System.in);
		
		if(!loginFile.exists())
		{
			System.out.println("No account exists.");
			return "false";
		}
			
		FileInputStream fis = new FileInputStream(loginFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<LoginData> loginList = (ArrayList<LoginData>) ois.readObject();
		ois.close();
		
		System.out.println("Enter your Account No. : ");
		ld.setAccountNo(loginSc.nextLine());
		System.out.println("Enter your Password : ");
		ld.setPassword(loginSc.nextLine());
		
		for(LoginData temp : loginList)
		{
			if(ld.equals(temp))
			{
				System.out.println("Login Successful.");
				return ld.getAccountNo();
			}
		}
		
		System.out.println("Invalid Account No. or Password");
		return "false";
		
		} catch (Exception e)
		{
			e.printStackTrace();
			return "false";
		}
	}
	
	static boolean writeInFile(LoginData ld)
    {
    	boolean status = false;
    	try {
    		
    		ArrayList<LoginData> loginList;
    		
    		if(loginFile.exists())
         	 {
       		     FileInputStream fis = new FileInputStream(loginFile);
            	 ObjectInputStream ois = new ObjectInputStream(fis);
   			     loginList = (ArrayList<LoginData>) ois.readObject();
           	     ois.close();
       	     } else {
       		     loginFile.createNewFile();
       		     loginList = new ArrayList<>();
         	 }
    		
    		loginList.add(ld);
    		FileOutputStream fos = new FileOutputStream(loginFile);
       	    ObjectOutputStream oos = new ObjectOutputStream(fos);
        	oos.writeObject(loginList);
        	oos.close();
        	
    		status = true;
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return status;
    }
}
