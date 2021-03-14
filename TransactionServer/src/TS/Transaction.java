/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

/**
 *
 * @author young
 */
public class Transaction 
{
   int id;
   Account account;
   
   public int getID()
   {
      return id;
   }
   
   public void setID(int num)
   {
      this.id = num;
   }
   
   public void abort()
   {
      
   }
}
