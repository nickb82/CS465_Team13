/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

import java.util.Scanner;

/**
 *
 * @author Nick and Robel
 */
public class TransactionClient {

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
   
   public static void main(String[] args) 
   {
      System.out.println("Please enter one of the commands: OPEN, CLOSE, READ, WRITE");
      Scanner userInput = new Scanner(System.in);
      String input = userInput.nextLine();
      
   }

}
