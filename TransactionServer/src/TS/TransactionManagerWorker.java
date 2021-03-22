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

/**
 *
 * @author Nick and Robel
 */
public class TransactionManagerWorker extends Thread
{
   Socket clientSocket;
   ObjectOutputStream writeToNet;
   ObjectInputStream readFromNet;
   TransactionManagerWorker(Socket localClientSocket)
   {
      clientSocket = localClientSocket;
   }
   
   public void run()
   {
      //loop that reads messages, coming from client & translates
      //them into high-level actions
      while(true)
      {
         try
         {
            readFromNet = new ObjectInputStream(clientSocket.getInputStream());
            writeToNet = new ObjectOutputStream(clientSocket.getOutputStream());
         }
         catch(IOException ioe)
         {
            
         }
      }
   }
}
