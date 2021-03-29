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
   
   public Account getAccount(int accountID)
   {
      return accountList.get(accountID);
   }
   
   public ArrayList<Account> getAccountList()
   {
      return accountList;
   }
   
   public int read(int accountID, Transaction transaction)
   {
      LockType lockType = new LockType();
      lockType.setType(1);
      Account account = getAccount(accountID);
      (TransactionServer.lockManager).setLock(account, transaction, lockType);
      return (getAccount(accountID)).getBalance();
   }
   
   public int write(int accountID, Transaction transaction, int balance)
   {
      Account account = getAccount(accountID);
      LockType lockType = new LockType();
      lockType.setType(2);
      
      (TransactionServer.lockManager).setLock(account,transaction,lockType);
      
      account.setBalance(balance);
      return balance;
   }
}
