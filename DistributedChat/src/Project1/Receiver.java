package Project1;

import java.util.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.*;



class Receiver extends Thread implements Serializable
{
     static ServerSocket receiverSocket = null;
    
    Receiver(NodeInfo nodeInfo)
    {
        //crete ServerSocket
        try
        {
            receiverSocket = new ServerSocket(nodeInfo.getPortNum());
        }

        catch(IOException ex)
        {
            System.out.printf("Creating Server Socket Failed");
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
                System.out.printf("Server Socket was not accepted");
            }
        }

    }

}
