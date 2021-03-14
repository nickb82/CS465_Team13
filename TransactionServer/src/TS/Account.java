/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

/**
 *
 * @author young
 */
public class Account 
{
   int balance;
   int accountID;
   
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
}
