package Project1;

import java.io.*; 
import java.util.*;
import java.net.*;


public class ReceiverWorker extends Thread
{
    ObjectOutputStream writeToNet = null;
    ObjectInputStream readFromNet = null;
    Message message = null;

    ReceiverWorker(Socket mySocket)
    {
        try
        {
            writeToNet = new ObjectOutputStream(mySocket.getOutputStream());
            readFromNet = new ObjectInputStream(mySocket.getInputStream());
        }

        catch(IOException ex)
        {
            System.out.println("Failed to open streams");
        }
    }

    @Override
    public void run()
    {
        try
        {
            message = (Message)readFromNet.readObject();
        }

        catch(IOException ex)
        {
            System.out.printf("Failed to read from Object IO ");
        }

        catch(ClassNotFoundException nf)
        {
            System.out.printf("Failed to read from Object CNF ");
        }


        switch(message.getType())
        {
            case "JOIN" :

                try
                {
                    ChatNode.clientList.add(ChatNode.myInfo);
                    writeToNet.writeObject(ChatNode.clientList);
                }

                catch(IOException ex)
                {
                    System.out.printf("Error delaing with Join request");
                }
                
                break;
            
            case "LEAVE" :

            ChatNode.clientList.remove(ChatNode.myInfo);
            
            break;

            case "NOTE" :
                
                NoteMessage noteMessage = (NoteMessage)message;
                String note = noteMessage.getMessage();
                System.out.printf(note);
        }

    }
    
}
