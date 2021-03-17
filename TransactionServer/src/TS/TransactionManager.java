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
public class TransactionManager extends Thread
{
   //list of active transactions
   int[] active_trans;
   Socket clientSocket;
   
   TransactionManager(Socket localClientSocket)
   {
      clientSocket = localClientSocket;
   }
   
   public void run()
   {
      
   }
}
