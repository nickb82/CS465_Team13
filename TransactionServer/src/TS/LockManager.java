/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Nick and Robel
 */
public class LockManager
{
   //transID/lockType ?
   private Hashtable theLocks;
   public boolean lockOn = false;
   
   public LockManager(boolean lockOn)
   {
      this.lockOn = lockOn;
   }
   
   // object is lock object?
   public void setLock(Object object, Transaction trans, LockType lockType)
   {
      Lock foundLock = null;
      
      synchronized(this)
      {
         //find the lock associated with object
         // if there is not one, create it and add to the hashtable
         if(!theLocks.contains(object))
         {
            theLocks.put(trans, lockType);
         }
         
         foundLock.acquire(trans, lockType);
      }
      
   }
   
   //synchronized this one because we want ot remove all entries
   public synchronized void unLock(Transaction trans)
   {
      Enumeration e = theLocks.elements();
      
      while(e.hasMoreElements())
      {
         Lock aLock = (Lock) (e.nextElement());
         
         //trans is a holder of this lock
         if(e.equals(trans))
         {
            aLock.release(trans);
         }
      }
   }
}
