package Project1;

import java.io.*; 
import java.util.*;
import java.net.*;


public class ReceiverWorker extends Thread
{
    ObjectOutputStream writeToNet = null;
    ObjectInputStream readFromNet = null;
    Message message = null;
    Socket getConnection;

    ReceiverWorker(Socket socket)
    {
        //create streams
        this.getConnection = socket;
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
            // read incoming messages
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


        //check which type of message was read and handle according to the type
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

            //remove node from list
            ChatNode.clientList.remove(ChatNode.myInfo);
            
            break;

            case "NOTE" :
                
                //Grab message from object and print to the screen
                NoteMessage noteMessage = (NoteMessage)message;
                String note = noteMessage.getMessage();
                System.out.printf(note);
        }

    }
    
}
