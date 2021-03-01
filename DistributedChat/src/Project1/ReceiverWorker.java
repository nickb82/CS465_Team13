package Project1;

import java.io.*; 
import java.util.*;
import java.net.*;


public class ReceiverWorker extends Thread
{
    Socket getConnection = null;
    ObjectOutputStream writeToNet = null;
    ObjectInputStream readFromNet = null;
    Message message = null;

    ReceiverWorker(Socket mySocket)
    {
        try
        {
            writeToNet = new ObjectOutputStream(getConnection.getOutputStream());
            readFromNet = new ObjectInputStream(getConnection.getInputStream());
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
            System.out.printf("Failed to read from Object");
        }

        catch(ClassNotFoundException nf)
        {
            System.out.printf("Failed to read from Object");
        }

        int index = 0;
        String names = null;
        NodeInfo particapantInfo = null;

        switch(message.getType())
        {
            case "JOIN" :

                try
                {
                    //ChatNode.clientList.add(ChatNode.myInfo);
                    writeToNet.writeObject(ChatNode.clientList);
                }

                catch(IOException ex)
                {
                    System.out.printf("Error delaing with Join request");
                }
                
                break;

            case "JOINED" :

            ChatNode.clientList.add(ChatNode.myInfo);

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
