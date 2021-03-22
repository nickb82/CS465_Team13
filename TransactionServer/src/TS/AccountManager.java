/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.util.ArrayList;

/**
 *
 * @author Nick and Robel
 */
public class AccountManager extends Thread
{
   //list of accounts
   static ArrayList<Account> accountList;
   static int numberAccounts;
   static int initialBalance;
   
   public AccountManager(int numberAccounts, int initialBalance)
   {
      accountList = new ArrayList();
      AccountManager.numberAccounts = numberAccounts;
      AccountManager.initialBalance = initialBalance;
      
      int accountIndex = 0;
      
      while(accountIndex < numberAccounts)
      {
         accountList.add(new Account(accountIndex,initialBalance));
         accountIndex++;
      }
   }
   
   public Account getAccount(int accountNumber)
   {
      return accountList.get(accountNumber);
   }
   
   public ArrayList<Account> getAccountList()
   {
      return accountList;
   }
}
