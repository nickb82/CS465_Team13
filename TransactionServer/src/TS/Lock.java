/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick and Robel
 */
public class Lock
{
   private Object object;
   private Vector holders;
   private LockType lockType;
   
   public synchronized void acquire(Transaction trans, LockType aLockType)
   {
      try
      {
         wait();
      } 
      catch (InterruptedException ex) 
      {
         Logger.getLogger(Lock.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      if(holders.isEmpty())
      {
         holders.addElement(trans);
         lockType = aLockType;
      }
      
      else if(holders.contains(trans))
      {
         //share read
         
         if(!holders.contains(trans))
         {
            holders.addElement(trans);
         }
      }
      
      else if(holders.contains(trans))
      {
         LockType.promote();
      }
   }
   
   public synchronized void release(Transaction trans)
   {
      //remove this holder
      holders.removeElement(trans);
      
      //set locktype to none
      notifyAll();
   }
}
