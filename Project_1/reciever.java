import java.io.*; 
import java.util.*; 
import java.net.*; 



class Reciever implements Runnable
{
    //ServerSocket senderS;
    
    // need worker thread within this thread
 
    public void run(){

        try(ServerSocket senderS = new ServerSocket())
        {
            //server loop
            while(true)
            {
                //spawn worker thread
                //Thread worker = new Thread();
                break;
            }

        }

        catch(IOException err)
        {
            System.out.println(err);
        }

    }

}
