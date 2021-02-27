import java.io.*; 
import java.util.*; 
import java.net.*; 


public class ChatNode
{
    int portNumber;
    String clientName, messageToSend, IP_Adress;
    static Vector<NodeInfo> clientList = new Vector<>();

    public static void main(String[] args) 
    {

        //ask for information and parse user input to crete a new node
        System.out.println("Enter your Name, IP Adress, and Port Number seperated by commas ");
        Scanner startInput = new Scanner(System.in);
        
        String input = startInput.nextLine();
        String argParse[] = input.split(",");

        //create a new chat node to be added to the list
        NodeInfo myInfo = new NodeInfo(argParse[0], argParse[1], Integer.parseInt(argParse[2]));

        
        while(true)
        {
            System.out.println("Enter a message command JOIN - to join a chat, LEAVE to leave a chat, NOTE to send a message to the chat ");
            
            String cmdMessage = startInput.nextLine();
            

            Sender sendThread = new Sender(cmdMessage, myInfo);
            Reciever recieveThread = new Reciever();

            sendThread.run();
            recieveThread.run();


            break;
        }

    }



    
}

