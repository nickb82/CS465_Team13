import java.io.*; 
import java.util.*; 
import java.net.*; 


public class ChatNode
{
    int portNumber;
    String clientName, messageToSend, IP_Adress;
    static Vector<NodeInfo> clientList = new Vector<>();

    public Vector<NodeInfo> getClientList() 
    {
        return clientList;
    }

    public static void main(String[] args) 
    {

        //ask for information and parse user input to crete a new node
        System.out.println("Please enter your Name, IP Adress, and Port Number seperated by commas ");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        String argParse[] = input.split(",");

        //create a new client node
        NodeInfo myInfo = new NodeInfo(argParse[0], argParse[1], Integer.parseInt(argParse[0]));

    }



    
}

