/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 *
 * @author Nick and Robel
 */
public class TransactionServer extends Thread
{
   public static AccountManager acctManager = null;
   public static TransactionManager transManager = null;
   public static LockManager lockManager = null;
   
   static ServerSocket serverSckt = null;
   
   static int messageCount = 0;
   
   boolean applyLocking;
   
   public TransactionServer(String serverPropertiesFile)
   {
      Properties serverProp = null;
      int numberAccounts;
      int initialBalance;
      
      try
      {
         serverProp = new PropertyHandler(serverPropertiesFile);
      }
      catch(IOException ex)
      {
         System.out.println("[TransactionServer] Could not open properties file");
         System.exit(1);
      }
      
      transManager = new TransactionManager();
      System.out.println("[TransactionServer] TransactionManager create");
      
      applyLocking = Boolean.valueOf(serverProp.getProperty("APPLY_LOCKING"));
      lockManager = new LockManager(applyLocking);
      System.out.println("[TransactionServer] LockManager created");
      
      numberAccounts = Integer.parseInt(serverProp.getProperty("NUMBER_ACCOUNTS"));
      initialBalance = Integer.parseInt(serverProp.getProperty("INITIAL_BALANCE"));
      
      TransactionServer.acctManager = new AccountManager(numberAccounts,initialBalance);
      System.out.println("[TranasactionServer] Account Manager created");
      
      try
      {
         serverSckt = new ServerSocket(Integer.parseInt(serverProp.getProperty("PORT")));
      }
      catch(IOException ioe)
      {
         System.out.println("[TransactionServer] Error with server socket");
         System.exit(1);
      }
   }
   
   public static synchronized int getMessageCount()
   {
      messageCount++;
      return messageCount;
   }
   
   @Override
   public void run()
   {
      while(true)
      {
         try
         {
            transManager.runTransaction(serverSckt.accept());
         }
         catch(IOException ioe)
         {
            System.out.println("[TransactionServer] Error trying to accept client");
         }
      }
      
   }
   
   public static void main(String[] args)
   {
      
   }
}
