import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.IOException;
// import java.util.Scanner;


public class Client_Node
{
    int IP_Adress;
    int portNumber;
    String clientName, messageToSend;
    ClientNode[] clientNameList;



    // private String messageToSend


    CLient_Node(String name, int ipAdress, int portNum)
    {
        this.clientName = name;
        this.IP_Adress = ipAdress;
        this.protNumber = portNum;
        this.messageToSend = messageToSend;
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


    public void Recieve_Thread(){
        
    }
    
}

