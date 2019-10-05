package com.bankingsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class UserTrans implements Serializable
{
	private static final long serialVersionUID = -3758646799355226546L;
	
	private String accountNo;
	private String type;
	private double amount;
	private String tran_id;
	private String status = "Pending";
	private LocalDateTime ldt;
	private String accountNo_RT;  //account no. of transfer/received person
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setTran_id(String tran_id) {
		this.tran_id = tran_id;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public String getType() {
		return type;
	}
	public double getAmount() {
		return amount;
	}
	public String getTran_id() {
		return tran_id;
	}
	public String getStatus() {
		return status;
	}
	public LocalDateTime getLdt() {
		return ldt;
	}
	public void setLdt(LocalDateTime ldt) {
		this.ldt = ldt;
	}
	public String getAccountNo_RT() {
		return accountNo_RT;
	}
	public void setAccountNo_RT(String accountNo_RT) {
		this.accountNo_RT = accountNo_RT;
	}
	
}

public class Transaction {
	
	static File tranFile = new File("transactions.txt");
	
	static void seeTrans(String acn)
	{
		boolean isFound = false;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		try {
			if(!tranFile.exists())
			{
				System.out.println("No transactions exist.");
				return;
			}
			
			FileInputStream fis = new FileInputStream(tranFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<UserTrans> tranList = (ArrayList<UserTrans>) ois.readObject();
			ois.close();
			
			System.out.println("TranID  Type    Amount  Status  To/From  Date   Time\n");
			for(UserTrans ut : tranList)
			{
				if(ut.getAccountNo().equals(acn))
				{
					System.out.println(ut.getTran_id()+"   "+ut.getType()
					  +"  "+String.format("%.2f", ut.getAmount())+"  "+ut.getStatus()+
					  "  "+ut.getAccountNo_RT()+" "+ut.getLdt().format(dtf));
					isFound = true;
				}
			}
			if(!isFound)	
			   System.out.println("No transactions exist.");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	static boolean tran(UserBalance ub,double amt,char c,String acn_rt)
	{
		boolean status = false;
		String id;
		ArrayList<UserTrans> tranList;
		
		UserTrans ut = new UserTrans();
		ut.setAccountNo(ub.getAccountNo());
		ut.setAmount(amt);
		ut.setTran_id(tranID());
		ut.setLdt(LocalDateTime.now());
		ut.setAccountNo_RT(acn_rt);
		
		if(c=='d' || c=='r')
		{
			if(c=='d')
			    ut.setType("Deposit");
			else if(c=='r')
				ut.setType("Received");
			
			ub.setBal(Balance.getBalance(ub.getAccountNo())+amt);
		} else if(c=='w' || c=='t') {
			
			if(c=='w')
			    ut.setType("Withdraw");
			else if(c=='t')
				ut.setType("Transfer");
		
			ub.setBal(Balance.getBalance(ub.getAccountNo())-amt);
		}
		
		try { 	
			
			if(tranFile.exists())
			{
				FileInputStream fis = new FileInputStream(tranFile);
				ObjectInputStream ois = new ObjectInputStream(fis);
				tranList = (ArrayList<UserTrans>) ois.readObject();
				ois.close();
			} else {
				tranFile.createNewFile();
				tranList = new ArrayList<>();
			}
			
			tranList.add(ut);
			FileOutputStream fos = new FileOutputStream(tranFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tranList);
			oos.close();
			
		    if((status = Balance.setBalance(ub)))
		    	tranList.get(tranList.size()-1).setStatus("Success");
		    else 
		    	tranList.get(tranList.size()-1).setStatus("Failed");
          
		    ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(tranFile));
		    oos1.writeObject(tranList);
			oos1.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return status;
	}
	
	static String tranID()
	{
		File tranIDFile = new File("tranID.txt"); 
		String id = "10000";
		
		try {
			if(!tranIDFile.exists())
			{
				tranIDFile.createNewFile();
				FileWriter tranIdFW = new FileWriter(tranIDFile);
				tranIdFW.write("10000");
				tranIdFW.close();
			}
			
			Scanner sc = new Scanner(tranIDFile);
			id = Integer.toString( Integer.parseInt(sc.nextLine())+1 );
			
			FileWriter tranIdFW = new FileWriter(tranIDFile);
			tranIdFW.write(id);
			tranIdFW.close();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return id;
	}

}
