import java.io.*; 
import java.util.*; 
import java.net.*; 
import java.io.IOException;



class Sender implements Runnable
{
    String message;
    NodeInfo myNode;

    Sender(String userInput, NodeInfo nodeInfo)
    {
        this.message = userInput;
        this.myNode = nodeInfo;
    }

    //socket createdd
    //Socket nodeSocket = new Socket(ipAdress, port);
    //ObjectInputStream ois = new ObjectInputStream(ndoeSocket.getInputStream());
    //ObjectOutputStream oos = new ObjectOutputStream(nodeSocket);


    //receieve the result from the server

    public void run()
    {
        while(true)
        {
            if(message.equalsIgnoreCase("JOIN"))
            {
                //create JoinMessage Object
            }

            if(message.equalsIgnoreCase("LEAVE"))
            {
                //create a LeaveMessage object
            }
            
            if(message.equalsIgnoreCase("NOTE"))
            {
                System.out.println("Enter a message into the chat:");
                Scanner noteInput = new Scanner(System.in);

                //create a NoteMessage Object

            }
            
            break;
        }
    }
    
}