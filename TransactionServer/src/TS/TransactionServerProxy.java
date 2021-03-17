/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

/**
 *
 * @author Nick and Robel
 */
public class TransactionServerProxy 
{
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
   
   TransactionServerProxy(Message clientMess)
   {
      
   }
}
