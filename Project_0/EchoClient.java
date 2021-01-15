import java.io.*;
import java.net.*;
import java.util.*;

//more comments to come
public class EchoClient 
{
    public static void main(String[] args)
    {
        System.out.println("Echo Client ...");

        try
        {
        InetAddress localHost = InetAddress.getLocalHost();
        Socket socket = new Socket(localHost,8000);
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("We are connected to server.");
        Scanner scanner = new Scanner(System.in);

        while(true)
        {
            System.out.println("Please Enter text: ");
            String input = scanner.nextLine();

            if("exit".equalsIgnoreCase(input))
            {
                break;
            }
            toServer.println(input);
            String response = fromServer.readLine();
            System.out.println("System response is: " + response);
        }
    }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        
    }
}
