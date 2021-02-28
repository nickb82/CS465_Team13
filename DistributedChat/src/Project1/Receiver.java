package Project1;

import java.util.*;
import java.io.IOException;
import java.net.*;



class Receiver extends Thread 
{
     static ServerSocket receiverSocket = null;
    
    Receiver(NodeInfo nodeInfo)
    {
        try
        {
            System.out.println("Receiver:" + nodeInfo.getPortNum());
            receiverSocket = new ServerSocket(8000);
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
                System.out.printf("Server Socket was not accepted");
            }
        }

    }

}
