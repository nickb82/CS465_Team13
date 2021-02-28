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

        ObjectOutputStream writeToNet;
        ObjectInputStream readFromNet;

        try(Socket nodeSocket = new Socket())
        {
            while(true)
            {
                System.out.println("Please enter a message command (JOIN,JOINED,LEAVE,NOTE)");
                Scanner userInput = new Scanner(System.in);
                String input = userInput.nextLine();


                writeToNet = new ObjectOutputStream(nodeSocket.getOutputStream());
                readFromNet = new ObjectInputStream(nodeSocket.getInputStream());

                //can be turned into a switch statement
                if(input.startsWith("JOIN"))
                {
                    //create JoinMessage Object
                    JoinMessage jMessage = new JoinMessage(myNode);
                    writeToNet.writeObject(jMessage);
                }

                if(input.equalsIgnoreCase("LEAVE"))
                {
                    //create a LeaveMessage object
                    LeaveMessage lMessage = new LeaveMessage(myNode);
                    writeToNet.writeObject(lMessage);
                }
                
                if(input.equalsIgnoreCase("NOTE"))
                {
                    System.out.println("Enter a message into the chat:");
                    userInput = new Scanner(System.in);
                    message = userInput.nextLine();

                    //create a NoteMessage Object
                    NoteMessage nMessage = new NoteMessage(message);
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