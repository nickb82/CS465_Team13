package Project1;

import java.io.*; 
import java.util.*; 
import java.net.*; 
import java.io.IOException;




class Sender extends Thread 
{
    Scanner userInput;
    boolean hasJoined;
    String input;

    public Sender()
    {
        userInput = new Scanner(System.in);
        hasJoined = false;
           
    }
  
    @Override
    public void run()
    {
        String message, name, joinAddress;
        int joinPortNum;

        NodeInfo participantInfo;
        Vector<NodeInfo> participantList;

        ObjectOutputStream writeToNet;
        ObjectInputStream readFromNet;

        //run until SHUTDOWN command is called
        while(true)
        {
            //ask the user for the type of message they would like to send
            //System.out.println("Please enter a message command (JOIN,JOINED,LEAVE,NOTE,SHUTDOWN)");
            //userInput = new Scanner(System.in);
            input = userInput.nextLine();


            //can be turned into a switch statement
            if(input.startsWith("JOIN"))
            {
                if(hasJoined)
                {
                    System.out.println("You have already joined the chat");
                    continue;
                }

                else
                {
                    String joinInput = userInput.nextLine();
                    String argParse[] = joinInput.split(" ");

                    try
                    {
                        name = argParse[0];
                        joinAddress = argParse[1];
                        joinPortNum = Integer.parseInt(argParse[2]);
                    }

                    catch(ArrayIndexOutOfBoundsException ex)
                    {
                        // Chat Node list is empty, so we assume we are the first to join
                        System.out.pritnln("No informaation was given");
                        ChatNode.clientList.add(ChatNode.myInfo);
                        hasJoined = true;

                        continue;
                    }

                    Socket senderSocket;
                    try
                    {
                        senderSocket = new Socket(joinAddress,joinPortNum); 
                    }

                    catch(IOException ex)
                    {
                        System.out.pritnln("Could not connect to Sender Socket");
                        continue;
                    }

                    //send join request
                    try
                    {
                        //open streams
                        writeToNet = new ObjectOutputStream(senderSocket.getOutputStream());
                        readFromNet = new ObjectInputStream(senderSocket.getInputStream());

                        //send join request over streams
                        writeToNet.writeObject(new JoinMessage(ChatNode.myInfo));
                        ChatNode.setList(readFromNet.readObject());

                        senderSocket.close();
                    }

                    catch(IOException ex)
                    {
                        System.out.println("Issues with Output Stream and Input Stream in sender");
                    }

                    finally
                    {
                        senderSocket.close();
                    }
                }
            }

            else if(input.startsWith("LEAVE") || input.startsWith("SHUTDOWN"))
            {
                if(!hasJoined)
                {
                    System.out.pritnln("You have not joined the chat");
                    continue;
                }


                else if(input.startsWith("LEAVE"))
                {
                    //create a LeaveMessage object
                    LeaveMessage lMessage = new LeaveMessage(ChatNode.myInfo);
                    writeToNet.writeObject(lMessage);
                    lMessage.setType("LEAVE");
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
                if(hasJoined)
                {
                    System.out.println("Enter a message into the chat:");
                    userInput = new Scanner(System.in);
                    message = userInput.nextLine();

                    //create a NoteMessage Object
                    NoteMessage nMessage = new NoteMessage(message);
                    nMessage.setType("NOTE");
                    writeToNet.writeObject(nMessage);
                }

                else
                {
                    System.out.println("Need to join chat first");
                    continue;
                }

            }

            else
            {
                if(!hasJoined)
                {
                    System.out.println("Need to join chat first");
                    continue;
                }

                else
                {
                    System.out.println("Please enter a valid command");
                    continue;
                }
            }
            
        }
    }
    
}