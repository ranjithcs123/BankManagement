package kodnestbank;

import java.util.Scanner;

public class BankApp {

	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		KodnestBank kb=KodnestBank.getInstance();
		while(true)
		{
			System.out.println("WELCOME TO KODNEST BANK");
			System.out.println("Choose from below Menu");
			System.out.println("Press 1 ------------> REGISTRATION");
			System.out.println("Press 2 ------------> LOGIN");
			System.out.println("Press 3 ------------> CHECK BALANCE");
			System.out.println("Press 4 ------------> TRANSFER AMOUNT");
			System.out.println("Press 5 ------------> UPDATE PASSWORD");
			System.out.println("Press 6 ------------> UPDATE PROFILE");
			System.out.println("Press 7 ------------> DELETE ACCOUNT");
			System.out.println("Press 8 ------------> STOP");
			int choice=scan.nextInt();
			switch(choice)
			{
			case 1: kb.register();
			break;
			case 2: kb.login();
			break;
			case 3: kb.checkBalance();
			break;
			case 4: kb.transferAumont();
			break;
			case 5: kb.updatePassword();
			break;
			case 6: kb.updateProfile();
			break;
			case 7: kb.deleteAccount();
			break;
			default :System.exit(0);
			System.out.println("Thank you for Using Kodnest bank");
			}
		}
		      
	}

}
