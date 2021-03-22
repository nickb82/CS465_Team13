/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

/**
 *
 * @author Nick and Robel
 */
public class Account {
   static int balance;
   static int accountID;
   
   public Account(int accountID, int balance)
   {
      Account.accountID = accountID;
      Account.balance = balance;
   }

   public int getBalance() 
   {
      return balance;
   }

   public void deposit(int num) 
   {
      balance += num;
   }

   public void withdraw(int num) 
   {
      balance -= num;
   }

   public int getID() 
   {
      return accountID;
   }

   public void setID(int newAccount) 
   {
      this.accountID = newAccount;
   }
   
   public void setBalance(int balance)
   {
      this.balance = balance;
   }
}
