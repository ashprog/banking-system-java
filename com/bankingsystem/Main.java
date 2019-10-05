package com.bankingsystem;

import java.util.Scanner;

public class Main {
	
	private static String accountNo = "false";
	static void setAccountNo(String acn)
	{
		accountNo = acn;
	}
	
	static String getAccountNo() {
		return accountNo;
	}

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		int choice=0;
		do {
		     System.out.println("1: REGISTER \n2: LOGIN \n0: EXIT");
		     System.out.println("\nEnter choice: ");
		     choice = Integer.parseInt(sc.nextLine());
		
		     switch(choice)
		     {
		        case 0: System.out.println("Exiting");
		                System.exit(0); 
		                break;
		        
		        case 1: Register.main(); break;
		
		        case 2: setAccountNo(Login.main()); break;
		
		        default: System.out.println("Invalid choice.");
		     }
		     
		     if(!getAccountNo().equals(new String("false")))
		     {
		    	 int choice2;
		    	 
		    	 do {
		    		 System.out.println("1: BALANCE \n2: DEPOSIT \n3: WITHDRAW \n4: TRANSFER "
		    		 		+ "\n5: TRANSACTIONS"
		    		 		+ "\n0: LOG OUT");
				     System.out.println("\nEnter choice: ");
				     choice2 = Integer.parseInt(sc.nextLine());
				
				     switch(choice2)
				     {
				        case 0: setAccountNo(new String("false"));
				        	    System.out.println("Logging Out...");
				                break;
				        
				        case 1: System.out.println("Your balance is: $"
				                                + String.format("%.2f", Balance.getBalance(getAccountNo()))); 
				                break;
				                
				        case 2: System.out.println("Enter the amount you want to deposit: ");
				                double deposit = Double.parseDouble(sc.nextLine());
				                if(deposit>=0.0)
		                        {
				                	UserBalance ub = new UserBalance();
				                	ub.setAccountNo(getAccountNo());
				       				if(Transaction.tran(ub, deposit,'d',"    "))
				       					{
				       					   System.out.println("Your new balance is: $"
					       		                + String.format("%.2f", Balance.getBalance(getAccountNo())));
				       					} else System.out.println("Deposit unsuccessful.");
		                        } else System.out.println("Invalid deposit amount");
				                
				                break;
				                
				        case 3: System.out.println("Enter the amount you want to withdraw: ");
		                        double withdraw = Double.parseDouble(sc.nextLine());
		                        if(withdraw>=0.0 && withdraw<=Balance.getBalance(getAccountNo()))
                                {
		                         	UserBalance ub = new UserBalance();
		                	        ub.setAccountNo(getAccountNo());
		       				        if(Transaction.tran(ub, withdraw,'w',"    "))
		       				     	{
		       					       System.out.println("Your new balance is: $"
			       		                  + String.format("%.2f", Balance.getBalance(getAccountNo())));
		       					    } else System.out.println("Withdraw unsuccessful.");
                                } else System.out.println("Withdrawing amount is greater "
                                		+ "than balance or less that 0.");
		                
		                        break;
		                
				        case 4: UserBalance ub = new UserBalance();
				                ub.setAccountNo(getAccountNo());
				                Transfer.main(ub);
				                break;
				                
				        case 5: Transaction.seeTrans(getAccountNo());
				                break;
				                
						default: System.out.println("Invalid choice.");
				     }
		           } while(choice2 != 0);
		     } 	 
		     
		} while(choice != 0);
	}

}
