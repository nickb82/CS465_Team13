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
public class Message implements MessageType
{
   // set type to integer for symbolic constent
   int type;
   Object content;
   
   public Message(int type, Object content)
   {
      this.type = type;
      this.content = content;
   }
   
   public Message(int type)
   {
      this(type, null);
   }
   
   public void setContent(Object localContent)
   {
      content = localContent;
   }
   
   public Object getContent()
   {
      return content;
   }

   public int getType() {
      return type;
   }

   public void setType(int localType) {
      this.type = localType;
   }
}
