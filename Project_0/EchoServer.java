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

            //Still need a condition to close the server
            while(serverOn == true)
            {
                //Creates threads for clients
                count++;
                Socket clientSocket = echoServerSckt.accept();
                Runnable clientRunnable = new EchoThread(clientSocket, count);
                Thread clientThread = new Thread(clientRunnable);

                clientThread.start();
                //System.out.println(" Server Connection Ending");

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
    InputStream fromClient;
    OutputStream toClient;
    private PrintWriter pw;

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

        
        System.out.println("(Server) Client " + count +" is connected" );

        try
        {
            fromClient = socket.getInputStream();
            toClient = socket.getOutputStream();
        }

        catch(IOException e)
        {
            System.err.println(e);
        }

        while(true)
        {
            try
            {
                //Recieve input from buffer provided by the client
                BufferedReader br = new BufferedReader(new InputStreamReader(fromClient));
                String input = br.readLine();

                if(input.equals("quit"))
                {
                    socket.close();
                    System.out.println("(Server) Client " + count + " Terminated");
                    break;
                }
                else
                {
                    System.out.println("(Server) From Client " + count + ": " + input);
                    pw = new PrintWriter(toClient, true);
                    pw.println(input);
                    pw.flush();
                }

            }

            catch(IOException e)
            {
                System.err.println(e);
            }
        }

    }
}




