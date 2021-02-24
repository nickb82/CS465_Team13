import java.io.ServerSocket;
import java.util.Scanner;
import java.util.IOException;

class Recieve_Thread implements Runnable
{
    ServerSocket senderSocket = null;
    
 
    public void run(){

        try{
            senderSocket = new ServerSocket(); 

        }

        catch(err){
            System.out.println(err);
        }

    }

}
