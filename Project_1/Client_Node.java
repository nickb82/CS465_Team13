import java.net.ServerSocket;
import java.util.Scanner;

public class Client_Node 
{
    int IP_Adress;
    int portNumber;
    String clientName;
    ClientNode[] clientNameList;

    CLient_Node(String name, int ipAdress, int portNum)
    {
        this.clientName = name;
        this.IP_Adress = ipAdress;
        this.protNumber = portNum;
    }

    public String[] getClientNameList() 
    {
        return clientNameList;
    }

    public int getIP_Adress() 
    {
        return IP_Adress;
    }

    public String getName() {
        return name;
    }

    public joinMessage(Client_Node[] clientList)
    {
        int index;

        for(index = 0; index < clientList.length; index++)
        {
            Client_Node[index].updateList(clientNam2e);
        }
    }

    public updateList(String name)
    {
        
    }
    
}

class Recieve_Thread implements Runnable
{
    ServerSocket;
    Recieve_Thread(Socket socket)
    {

    }


    while(true)
    {

    }

}
class Send_Thread implements Runnable
{

    Scanner scanInput = new Scanner(System.in);
    System.out.println("To join the chat pleae enter: JOIN, followed by an IP Address and port number");
    

    String userInput = scanInput.nextline();
    String argParse[] = userInput.split(" ");

}
