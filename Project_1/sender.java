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

    

    public void run()
    {
        try(Socket nodeSocket = new Socket())
        {
            while(true)
            {
                ObjectOutputStream oos = new ObjectOutputStream(nodeSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(nodeSocket.getInputStream());

                if(message.equalsIgnoreCase("JOIN"))
                {
                    //create JoinMessage Object
                    JoinMessage jMessage = new JoinMessage(myNode);
                    oos.writeObject(jMessage);
                }

                if(message.equalsIgnoreCase("LEAVE"))
                {
                    //create a LeaveMessage object
                    LeaveMessage lMessage = new LeaveMessage(myNode);
                    oos.writeObject(lMessage);
                }
                
                if(message.equalsIgnoreCase("NOTE"))
                {
                    System.out.println("Enter a message into the chat:");
                    Scanner noteInput = new Scanner(System.in);

                    //create a NoteMessage Object
                    NoteMessage nMessage = new NoteMessage(noteInput.toString());
                    oos.writeObject(nMessage);

                }
                
                break;
            }
        }

        catch(IOException err)
        {
            System.out.println(err);
        }
    }
    
}