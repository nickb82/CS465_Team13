/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.io.ObjectOutputStream;

/**
 *
 * @author Nick and Robel
 */
public class TransactionServerProxy 
{
   Message clientMessage;
   ObjectOutputStream op;
   public void createMessage(String type)
   {
      if(type == "OPEN")
      {
         
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
