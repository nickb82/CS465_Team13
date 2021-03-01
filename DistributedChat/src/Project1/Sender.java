package Project1;

import java.util.*; 
import java.net.*; 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;




class Sender extends Thread 
{
    Scanner userInput;
    boolean hasJoined;
    String input;

  
    @Override
    public void run()
    {
        String message, name, joinAddress;
        int joinPortNum;

        NodeInfo participantInfo;

        ObjectOutputStream writeToNet = null;
        ObjectInputStream readFromNet = null;
        hasJoined = false;

        //run until SHUTDOWN command is called
        while(true)
        {
            System.out.printf("Enter one of the following commands: JOIN, JOINED, LEAVE, NOTE\n");
            userInput = new Scanner(System.in);
            
            input = userInput.nextLine();


            //can be turned into a switch statement
            if(input.startsWith("JOIN"))
            {
                if(hasJoined)
                {
                    System.out.printf("You have already joined the chat");
                    continue;
                }

                else
                {
                    name = ChatNode.myInfo.getName();
                    joinAddress = ChatNode.myInfo.getIPAdress();
                    joinPortNum = ChatNode.myInfo.getPortNum();


                    //Crete Socket
                    Socket senderSocket;
                    try
                    {
                        senderSocket = new Socket(joinAddress,joinPortNum); 
                    }

                    catch(IOException ex)
                    {
                        System.out.printf("Error with Socket");
                        continue;
                    }

                    //send join request
                    try
                    {
                        //open streams
                        writeToNet = new ObjectOutputStream(senderSocket.getOutputStream());
                        readFromNet = new ObjectInputStream(senderSocket.getInputStream());

                        //send join request over streams
                        JoinMessage JMessage = new JoinMessage(ChatNode.myInfo);
                        JMessage.setType("JOIN");
                        JMessage.setNode(ChatNode.myInfo);
                        writeToNet.writeObject(JMessage);

                        //recieve participant list
                        Vector<NodeInfo> updatedList = (Vector<NodeInfo>)readFromNet.readObject();
                        ChatNode.clientList.addAll(updatedList);
                    }

                    catch(IOException ex)
                    {
                        System.out.printf("Streams fail to open");
                    }

                        senderSocket.close();
                        writeToNet.close();
                        readFromNet.close();
                    
                    
                }
                
            }

            else if(input.startsWith("LEAVE") || input.startsWith("SHUTDOWN"))
            {
                if(!hasJoined)
                {
                    System.out.printf("You have not joined the chat\n");
                    continue;
                }


                else if(input.startsWith("LEAVE"))
                {
                    try
                    {
                        //create a LeaveMessage object
                        LeaveMessage lMessage = new LeaveMessage(ChatNode.myInfo);
                        lMessage.setType("LEAVE");
                        lMessage.setNode(ChatNode.myInfo);
                        writeToNet.writeObject(lMessage);
                        hasJoined = false;
                        System.out.printf("You have LEFT the chat\n");
                        break;
                    }

                    catch(IOException ex)
                    {
                        System.out.printf("Sender: Error leaving the chat");
                    }
                }

                //Shutdown the system
                else
                {
                    System.out.printf("Shuting down the system");
                    System.exit(0);
                }
            }
            
            else if(input.startsWith("NOTE"))
            {
                if(hasJoined)
                {
                    try
                    { 
                        System.out.printf("Enter a message into the chat: ");
                        userInput = new Scanner(System.in);
                        message = ChatNode.myInfo.getName() + ": " + userInput.nextLine();

                        //create a NoteMessage Object
                        NoteMessage nMessage = new NoteMessage(message);
                        nMessage.setType("NOTE");
                        nMessage.setNode(ChatNode.myInfo);
                        writeToNet.writeObject(nMessage);
                    }

                    catch(IOException ex)
                    {
                        System.out.printf("Sender: Error when creating NOTE");
                    }
                }

                else
                {
                    System.out.printf("Need to join chat first\n");
                    continue;
                }

            }

            else
            {
                if(!hasJoined)
                {
                    System.out.printf("Need to join chat first\n");
                    continue;
                }

                else
                {
                    System.out.printf("Please enter a valid command\n");
                    continue;
                }
            }
            
        }
    }
}