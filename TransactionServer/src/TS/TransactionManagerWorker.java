/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.net.Socket;

/**
 *
 * @author Nick and Robel
 */
public class TransactionManagerWorker extends Thread
{
   Socket clientSocket;
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
         
      }
   }
}
