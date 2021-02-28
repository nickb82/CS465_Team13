package Project1;

import java.io.*; 
import java.util.*; 
import java.net.*; 
import java.io.IOException;




class Sender extends Thread 
{
    String message;
    NodeInfo myNode;
    boolean hasJoined = false;
  
    @Override
    public void run()
    {
        NodeInfo participantInfo;
        Vector<NodeInfo> participantList;

        ObjectOutputStream writeToNet;
        ObjectInputStream readFromNet;

        try(Socket nodeSocket = new Socket())
        {
            while(true)
            {
                //ask the user for the type of message they would like to send
                System.out.println("Please enter a message command (JOIN,JOINED,LEAVE,NOTE,SHUTDOWN)");
                Scanner userInput = new Scanner(System.in);
                String input = userInput.nextLine();


                writeToNet = new ObjectOutputStream(nodeSocket.getOutputStream());
                readFromNet = new ObjectInputStream(nodeSocket.getInputStream());

                //can be turned into a switch statement
                if(input.startsWith("JOIN"))
                {
                    if(hasJoined)
                    {
                        System.out.println("You have already joined the chat");
                        continue;
                    }

                    //create JoinMessage Object
                    JoinMessage jMessage = new JoinMessage(myNode);
                    writeToNet.writeObject(jMessage);
                    hasJoined = true;
                }

                else if(input.startsWith("LEAVE") || input.startsWith("SHUTDOWN"))
                {
                    if(input.startsWith("LEAVE"))
                    {
                        //create a LeaveMessage object
                        LeaveMessage lMessage = new LeaveMessage(myNode);
                        writeToNet.writeObject(lMessage);
                        hasJoined = false;
                        System.out.println("You have LEFT the chat");
                        break;
                    }

                    //Shutdown the system
                    else
                    {
                        System.out.println("Shuting down the system");
                        System.exit(0);
                    }
                }
                
                else if(input.startsWith("NOTE"))
                {
                    System.out.println("Enter a message into the chat:");
                    userInput = new Scanner(System.in);
                    message = userInput.nextLine();

                    //create a NoteMessage Object
                    NoteMessage nMessage = new NoteMessage(message);
                    writeToNet.writeObject(nMessage);
                }

                else
                {
                    if(!hasJoined)
                    {
                        System.out.println("Need to join chat first");
                        continue;
                    }
                }
                
            }
        }

        catch(IOException err)
        {
            System.out.println("Sender Socket was denied");
        }
    }
    
}