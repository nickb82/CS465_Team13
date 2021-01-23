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

                //Start the clients thread
                clientThread.start();

                if(serverOn == false)
                {
                    System.out.println(" Server Connection Ending");
                    break;
                }
            }
        }

        // Any issue with creating server socket
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

    enum State
    {
        NO_STATE,Q_STATE, U_STATE, I_STATE, TERMINATE_STATE;
    }

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


        //Print to the server that a client has connected
        System.out.println("(Server) Client " + count +" is connected" );
        
        // while thread is active
        while(threadRun)
        {
            
            try
            {
                DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
                DataInputStream fromClient = new DataInputStream(socket.getInputStream());
                char charFromClient;
                char[] validCharArr = new char[50];
                int alphIndex = 0;
                State currentState = State.NO_STATE;
                String validInput;

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

                        //Change state of thread as user input comes closer to spelling quit
                        if(charFromClient == 'q' && currentState == State.NO_STATE)
                        {
                            currentState = State.Q_STATE;
                        }
                        if(charFromClient == 'u' && currentState == State.Q_STATE)
                        {
                            currentState = State.U_STATE;
                        }
                        if(charFromClient == 'i' && currentState == State.U_STATE)
                        {
                            currentState = State.I_STATE;
                        }
                        if(charFromClient == 't' && currentState == State.I_STATE)
                        {
                            currentState = State.TERMINATE_STATE;
                        }

                        if(currentState == State.TERMINATE_STATE)
                        {
                            System.out.println("(Server) Client " + count + " is terminated");
                            threadRun = false;
                        }

                    }

                    // flush buffer
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




