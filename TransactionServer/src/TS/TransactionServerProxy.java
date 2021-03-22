/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Nick and Robel
 */
public class TransactionServerProxy 
{
   Message clientMessage;
   OpenMessage opMess;
   InetAddress IPAdress;
   int portNum;

   public ObjectOutputStream writeToNet = null;
   public void createMessage(String type)
   {
      if(type == "OPEN")
      {
         try(Socket senderSocket = new Socket(IPAdress,portNum))
         {
            clientMessage.setType(type);
            writeToNet = new ObjectOutputStream(senderSocket.getOutputStream());
            writeToNet.writeObject(clientMessage);
         }
         
         catch(IOException ioe)
         {
            System.out.println(ioe);
         }
      }
   }
   
   public void openTransaction()
   {
      createMessage("OPEN");
   }
   
   TransactionServerProxy()
   {
      
   }
}
