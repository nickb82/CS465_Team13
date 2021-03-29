/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Nick and Robel
 */
public class TransactionServerProxy implements MessageType
{
   Message clientMessage;
   InetAddress IPAdress;
   String host;
   int transID;
   public int portNum;
   Socket connection;

   public ObjectOutputStream writeToNet = null;
   public ObjectInputStream readFromNet = null;
   
   TransactionServerProxy(String host, int port)
   {
      this.host = host;
      portNum = port;
   }
   
   public int openTransaction()
   {
      Message openTransMess = new Message(OPEN_TRANSACTION,null);
      
      try
      {
         connection = new Socket(host,portNum);
         writeToNet = new ObjectOutputStream(connection.getOutputStream());
         readFromNet = new ObjectInputStream(connection.getInputStream());
         
         writeToNet.writeObject(openTransMess);
         transID = (Integer) readFromNet.readObject();
      }
      catch(Exception ex)
      {
         System.out.println("[TransactionServerProxy] Error trying to create openTransaction Message");
         ex.printStackTrace();
      }
      
      return transID;
   }
   
   public void closeTransaction()
   {
      Message closeTransMess = new Message(CLOSE_TRANSACTION, null);
      
      try
      {
         writeToNet.writeObject(closeTransMess);
         
         readFromNet.close();
         writeToNet.close();
         connection.close();
      }
      
      catch(Exception ex)
      {
         System.out.println("[TransactionServerProxy] Error trying to close transaction");
         ex.printStackTrace();
      }
   }
   
   public int read(int accountNumber)
   {
      Message message = new Message(READ_REQUEST,null);
      
      try
      {
         writeToNet.writeObject(message);
         message = (Message) readFromNet.readObject();
      }
      
      catch(Exception ex)
      {
         System.out.println("[TransactionServerProxy] Error trying to read");
         ex.printStackTrace();
      }
      
      return (Integer) message.getContent();
   }
   
   public int write(int accountNumber, int amount)
   {
      Object[] content = new Object[]{accountNumber, amount};
      Message message = new Message(WRITE_REQUEST, content);
      
      try
      {
         writeToNet.writeObject(message);
         message = (Message) readFromNet.readObject();
      }
      
      catch(IOException ioe)
      {
         System.out.println("[TransactionServerProxy] Error when trying to write");
         ioe.printStackTrace();
      }
      
      return (Integer) message.getContent();
   }
   
}
