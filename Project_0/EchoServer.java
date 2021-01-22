import java.io.*;
import java.net.*;
import java.util.*;



import java.text.SimpleDateFormat;

public class EchoServer
{
    public static void main(String args[])
    {
        char[] alphArray;
        int count = 0;
        boolean serverOn = true;

        System.out.println("Echo Server is started!");

        //try to connect Server to a port
        try(ServerSocket echoServerSckt = new ServerSocket(8000))
        {
            System.out.println("Server Strarted!");

            //Reporting if and when the server is connected
            Calendar currentTime = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM dd, yyyy 'at' hh:mm:ss a, zzzz");
            System.out.println("Time/Date: " + formatter.format(currentTime.getTime()));
            System.out.println("(Server) Waiting on Clients......");

            //WHile server is on and running
            while(serverOn == true)
            {
                //Creates threads for clients
                count++;
                Socket clientSocket = echoServerSckt.accept();
                Runnable clientRunnable = new EchoThread(clientSocket, count);
                Thread clientThread = new Thread(clientRunnable);

                clientThread.start();

                if(serverOn == false)
                {
                    System.out.println(" Server Connection Ending");
                    break;
                }
            }
        }

        catch(IOException ioe)
        {
            System.out.println("Could not create server socket on port 8000. System Exiting!");
            System.exit(-1);
        }

    }
}

class EchoThread implements Runnable
{
    private Socket socket;
    private int count;
    private long threadId;

    EchoThread(Socket socket, int count)
    {
        this.socket = socket;
        this.count = count;
    }

    public long getThreadId()
    {
        threadId = Thread.currentThread().getId();
        return threadId;
    }

    @Override
    public void run()
    {
        boolean threadRun = true;

        
        System.out.println("(Server) Client " + count +" is connected" );

        while(threadRun)
        {
            
            try
            {
                DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
                DataInputStream fromClient = new DataInputStream(socket.getInputStream());
                char charFromClient;
                char[] validCharArr = new char[50];
                int alphIndex = 0;

                //check input stream from client 
                while(fromClient.available() > 0)
                {
                    //convert bytes to char
                    charFromClient = (char)fromClient.read();
                    
                    // put valid input into alphabetical array 
                    if( (charFromClient >= 'a' && charFromClient <= 'z')  || ( charFromClient >= 'A' && charFromClient <= 'Z') )
                    {
                        //Send current valid char to client
                        toClient.writeChar(charFromClient);

                        //Save input to later be checked for user input == quit
                        validCharArr[alphIndex] = charFromClient;

                        // Shut down thread if valid quit is recieved
                        if( Character.toLowerCase(validCharArr[0]) == 'q' && Character.toLowerCase(validCharArr[1]) == 'u' && Character.toLowerCase(validCharArr[2]) == 'i' && Character.toLowerCase(validCharArr[3]) == 't')
                        {
                            System.out.println("(Server) Client " + count + " is terminated");
                            threadRun = false;
                        }
                        alphIndex++;
                    }

                    toClient.flush();
                }
            }

            catch(IOException e)
            {
                System.err.println(e);
            }
        }

    }
}




