import java.io.*;
import java.net.*;

public class EchoServer
{

    public static void main(String args[])
    {
        System.out.println("Echo Server is started!");
        try 
        (ServerSocket echoServer = new ServerSocket(8000);)
        {
            Socket clientSocket = echoServer.accept();
            System.out.println("We are connected to client");
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(),true);
            String line;
            //take in characeter by character
           // char charFromClient;
           // char[] charArray = new char[30];

            while((line = fromClient.readLine()) != null)
            {
                System.out.println("On Server: " + line);
                toClient.println(line);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}

//Please ignore, this is a work in progress
/*
class EchoThread implements Runnable
{
    private Socket socket;
    private in count;
    private long threadId;
    InputStream fromClient;
    OutputStream toClient;
    private PrintWriter pw;

    EchoThread(Sockets socket, int count)
    {
        this.socket = socket;
        this.count = count;
    }

    public long getThreadId()
    {
        threadId = Thread.currentThread().getId();
        return id;
    }

    @Ovveride
    public void run()
    {
        try
        {
            fromClient = socket.getInputStream();
            toClient = socket.getOutputStream();
        }

        catch(IOException e)
        {
            System.err.println(e);
        }

        try(Scanner sc = new Scanner(fromClient))
        {
            pw = new PrintWriter(toClient, true);

            if()
        }
    }
}
*/