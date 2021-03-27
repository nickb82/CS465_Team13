/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Nick and Robel
 */
public class TransactionManager implements MessageType
{
   private static int transactionCounter = 0;
   //list of active transactions
   private static ArrayList<Transaction> transactionList = new ArrayList<>();
   
   TransactionManager()
   {
   
   }
   
  public ArrayList<Transaction> getTransactions()
  {
     return transactionList;
  }
  
  public void runTransaction(Socket client)
  {
     (new TransactionManagerWorker(client)).start();
  }
  
  public class TransactionManagerWorker extends Thread
   {
     //network
      Socket clientSocket = null;
      ObjectOutputStream writeToNet = null;
      ObjectInputStream readFromNet = null;
      Message message = null;
      
      //transactions
      Transaction transaction = null;
      int accountNumber = 0;
      int balance = 0;
      
      boolean running = true;
      TransactionManagerWorker(Socket localClientSocket)
      {
         this.clientSocket = localClientSocket;

         try
         {
            readFromNet = new ObjectInputStream(clientSocket.getInputStream());
              writeToNet = new ObjectOutputStream(clientSocket.getOutputStream());
         }
         catch(IOException ioe)
         {
            System.out.println("[TransactionManagerWorker] Failed to open object streams");
            ioe.printStackTrace();
            System.exit(1);
         }
      }

      public void run()
      {
         //loop that reads messages, coming from client & translates
         //them into high-level actions
         while(running)
         {
            try
            {
               message = (Message) readFromNet.readObject();
            }
            catch(IOException | ClassNotFoundException e)
            {
               System.out.println("[TransactionManagerWorker.run] Message could not be read");
               System.exit(1);
            }

            switch(message.getType())
            {
               //integers that represent, symbolic constant with interface
               case OPEN_TRANSACTION:

                  
                  synchronized(transaction)
                  {
                     transaction = new Transaction(transactionCounter++);
                     transactionList.add(transaction);
                  }
                  
                  try
                  {
                     writeToNet.writeObject(transaction.getID());
                  }
                  
                  catch(IOException ioe)
                  {
                     System.out.println("[TransactionManagerWorker.run] OPEN_TRansaction - Error when writting transaction ID");
                  }
                  
                  transaction.log("[TransactionManagerWorker.run] OPEN_TRANSACTION #" + transaction.getID());
                  
                  break;
                  
               case CLOSE_TRANSACTION:
                  
                  LockManager.unLock(transaction);
                  transaction.remove(transaction);
                  
                  try
                  {
                     readFromNet.close();
                     writeToNet.close();
                     clientSocket.close();
                     running = false;
                  }
                  
                  catch(IOException ioe)
                  {
                     System.out.println("[TransactionmanagerWorker.run] CLOSE_TRANSACTION - Error when closing connection");
                  }
                  
                  transaction.log("[TransactionManagerWorker.run] CLOSE_TRANSACTION #" + transaction.getID());
                  
                  if(TransactionServer.transactionView)
                  {
                     System.out.println(transaction.getLog());
                  }
                  
                  break;
                  
               case READ_REQUEST:
                  
                  // get content is not a string?
                  //accountNumber = (Integer) message.getContent();
                  accountNumber = Integer.parseInt(message.getContent());
                  transaction.log("[TransactionManagerWorker.run] READ_REQUEST ------- account #" + accountNumber + "balance $" + balance);
                  
                  balance = TransactionServer.AccountManger.read(accountNumber, transaction);
                  
                  try
                  {
                     writeToNet.writeObject((Integer) balance);
                  }
                  
                  catch(IOException ioe)
                  {
                     System.out.println("[TransactionManagerWorker.run] READ_REQUEST -> error when writing to object");
                  }
                  
                  transaction.log("[TransactionManagerWorker.run] READ_REQUEST ----- account #" + accountNumber + "balance $" + balance);
                  
                  break;
                  
               case WRITE_REQUEST:
                  //message contetn a string? or an object array
                  Object[] content = (Object[]) message.getContent();
                  accountNumber = (Integer) content[0];
                  balance = (Integer) content[1];
                  transaction.log("[TransactionmanagerWorker.run] WRITE_REQUEST ----- account #" + accountNumber + ", new balance: " + balance);
                  
                  balance = TransactionServer.AccountManager.write(accountNumber, transaction, balance);
                  
                  try
                  {
                     writeToNet.writeObject((Integer) balance);
                  }
                  
                  catch(IOException ioe)
                  {
                     System.out.println("[TransactionManagerWorker.run] WRITE_REQUEST -> Error when writing to object");
                  }
                  
                  transaction.log("[TransactionManagerWorker.run] WRITE_REQUEST ---- account #" + accountNumber + "new balance: " + balance);
                  
                  break;
                  
               default:
                  System.out.println("[TransactionManagerWorker.run] Warning: Message type was not valid or could not be read");
            }
         }
      }
   }
  
}
