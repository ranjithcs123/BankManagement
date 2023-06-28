package kodnestbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

@SuppressWarnings("unused")
public class KodnestBank {
private Connection con=null;
private Scanner scan=new Scanner (System.in);
public static KodnestBank ref=null;

public static KodnestBank getInstance()
{
	if(ref==null)
	{
		ref=new KodnestBank();
		
	}
	return ref;
}

 private KodnestBank()
  {
	String path="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@//localhost:1521/XE";
	String user="system";
	String password="system";
	
	try {
		//Loading The Driver
		Class.forName(path);
		
		//Establishing Connection
		con=DriverManager.getConnection(url,user,password);
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}
void register()
{
	try{
		System.out.println("Enter Accno");
		String accno=scan.next();
		System.out.println("Enter password");
		String pwd=scan.next();
		System.out.println("Enter Confirm Password");
		String cpwd=scan.next();
		System.out.println("Enter Name");
		String name=scan.next();
		System.out.println("Enter Amount");
		int amt=scan.nextInt();
		System.out.println("Enter Age");
		int age=scan.nextInt();
		System.out.println("Enter Email");
		String email=scan.next();
		System.out.println("Enter Phone Number");
		String phone=scan.next();
		if(accno.length()!=10||pwd.equals(cpwd)==false||name.length()<3||amt<=0||age<18||email.length()<10||phone.length()!=10)
		{
		System.out.println("Registration Unsuccessful...please Retry...");	
		}
		else
		{
		String sql="insert into kodnestbank values(?,?,?,?,?,?,?)";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, accno);
		ps.setString(2, pwd);
		ps.setString(3, name);
		ps.setInt(4, amt);
		ps.setInt(5, age);
		ps.setString(6, email);
		ps.setString(7, phone);
		int nora=ps.executeUpdate();
		if(nora==1)
		{
			System.out.println("Registration Successfull");
		}
		else
		{
			System.out.println("Some Issue Occured Please Contact Nearest Branch..");
		}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		System.out.println(e);
	}


}
void login()
{
	try{
		String sql="select * from KODNESTBANK where ACC_NO=? and PASSWORD=?";
		PreparedStatement ps=con.prepareStatement(sql);
		System.out.println("Enter Your Account Number");
		String accno=scan.next();
		System.out.println("Enter Your Password");
		String password=scan.next();
		ps.setString(1, accno);
		ps.setString(2, password);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			System.out.println("Login Success....");
			System.out.println("Your Balance Is "+rs.getInt("amount"));
		}
		else
		{
			System.out.println("Invalid User or Password Please Try agian..");
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

void checkBalance()
{
	try{
		String sql="select * from KODNESTBANK where ACC_NO=? and PASSWORD=?";
		PreparedStatement ps=con.prepareStatement(sql);
		System.out.println("Enter Your Account Number");
		String accno=scan.next();
		System.out.println("Enter Your Password");
		String password=scan.next();
		ps.setString(1, accno);
		ps.setString(2, password);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			System.out.println("Your Balance Is "+rs.getInt("amount"));
		}
		else
		{
			System.out.println("Invalid User or Password Please Try agian..");
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
	

void transferAumont()
{
	try{
		String sql1="update kodnestbank set amount=amount-? where ACC_NO=? and PASSWORD=?";
		String sql2="update kodnestbank set amount=amount+? where ACC_NO=? ";
		con.setAutoCommit(false);
		PreparedStatement ps1=con.prepareStatement(sql1);
		PreparedStatement	ps2=con.prepareStatement(sql2);
		System.out.println("Enter from accno");
		String faccno=scan.next();
		System.out.println("Enter Password");
		String fpwd=scan.next();
		System.out.println("Enter To accno");
		String taccno=scan.next();
		System.out.println("Enter Amount To Transfer");
		int amt=scan.nextInt();
		
		ps1.setInt(1, amt);
		ps1.setString(2, faccno);
		ps1.setString(3, fpwd);

		int nora1=ps1.executeUpdate();
		
		ps2.setInt(1, amt);
		ps2.setString(2, taccno);
		
		int nora2=ps2.executeUpdate();
		
		System.out.println(nora1+" "+nora2);
		if(nora1==1 && nora2==1)
		{
			con.commit();
		}
		else
		{
			con.rollback();
		}
	}
	catch (Exception e) {
		try{
		con.rollback();
		System.out.println("Some Problem In Server ...Dont Worry Your Money Is SAFE WITH KODNEST BANK....");
		}catch (Exception e1) {
			e1.printStackTrace();

		}
	}
	
}
void updatePassword()
{
		try{
			String sql="select * from KODNESTBANK where ACC_NO=? and PASSWORD=?";
			PreparedStatement ps=con.prepareStatement(sql);
			System.out.println("Enter Your Account Number");
			String accno=scan.next();
			System.out.println("Enter Your Password");
			String password=scan.next();
			ps.setString(1, accno);
			ps.setString(2, password);
			
			ResultSet rs=ps.executeQuery();
            if(rs.next())
			{
				String sql2="update KODNESTBANK set PASSWORD=? where ACC_NO=? and PASSWORD=?";
				PreparedStatement ps1=con.prepareStatement(sql2);
				System.out.println("Enter new Password");
				String newpwd=scan.next();
				System.out.println("Confirm new Password");
				String cnewpwd=scan.next();
				if(newpwd.equals(cnewpwd))
				{
				ps1.setString(1, cnewpwd);
				ps1.setString(2, accno);
				ps1.setString(3, password);
				int nora=ps1.executeUpdate();
				if(nora==1)
				{
					System.out.println(nora);
					System.out.println("Password Updated ");
				}
				else
				{
					System.out.println("Some Problem Occured PLease try Again.....");
				}
				}
				else {
					
					System.out.println("New password and confirm new password is not matching");
				}
				
			}
			else
			{
				System.out.println("Old Accno and Pwd is Invlaid...");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
void updateProfile()
{
	try{
		String sql="select * from kodnestbank where ACC_NO=? and PASSWORD=?";
		PreparedStatement ps=con.prepareStatement(sql);
		System.out.println("Enter Your Account Number");
		String accno=scan.next();
		System.out.println("Enter Your Password");
		String password=scan.next();
		ps.setString(1, accno);
		ps.setString(2, password);
		ResultSet rs=ps.executeQuery();
		
		if(rs.next())
		{
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("FOLLOWING ARE YOUR DETAILS");
			
			System.out.println("accno= "+rs.getString(1));
			System.out.println("Password= "+rs.getString(2));
			System.out.println("Name= "+rs.getString(3));
			System.out.println("Amount= "+rs.getInt(4));
			System.out.println("Age= "+rs.getInt(5));
			System.out.println("Email= "+rs.getString(6));
			System.out.println("Phone= "+rs.getString(7));
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		else
		{
			System.out.println("Invalid User or Password Please Try agian..");
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
}
void deleteAccount()
{
	try{
		con.setAutoCommit(false);
		String sql="select * from kodnestbank where ACC_NO=? and PASSWORD=?";
		PreparedStatement ps=con.prepareStatement(sql);
		System.out.println("Enter Your Account Number");
		String accno=scan.next();
		System.out.println("Enter Your Password");
		String password=scan.next();
		ps.setString(1, accno);
		ps.setString(2, password);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			String sql1="delete from KODNESTBANK where ACC_NO=?";
			PreparedStatement ps1=con.prepareStatement(sql1);
			ps1.setString(1, accno);
			int nora=ps1.executeUpdate();
			if(nora==1)
			{
				con.commit();
				System.out.println("Account Deactivated...You can login and check ...");
			}
			else
			{
				con.rollback();
			}
		}
		else
		{
			System.out.println("Invalid User or Password Please Try agian..");
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
}