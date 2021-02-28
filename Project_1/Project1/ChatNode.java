package Project1;

import java.io.*; 
import java.util.*; 
import java.net.*; 


public class ChatNode
{
    static int myPortNum;
    static String myName, messageToSend, myIPAddress, input, argParse[];
    static Vector<NodeInfo> clientList = new Vector<>();

    static Sender senderThread;
    static Receiver receiverThread;


    public static void run()
    {
        //ask for information and parse user input to crete a new node
        System.out.println("Enter your First Name and Port Number seperated by a space");
        Scanner userInput = new Scanner(System.in);
         String input = userInput.nextLine();
         String argParse[] = input.split(" ");
         myName = argParse[0];
         myPortNum = Integer.parseInt(argParse[1]);

        try
        {
            myIPAddress = InetAddress.getLocalHost().getHostAddress();
        }

        catch (UnknownHostException err)
        {
            System.out.println(err);
        }
        

        

        //create a new chat node to be added to the list
        NodeInfo myInfo = new NodeInfo(myName, myIPAddress, myPortNum);

        (receiverThread = new Receiver(myInfo)).start();
        (senderThread = new Sender()).start();

        
        
    }

    public static void main(String[] args) 
    {

        ChatNode.run();

    }



    
}

