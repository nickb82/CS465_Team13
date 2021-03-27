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
public class Transaction {
   int id;
   //list of locks
   static ArrayList<Lock> holds = new ArrayList<>();
   static ArrayList<String> info = new ArrayList<>();
   //logging information
   Account account;
   
   Transaction(int transactionID)
   {
      this.id = transactionID;
   }

   public int getID() 
   {
      return id;
   }

   public void setID(int num) 
   {
      this.id = num;
   }

   public void abort() 
   {

   }
   
   public void read(Account acctNum) 
   {
      acctNum.getBalance();
   }
   
   public void write(Account acctNum, int amount) 
   {
      if (amount < 0) {
         acctNum.withdraw(amount);
      }

      else if (amount > 0) {
         acctNum.deposit(amount);
      }

   }
   
   public void log(String logInfo)
   {
      info.add(logInfo);
   }
}
