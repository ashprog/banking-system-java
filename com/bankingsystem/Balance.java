package com.bankingsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

class UserBalance implements Serializable
{
	private static final long serialVersionUID = 2416325743332373951L;
	
	String accountNo;
	double bal=0.0;
	
	void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	void setBal(double bal) {
		this.bal = bal;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public double getBal() {
		return bal;
	}
}

public class Balance {
	
	static File balFile = new File("balance.txt");
	
	static double getBalance(String acn)
	{
		double bal = 0.0;
		try {
			
		     FileInputStream fis = new FileInputStream(balFile);
		     ObjectInputStream ois = new ObjectInputStream(fis);
		     ArrayList<UserBalance> balList = (ArrayList<UserBalance>) ois.readObject();
		     ois.close();
		     
		     for(UserBalance ub : balList)
		     {
		    	 if(acn.equals(ub.getAccountNo()))
		    	 {
		    		 bal = ub.getBal();
		    		 break;
		    	 }
		     }
	 	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return bal;
	}
	
	static boolean setBalance(UserBalance ub)
	{
		boolean status = false;
		try {
			
            FileInputStream fis = new FileInputStream(balFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<UserBalance> balList = (ArrayList<UserBalance>) ois.readObject();
			ois.close();
			
			for(int i=0;i<balList.size();i++)
			{
				if(balList.get(i).getAccountNo().equals(ub.getAccountNo()))
				{
					balList.get(i).setBal(ub.getBal());
					break;
				}
			}
			
			FileOutputStream fos = new FileOutputStream(balFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(balList);
			oos.close();
			
			status = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}

	static boolean writeInFile(UserBalance ub)
	{
		boolean status = false;
		try {
			
			ArrayList<UserBalance> balList;
			
			if(balFile.exists())
			{
				FileInputStream fis = new FileInputStream(balFile);
				ObjectInputStream ois = new ObjectInputStream(fis);
				balList = (ArrayList<UserBalance>) ois.readObject();
				ois.close();
			} else {
				balFile.createNewFile();
				balList = new ArrayList<>();
			}
			
			balList.add(ub);
			FileOutputStream fos = new FileOutputStream(balFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(balList);
			oos.close();
			
			status = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
}
