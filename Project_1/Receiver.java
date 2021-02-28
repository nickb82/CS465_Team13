import java.io.*; 
import java.util.*;
import java.net.*;



class Receiver extends Thread implements Serializable
{
    static ServerSocket receiverSocket = null;
    
    Receiver(NodeInfo nodeInfo)
    {
        try
        {
            receiverSocket = new ServerSocket(nodeInfo.getPortNum());
        }

        catch(IOException ex)
        {
            System.out.println("Creating Server Socket Failed");
        }
    }
 
    public void run(){

        while(true)
        {
            try
            {
                //run receiver worker thread

                (new ReceiverWorker(receiverSocket.accept())).start();
            }

            catch(IOException err)
            {
                System.out.println("Error accpeting client");
            }
        }

    }

}
