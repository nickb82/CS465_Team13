package Project1;

import java.util.*; 
import java.net.*; 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;




class Sender extends Thread 
{
    Scanner userInput;
    boolean hasJoined = false;
    String input;
    ObjectOutputStream writeToNet = null;
    ObjectInputStream readFromNet = null;
    Vector<NodeInfo> updatedList;



  
    @Override
    public void run()
    {
        String message, name, joinAddress;
        int joinPortNum;

        NodeInfo participantInfo;

        name = ChatNode.myInfo.getName();
        joinAddress = ChatNode.myInfo.getIPAdress();
        joinPortNum = ChatNode.myInfo.getPortNum();

        

        //run until SHUTDOWN command is called
        while(true)
        {
            System.out.printf("Enter one of the following commands: JOIN, JOINED, LEAVE, NOTE\n");
            userInput = new Scanner(System.in);
            
            input = userInput.nextLine();
            
            //Crete Socket
            Socket senderSocket;
            try
            {
                senderSocket = new Socket(joinAddress,joinPortNum); 
            }

            catch(IOException ex)
            {
                System.out.printf("Error with Socket\n");
                continue;
            }

            // create input and out put streams
            try
            {
                writeToNet = new ObjectOutputStream(senderSocket.getOutputStream());
                readFromNet = new ObjectInputStream(senderSocket.getInputStream());
            }

            catch(IOException ex)
            {
                System.out.println("Failed to open streams");
            }

            //Start checking for each message command 
            if(input.startsWith("JOIN"))
            {
                if(hasJoined)
                {
                    System.out.printf("You have already joined the chat");
                    continue;
                }

                else
                {
                    

                    //send join request
                    try
                    {
                        //open streams
                        writeToNet = new ObjectOutputStream(senderSocket.getOutputStream());
                        readFromNet = new ObjectInputStream(senderSocket.getInputStream());

                        //send join request over streams
                        JoinMessage jMessage = new JoinMessage();
                        jMessage.setType("JOIN");
                        jMessage.setNode(ChatNode.myInfo);
                        System.out.printf("[CheckPoint] Sender: before jM write to stream");
                        writeToNet.writeObject(jMessage);

                        //recieve participant list
                        try
                        {
                            updatedList = (Vector<NodeInfo>)readFromNet.readObject();
                        }

                        catch(ClassNotFoundException ex)
                        {
                            System.out.printf("Could not update list through stream\n");
                        }
                        
                        //update list
                        ChatNode.clientList.addAll(updatedList);
                    }

                    catch(IOException ex)
                    {
                        System.out.printf("Streams fail to open");
                    }

                    //close socket and streams
                    finally
                    {
                        try
                        {
                            senderSocket.close();
                            writeToNet.close();
                            readFromNet.close();
                        }

                        catch(IOException ex)
                        {
                            System.out.printf("Error trying to close streams and socket");
                        }
                    }
                    
                    
                }
                
            }

            else if(input.startsWith("JOINED"))
            {

                if(hasJoined)
                {
                    System.out.printf("You have already joined the chat\n");
                    continue;
                }

                else
                {
                    //create JoinedMessage and write to stream
                    JoinedMessage jdMessage = new JoinedMessage();
                    jdMessage.setType("JOINED");
                    jdMessage.setNode(ChatNode.myInfo);
                    
                    try
                    {
                        writeToNet.writeObject(jdMessage);
                    }

                    catch(IOException ex)
                    {
                        System.out.printf("Error trying to send JOINED message");
                    }

                    hasJoined = true;
                    break;

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
                        //create a LeaveMessage object and write it to the stream
                        LeaveMessage lMessage = new LeaveMessage();
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
                        //Grab the users message and attach it to the NoteMessage Object
                        System.out.printf("Enter a message into the chat: ");
                        userInput = new Scanner(System.in);
                        message = ChatNode.myInfo.getName() + ": " + userInput.nextLine();

                        //create a NoteMessage Object and write it to the stream
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

                //if user has joined chat and entered an invalid message command
                else
                {
                    System.out.printf("Please enter a valid command\n");
                    continue;
                }
            }
            
        }
    }
}