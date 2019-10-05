package com.bankingsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Transfer {

	static void main(UserBalance ub1) {
		
		Scanner sc = new Scanner(System.in);
		String name;
		System.out.println("Enter the account no. of the user: ");
		String accountNo_RT = sc.nextLine();
		try {
			
			File registerFile = new File("register.txt");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(registerFile));
			ArrayList<UserData> userList = (ArrayList<UserData>) ois.readObject();
			boolean isFound = false;
			for(UserData ud : userList)
			{
				if(ud.getAccountNo().equals(accountNo_RT))
				{
					isFound = true;
					name = ud.getName();
					System.out.println("User found. Name: "+name);
					break;
				}
			}
			if(!isFound) {
				System.out.println("User not found");
				return;
			}
			
	        UserBalance ub2 = new UserBalance();
	        ub2.setAccountNo(accountNo_RT);
	        
	        System.out.println("Enter the amount you want to transfer: ");
	        double amt = Double.parseDouble(sc.nextLine());
	        
	        if(amt>0.0 && amt<=Balance.getBalance(ub1.getAccountNo()))
	        {
	        	if( Transaction.tran(ub1, amt,'t', ub2.getAccountNo()) &&
	        	                           Transaction.tran(ub2, amt,'r', ub1.getAccountNo()) )
	        	{
	        		System.out.println("Success. Amount Transferred.");
	        	} else
	        		System.out.println("Some error occured.");
	        	
	        }else {
	        	System.out.println("Transfer amount is either greater than balance or less than 0");
	        }
		} catch (Exception e)
		{
			e.printStackTrace();
		}	
	}

}
