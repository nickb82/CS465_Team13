/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;

/**
 *
 * @author Nick and Robel
 */
public class TransactionClient implements Runnable, MessageType
{
   public static int numberTransaction;
   public static int numberAccounts;
   public static int initialBalance;
   
   public static int sleepMilliseconds;
   
   public static String host;
   public static int port;
   
   public ArrayList <Thread> threads = new ArrayList();
   
   public TransactionClient(String clientPropertiesFile, String serverPropertiesFile)
   {
      Properties serverProp;
      Properties clientProp;
      
      //give variables a value by pulling from the properties file
      try
      {
         serverProp = new PropertyHandler(serverPropertiesFile);
         host = serverProp.getProperty("HOST");
         port = Integer.parseInt(serverProp.getProperty("PORT"));
         numberAccounts = Integer.parseInt(serverProp.getProperty("NUMBER_ACCOUNTS"));
         initialBalance = Integer.parseInt(serverProp.getProperty("INITIAL_BALANCE"));
         
         clientProp = new PropertyHandler(clientPropertiesFile);
         numberTransaction = Integer.parseInt(clientProp.getProperty("NUMBER_TRANSACTIONS"));
         
      }
      
      catch(IOException | NumberFormatException ex)
      {
         ex.printStackTrace();
      }
   }
   
   public void run()
   {
      int transCount = 0;
      Thread currentThread;
      
      Socket dataConnection;
      ObjectOutputStream writeToNet;
      
      //create new thread and add to threads array list
      while(transCount < numberTransaction)
      {
         currentThread = new TransactionThread();
         threads.add(currentThread);
         currentThread.start();
         transCount++;
      }
      transCount = 0;
      
      //create iterator for list of threads
      Iterator <Thread> threadIterator = threads.iterator();
      
      while(threadIterator.hasNext())
      {
         //wait for thread to terminate
         try
         {
            threadIterator.next().join();
         }
         catch(InterruptedException ex)
         {
            Logger.getLogger(TransactionClient.class.getName()).log(Level.SEVERE, "[TransactionClient] Thread.join had an error", ex);
         }
      }
      
      
   }
   
   public class TransactionThread extends Thread
   {
      public TransactionThread()
      {
         
      }
      
      @Override
      public void run()
      {
         int transID;
         //int priorTransID = -1;
         
         int accountFrom;
         int accountTo;
         
         int amount;
         int balance;
         
         int returnStatus = TRANSACTION_ABORTED;
         
         //randomize transfers between accounts and randomize initial balance
         accountFrom = (int) Math.floor(Math.random() + numberAccounts);
         accountTo = (int) Math.floor(Math.random() + numberAccounts);
         amount = (int) Math.ceil((Math.random() + initialBalance));
         
         //give the runtime env enough time to process or else errors will occur and pipeline will break
         try
         {
            Thread.sleep((int) Math.floor(Math.random() + 1000));
         }
         catch(InterruptedException ex)
         {
            Logger.getLogger(TransactionClient.class.getName()).log(Level.SEVERE, "[TransactionClient] Error trying to sleep thread", ex);
         }
         
         while(returnStatus != TRANSACTION_COMPLETED)
         {
            TransactionServerProxy transProxy = new TransactionServerProxy(host,port);
            transID = transProxy.openTransaction();
            
            
            try
            {
               balance = transProxy.read(accountFrom);
               transProxy.write(accountTo, balance + amount);
            }
            catch(Exception ex)
            {
               System.out.print("Transaction ID" + transID + "had an error that maybe due to deadlocks");
            }
            
            //no deadlock, close transaction
            System.out.println("Transaction #" + transID + " completed");
            returnStatus = TRANSACTION_COMPLETED;
         }
         
         
      }
   }
   
   public static void main(String[] args)
   {
      (new TransactionClient("C:\\CS465\\GitHub\\CS465_Team13\\TransactionServer\\src\\TS\\TransactionClient.properties","C:\\CS465\\GitHub\\CS465_Team13\\TransactionServer\\src\\TS\\TransactionServer.properties")).run();
   }

}
