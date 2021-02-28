import java.io.*; 
import java.util.*; 
import java.net.*; 
import java.io.IOException;



class Sender extends Thread 
{
    String message;
    NodeInfo myNode;
  

    public void run()
    {
        try(Socket nodeSocket = new Socket())
        {
            while(true)
            {
                ObjectOutputStream writeToNet = new ObjectOutputStream(nodeSocket.getOutputStream());
                ObjectInputStream readFromNet = new ObjectInputStream(nodeSocket.getInputStream());

                //can be turned into a switch statement
                if(message.equalsIgnoreCase("JOIN"))
                {
                    //create JoinMessage Object
                    JoinMessage jMessage = new JoinMessage(myNode);
                    writeToNet.writeObject(jMessage);
                }

                if(message.equalsIgnoreCase("LEAVE"))
                {
                    //create a LeaveMessage object
                    LeaveMessage lMessage = new LeaveMessage(myNode);
                    writeToNet.writeObject(lMessage);
                }
                
                if(message.equalsIgnoreCase("NOTE"))
                {
                    System.out.println("Enter a message into the chat:");
                    Scanner noteInput = new Scanner(System.in);

                    //create a NoteMessage Object
                    NoteMessage nMessage = new NoteMessage(noteInput.toString());
                    writeToNet.writeObject(nMessage);
                }

                //create ois to grab updated list if sent by reciever
                
                break;
            }
        }

        catch(IOException err)
        {
            System.out.println(err);
        }
    }
    
}